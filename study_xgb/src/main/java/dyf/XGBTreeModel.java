package dyf;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class XGBTreeModel implements Serializable {
    public static final int TREE_BASE_CNT = 10000;//TREE_BASE_CNT+leaf's index is the key in leafIndexOfVector
    public Map<Integer, Integer> leafIndexOfVectorMap = new HashMap<>();
    public Set<String> featureSet = new HashSet<>();

    public XGBTreeModel() {
    }

    public XGBTreeModel(String path) {
        treeList = loadModel(path);
    }

    public XGBTreeModel(InputStream in) {
        treeList = loadModel(in);
    }

    @Getter
    private List<TreeNode> treeList = new ArrayList<>();

    public void setLeafIndexToVectorIndex(int treeIndex, int leafIndex, int vectorIndex) {
        leafIndexOfVectorMap.put(leafIndex + TREE_BASE_CNT * treeIndex, vectorIndex);
    }

    public Integer getVectorIndexByLeafIndex(int treeIndex, int leafIndex) {
        return leafIndexOfVectorMap.get(leafIndex + TREE_BASE_CNT * treeIndex);
    }

    public List<TreeNode> loadModel(InputStream in) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line = "";
            log.info("update word list start");

            Map<Integer, TreeNode> tmpTreeMap = new HashMap<>();
            int vectorIndex = 0;
            int treeIndex = 0;


            while (null != (line = br.readLine())) {
                if (line.startsWith("booster")) {
                    continue;
                }

                TreeNode treeNode = new TreeNode();
                int nodeIdx = 0;
                if (!line.contains("leaf")) {
                    String[] condition = line.split(":");
                    //0:[servicetimes<41] yes=1,no=2,missing=1
                    nodeIdx = Integer.parseInt(condition[0].trim());
                    if (0 == nodeIdx) {
                        treeList.add(treeNode);
                        treeIndex++;
                        tmpTreeMap.clear();
                    }

                    String[] keyAndSplitValue = condition[1].split("\\[|<|\\]");
                    treeNode.setKey(keyAndSplitValue[1].trim());
                    featureSet.add(treeNode.getKey());
                    treeNode.setSplitValue(new Float(keyAndSplitValue[2].trim()));

                    String[] subTreeIdxAndWeight = keyAndSplitValue[3].split("=|,");
                    int subIdx = Integer.parseInt(subTreeIdxAndWeight[1].trim());
//					treeNode.setWeight(new Float(subTreeIdxAndWeight[subTreeIdxAndWeight.length - 1].trim()));
                    tmpTreeMap.put(subIdx, treeNode);
                } else {
                    String[] weight = line.split("=|,|:");
                    nodeIdx = Integer.parseInt(weight[0].trim());
                    treeNode.setLeafIndex(nodeIdx);
                    this.setLeafIndexToVectorIndex(treeIndex, nodeIdx, vectorIndex++);
                    treeNode.setWeight(new Float(weight[weight.length - 1].trim()));
                }
                if (nodeIdx > 0) {
                    TreeNode fatherTree = null;
                    if (0 == nodeIdx % 2) {
                        fatherTree = tmpTreeMap.get(nodeIdx - 1);
                        fatherTree.setGreatThanSubTree(treeNode);
                    } else {
                        fatherTree = tmpTreeMap.get(nodeIdx);
                        fatherTree.setLessThanSubTree(treeNode);
                    }
                }
            }

        } catch (IOException e) {
            log.error("write word list failed, error:{}", e);
        }
        return treeList;
    }


    public List<TreeNode> loadModel(String path) {
        try {
            return loadModel(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Map<Integer, Integer> getLeafIndexOfVectorMap() {
        return leafIndexOfVectorMap;
    }

    public void setLeafIndexOfVectorMap(Map<Integer, Integer> leafIndexOfVectorMap) {
        this.leafIndexOfVectorMap = leafIndexOfVectorMap;
    }

    public void setTreeNodes(List<TreeNode> treeNodes) {
        this.treeList = treeNodes;
    }

    public double predict(Map<String, Double> values) {
        try {
            if (treeList != null || treeList.size() > 0) {
                return 0.0;
            }
            double score = 0.0;
            for (int i = 0; i < treeList.size(); i++) {
                TreeNode treeNode = treeList.get(i);
                double leafWeight = getLeafWeight(treeNode, values);
                score += leafWeight;
//                log.info("predict:{},leafWeight:{}", i, leafWeight);
            }
            return score;
        } catch (Exception e) {
            log.warn("XgboostTreeModel error:" + JSON.toJSONString(values), e);
        }
        return 0.0;
    }

    public double predictProba(Map<String, Double> values) {
        return 1 / (1 + Math.exp(-predict(values)));
    }

    public Integer[] transformFeatureToIntArr(Map<String, Double> values) {
        if (CollectionUtils.isEmpty(getTreeList())) {
            return null;
        }
        List<Integer> featureInt = new ArrayList<>();
        for (int i = 0; i < getTreeList().size(); i++) {
            TreeNode treeNode = getTreeList().get(i);
            int leafIndex = this.getLeafIndex(treeNode, values);
            int vectorIndex = getVectorIndexByLeafIndex(i + 1, leafIndex);
            featureInt.add(vectorIndex);
        }
        return featureInt.toArray(new Integer[featureInt.size()]);
    }

    public int getLeafIndex(TreeNode treeNode, Map<String, Double> features) {
        TreeNode leftNode = getLeaf(treeNode, features);
        if (leftNode != null) {
            return leftNode.getLeafIndex();
        }
        return -1;
    }

    public double getLeafWeight(TreeNode treeNode, Map<String, Double> features) {
        TreeNode leftNode = getLeaf(treeNode, features);
        if (leftNode != null) {
            return leftNode.getWeight();
        }
        return 0.0;
    }

    public TreeNode getLeaf(TreeNode treeNode, Map<String, Double> features) {
        if (treeNode == null) {
            return null;
        }
        String key = treeNode.getKey();
        if (StringUtils.isNotBlank(key) && features.containsKey(key)) {
            Double f = features.get(key);
            if (null == f) {
                f = 0D;
            }
            if (f <= treeNode.getSplitValue()) {
                return getLeaf(treeNode.getLessThanSubTree(), features);
            } else {
                return getLeaf(treeNode.getGreatThanSubTree(), features);
            }
        } else {
            return treeNode;
        }
    }

    private static String convertIndexToVector(int leafSize, String lable, Integer[] result) {
        String[] finalVector = new String[leafSize];
        Arrays.fill(finalVector, "0");
        for (int index : result) {
            finalVector[index] = "1";
        }
        String newLine = lable + "," + String.join(",", finalVector) + "\n";
        return newLine;
    }

    public static String processIndex(String lable, Integer[] result) {
        String newLine = lable + "," + Arrays.stream(result).map(i -> i.toString()).collect(Collectors.joining(",")) + "\n";
        return newLine;
    }
}

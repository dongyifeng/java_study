package bigdata;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindRepeatedUrl {

    private List<Path> splitFile(String dataFileName, int count) {
        BufferedWriter[] writers = null;
        List<Path> smallFileNames = new ArrayList<>(count);
        try {
            writers = new BufferedWriter[count];
            for (int i = 0; i < count; i++) {
                Path path = Paths.get(dataFileName + "_" + i);
                writers[i] = Files.newBufferedWriter(path);
                smallFileNames.add(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Stream<String> stream = Files.lines(Paths.get(dataFileName))) {
            for (String line : stream.collect(Collectors.toList())) {
                int index = line.hashCode() % count;
                writers[index].write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for (int i = 0; i < count; i++) {
                    writers[i].flush();
                    writers[i].close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return smallFileNames;
        }
    }

    public Set<String> findRepeatedUrl(Path path) {
        try (Stream<String> stream = Files.lines(path)) {
            Map<String, Long> collect = stream.collect(Collectors.groupingBy(x -> x, Collectors.counting()));
            return collect.entrySet().stream().filter(x -> x.getValue() > 1).map(x -> x.getKey()).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    public void findRepeatedUrl(String dataFileName, int fileSize) {
        if (fileSize <= 0) {
            return;
        }
        long fileLineCount = getFileLineCount(dataFileName);
        Long count = fileLineCount % fileSize == 0 ? fileLineCount / fileSize : fileLineCount / fileSize + 1;

        List<Path> pathList = splitFile(dataFileName, count.intValue());
        BufferedWriter resultPath = null;

        try {
            resultPath = Files.newBufferedWriter(Paths.get(dataFileName + "_repeated_url"));
            for (Path path : pathList) {
                Set<String> repeatedUrl = findRepeatedUrl(path);
                for (String url : repeatedUrl) {
                    resultPath.write(url + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultPath.flush();
                resultPath.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private long getFileLineCount(String dataFileName) {
        try {
            return Files.lines(Paths.get(dataFileName)).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        FindRepeatedUrl findRepeatedUrl = new FindRepeatedUrl();
        findRepeatedUrl.findRepeatedUrl("/Users/dongyf/Desktop/word_a", 100);
    }
}

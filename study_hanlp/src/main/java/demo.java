import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

public class demo {
    public static void main(String[] args) {
//        CustomDictionary.add("长和");
//        CustomDictionary.add("时长","nr 1024");
//        CustomDictionary.add("时长");

//        final List<Term> termList = StandardTokenizer.segment("用户时长和登录频率远不及小视频应用");
//        System.out.println(termList);
//        System.out.println(CustomDictionary.get("2"));


//        System.out.println(JSON.toJSONString(StandardTokenizer.segment("用户时长和登录频率远不及小视频应用")));
//        System.out.println(CustomDictionary.get("时长"));
//        JSON.toJSONString(StandardTokenizer.segment(text))
        CustomDictionary.add("的");

        String text = "海康威视";
//        System.out.println(indexTokenizer(text));
        System.out.println(StandardTokenizer.segment(text));

    }

    // 索引分词
    public static List<Term> indexTokenizer(String text) {
        return IndexTokenizer.segment(text);
    }
}
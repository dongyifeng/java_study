package simhash;

import java.util.List;
import java.util.Set;

public interface IWordSeg {
    List<String> tokens(String doc);

    List<String> tokens(String doc, Set<String> stopWords);
}

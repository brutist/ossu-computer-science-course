import java.util.ArrayList;
import java.util.HashMap;
import edu.duke.FileResource;
import java.io.File;

public class WordsInFiles {
    private HashMap<String,ArrayList<String>> wordMap;

    public WordsInFiles() {
        wordMap = new HashMap<String,ArrayList<String>>();
    }
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        for (String word : fr.words()) {
            if (wordMap.containsKey(word)) {
                ArrayList<String> val = wordMap.get(word);
                val.add(f.getName());
                wordMap.put(word,val);
            }
            else {
                ArrayList<String> fname = new ArrayList<String>();
                fname.add(f.getName());
                wordMap.put(word,fname);
            }
        }
    }
}

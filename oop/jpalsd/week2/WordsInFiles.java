import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import java.io.File;

public class WordsInFiles {
    private HashMap<String,ArrayList<String>> wordMap;
    private String[] testFiles;
    private String source;

    public WordsInFiles() {
        wordMap = new HashMap<String,ArrayList<String>>();
        //source = "gladlibtexts/";
        //testFiles = new String[]{source+"brief1.txt",source+"brief2.txt",
        //                        source+"brief3.txt",source+"brief4.txt"};
        source = "literaturelib/";
        testFiles = new String[]{source+"caesar.txt",source+"hamlet.txt",
                                 source+"likeit.txt",source+"macbeth.txt",
                                 source+"romeo.txt"};
    }
    private void addWordsFromFile(File f) {
        String filename = source + f.getName();
        FileResource fr = new FileResource(filename);
        for (String word : fr.words()) {
            if (wordMap.containsKey(word)) {
                ArrayList<String> val = wordMap.get(word);
                String fname = f.getName();
                if (!val.contains(fname)) {
                    val.add(f.getName());
                    wordMap.put(word,val);
                }
            }
            else {
                ArrayList<String> fname = new ArrayList<String>();
                fname.add(f.getName());
                wordMap.put(word,fname);
            }
        }
    }
    public void buildWordFileMap() {
        wordMap.clear();
        // use this if you want to pick the files
        /* DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }                                                  */
        for (int i = 0; i < testFiles.length; i++) {
            File f = new File(testFiles[i]);
            addWordsFromFile(f);
        }
    }
    public int maxNumber() {
        int maxOccurence = 0;
        for (String key : wordMap.keySet()) {
            int currOccurence = wordMap.get(key).size();
            if (currOccurence > maxOccurence) {
                maxOccurence = currOccurence;
            }
        }
        return maxOccurence;
    }
    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> wordList = new ArrayList<>();
        for (String key : wordMap.keySet()) {
            int currNum = wordMap.get(key).size();
            if (currNum == number) {
                wordList.add(key);
            }
        }
        return wordList;
    }
    public void printFilesIn(String word) {
        ArrayList<String> filesWordIn = wordMap.get(word);
        for (int i = 0; i < filesWordIn.size(); i++) {
            System.out.println(filesWordIn.get(i));
        }
    }
    public void tester() {
        buildWordFileMap();
        /*int maxOccurence = maxNumber();
        for (String key : wordMap.keySet()) {
            int currOccurence = wordMap.get(key).size();
            if (currOccurence >= maxOccurence) {
                System.out.println(String.format("'%s' appeared %d times in the files",key,currOccurence));
                //printFilesIn(key);
            }
        }*/
        int minFiles = 5;
        ArrayList<String> wordsInFiveFiles = wordsInNumFiles(minFiles);
        System.out.println(String.format("No. of words in %d files: %d",minFiles,wordsInFiveFiles.size()));
        System.out.println("All words in files");
        /*for (String key : wordMap.keySet()) {
            System.out.println(String.format("'%s' appeared %d times in the files",key,wordMap.get(key).size()));
            printFilesIn(key);
        } 
        printFilesIn("sad"); */
    }
    public static void main(String[] args) {
        WordsInFiles wf = new WordsInFiles();
        wf.tester();
    }
}

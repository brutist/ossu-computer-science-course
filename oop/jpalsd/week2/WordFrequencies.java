package jpalsd.week2;
import java.util.ArrayList;

import edu.duke.FileResource;

public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer>myFreqs;

    public WordFrequencies(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    public void findUnique() {
        myWords.clear();
        myFreqs.clear();

        FileResource fr = new FileResource();
        for (String word : fr.words()) {
            String currWord = word.toLowerCase();
            if (myWords.contains(currWord)) {
                int indexOfWord = myWords.indexOf(currWord);
                int value = myFreqs.get(indexOfWord);
                myFreqs.set(indexOfWord, value+1);
            }
            else {
                myWords.add(currWord);
                myFreqs.add(1);
            }
        }
    }
    // assumes myFreqs has some elements in it before being called
    public int findIndexOfMax() {
        int largestIndex = 0;
        int maxValue = myFreqs.get(0);
        for (int i = 0; i < myFreqs.size(); i++) {
            int currValue = myFreqs.get(i);
            if (currValue > maxValue) {
                largestIndex = i;
                maxValue = currValue;
            }
        }
        return largestIndex;
    }
    public void tester() {
        findUnique();
        int maxIndex = findIndexOfMax();
        for (int i = 0; i < myWords.size(); i++) {
            System.out.println(String.format("%d %s", myFreqs.get(i), myWords.get(i)));
        }
        System.out.println(String.format("The word that occurs most often and its count are: %s %d", myWords.get(maxIndex),myFreqs.get(maxIndex)));
    }
    public static void main(String[] args) {
        WordFrequencies wf = new WordFrequencies();
        //wf.tester();

        wf.findUnique();
        int uniqueWords = wf.myWords.size();
        String mostCommonWord = wf.myWords.get(wf.findIndexOfMax());
        int mostCommonWordTimes = wf.myFreqs.get(wf.findIndexOfMax());
        System.out.println(String.format("No. of unique words %d Most common word: %s", uniqueWords, mostCommonWord));
        System.out.println(String.format("The most common occured %s times", mostCommonWordTimes));
    }
}

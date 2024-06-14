package jpalsd.week1;
import edu.duke.*;

public class WordLengths {
    public void countWordLengths(FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            int wordLength = word.length();
            char firstChar = word.charAt(0);
            char lastChar = word.charAt(wordLength-1);
            if (!Character.isLetter(lastChar)) {
                word = word.substring(0,wordLength-1);
            }
            if (!Character.isLetter(firstChar)) {
                word = word.substring(1);
            }
            wordLength = word.length();
            if (wordLength > counts.length) {
                counts[counts.length] += 1;
            }
            else {
                counts[wordLength] += 1;
            }
        }
    }
    // Assumes that the int[] values has entries
    public int indexOfMax(int[] values) {
        int largestVal = 0;
        int largestValIndex = 0;
        for (int i = 0; i < values.length; i++) {
            int currentVal = values[i];
            if (currentVal > largestVal) {
                largestVal = currentVal;
                largestValIndex = i;
            }
        }
        return largestValIndex;
    }
    public void testCountWordLengths() {
        FileResource resource = new FileResource();
        int[] wordLengths = new int[31];
        countWordLengths(resource, wordLengths);

        for (int i = 0; i < wordLengths.length; i++) {
            if (wordLengths[i] > 0) {
                System.out.print(String.format("%d words of length %d:", wordLengths[i],i));
                for (String word : resource.words()) {
                    int wordLength = word.length();
                    char firstChar = word.charAt(0);
                    char lastChar = word.charAt(wordLength-1);
                    if (!Character.isLetter(lastChar)) {
                        word = word.substring(0,wordLength-1);
                    }
                    if (!Character.isLetter(firstChar)) {
                        word = word.substring(1);
                    }
                    wordLength = word.length();
                    if (wordLength == i) {
                        //System.out.print(String.format(" %s ", word));
                    }
                }
                System.out.println("\n");
            }
        }
    }
    public void testIndexOfMax() {
        int[][] inputArr = {new int[]{1,3,4,5},
                            new int[]{4,8,4,5},
                            new int[]{8,8,8,8},
                            new int[]{5,1,4,5},};
        int[] outputArr = {3,1,0,0};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr.length; i++) {
            int[] currInput = inputArr[i];
            int currOutput = outputArr[i];
            int answer = indexOfMax(currInput);
            if (currOutput != answer) { 
                System.out.println(String.format("Test %d - Failed", i+1)); 
                testResult = false;
            }
        }
        if (testResult) {
            System.out.println(String.format("indexOfMax Tests - ALL %d TESTS PASSED",i));
        }
    }
    public static void main(String[] args) {
        WordLengths w = new WordLengths();
        w.testCountWordLengths();
        //w.testIndexOfMax();
    }
}

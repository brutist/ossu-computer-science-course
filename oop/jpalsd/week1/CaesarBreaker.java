package jpalsd.week1;
import java.util.Arrays;

public class CaesarBreaker {
    public int[] countLetters(String letters) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] countPerLetter = new int[26];
        for (int i = 0; i < letters.length(); i++) {
            char currChar = letters.charAt(i);
            int currCharPos = alphabet.indexOf(Character.toLowerCase(currChar));
            countPerLetter[currCharPos] += 1;
        }
        return countPerLetter;
    }
    public int maxIndex(int[] values) {
        WordLengths w = new WordLengths();
        int maxIndex = w.indexOfMax(values);
        return maxIndex;
    }
    public String decrypt(String encrypted) {
        CaesarCipher cc = new CaesarCipher();

        int[] freqs = countLetters(encrypted);
        int maxPos = maxIndex(freqs);
        int encKey = maxPos - 4;
        if (maxPos < 4) {
            encKey = 26 - (4-maxPos);
        }
        return cc.encrypt(encrypted, 26-encKey);
    }
    public void testCountLetters() {
        String[] inputArr = {"abcdefghijklmnopqrstuvwxyz",
                             "aacdefghijklmnopqrstuvwxyz"};
        int[][] outputArr = {new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                             new int[]{2,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr.length; i++) {
            String currInput = inputArr[i];
            int[] currOutput = outputArr[i];
            int[] answer = countLetters(currInput);
            if (Arrays.equals(currOutput, answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currOutput)); 
                testResult = false;
            }
        }
        if (testResult) {
            System.out.println(String.format("countLetters Tests - ALL %d TESTS PASSED",i));
        }
    }
    public static void main(String[] args) {
        CaesarBreaker cb = new CaesarBreaker();
        cb.testCountLetters();
    }
}



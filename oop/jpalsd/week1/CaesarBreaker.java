package jpalsd.week1;
import java.util.Arrays;

import edu.duke.FileResource;

public class CaesarBreaker {
    public int[] countLetters(String letters) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] countPerLetter = new int[26];
        for (int i = 0; i < letters.length(); i++) {
            char currChar = Character.toLowerCase(letters.charAt(i));
            if (Character.isLetter(currChar)) {
                int currCharPos = alphabet.indexOf(currChar);
                countPerLetter[currCharPos] += 1;
            }
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
    public String halfOfString(String message, int start) {
        String result = "";
        for(int i = start; i < message.length(); i+=2) {
            result += message.charAt(i);
        }
        return result;
    }
    public int getKey(String s) {
        int [] freqs = countLetters(s);
        int maxPos = maxIndex(freqs);
        int key = maxPos -4;
        if (maxPos < 4) {
            key = 26 - (4-maxPos);
        }
        return key;
    }
    public String decryptTwoKeys(String encrypted) {
        String oddEncrypted = halfOfString(encrypted,0);
        String evenEncrypted = halfOfString(encrypted,1);
        int oddKey = getKey(oddEncrypted);
        int evenKey = getKey(evenEncrypted);

        // print the two keys found
        System.out.println(String.format("Odd letters key: %d", oddKey));
        System.out.println(String.format("Even letters key: %d", evenKey));

        CaesarCipher cc = new CaesarCipher();
        String decryptedMessage = cc.encryptTwoKeys(encrypted,26-oddKey,26-evenKey);

        return decryptedMessage;
    }
    public void testCountLetters() {
        String[] inputArr = {"abcdefghijklmnopqrstuvwxyz",
                             "aacdefghijklmnopqrstuvwxyz",
                             "aacdefghijklmnopqrstuvwxyz!"};
        int[][] outputArr = {new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                             new int[]{2,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                             new int[]{2,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr.length; i++) {
            String currInput = inputArr[i];
            int[] currOutput = outputArr[i];
            int[] answer = countLetters(currInput);
            if (!Arrays.equals(currOutput, answer)) { 
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
    public void printCounts(int[] counts) {
        for (int i = 0; i < counts.length; i++) {
            System.out.print(String.format(" %d ", counts[i]));
        }
        System.out.println("\n");
    }
    public void testDecrypt(){
        String[] outputArr = {"eeeeeeeeeeeeeeeees"};
        String[] inputArr1 = {"gggggggggggggggggu"};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            String answer = outputArr[i];
            String currResult = decrypt(currInput1);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
                testResult = false;
            }
        }
        if (testResult) {
            System.out.println(String.format("decrypt Tests - ALL %d TESTS PASSED",i));
        }
    }
    public void testHalfOfString(){
        String[] inputArr1 = {"abcdef","klmnop","Systematic"};
        int[] inputArr2 = {1,0,5};
        String[] outputArr = {"bdf","kmo","mtc"};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            int currInput2 = inputArr2[i];
            String answer = outputArr[i];
            String currResult = halfOfString(currInput1,currInput2);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
                testResult = false;
            }
        }
        if (testResult) {
            System.out.println(String.format("halfOfString Tests - ALL %d TESTS PASSED",i));
        }
    }
    public void testGetKey(){
        String[] inputArr1 = {"gggggggggggggggggu"};
        int[] outputArr = {2};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            int answer = outputArr[i];
            int currResult = getKey(currInput1);
            if (currResult != answer) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
                testResult = false;
            }
        }
        if (testResult) {
            System.out.println(String.format("getKey Tests - ALL %d TESTS PASSED",i));
        }
    }
    public void testDecryptTwoKeys() {
        String[] inputArr1 = {"Gwpv c vbuq pvokki yfve iqqu qc bgbgbgbgbgbgbgbgbu"};
        String[] outputArr = {"Just a test string with lots of eeeeeeeeeeeeeeeees"};
        boolean testResult = true;
        int i = 0;
        for (; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            String answer = outputArr[i];
            String currResult = decryptTwoKeys(currInput1);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
                testResult = false;
            }
        }
        if (testResult) {
            System.out.println(String.format("getKey Tests - ALL %d TESTS PASSED",i));
        }
    }
    public static void main(String[] args) {
        CaesarBreaker cb = new CaesarBreaker();
        cb.testCountLetters();
        cb.testDecrypt();
        cb.testHalfOfString();
        cb.testGetKey();
        cb.testDecryptTwoKeys();
        // choose mysteryTextTwoKeys.txt
        String mysteryText = new FileResource().asString();
        System.out.println(cb.decryptTwoKeys(mysteryText));
    }
}



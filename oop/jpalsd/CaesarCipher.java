package jpalsd;

import java.util.Arrays;
import java.util.List;

public class CaesarCipher {
    public String encrypt(String input, int key) {
        String output = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String cryptographicKey = alphabet.substring(key) + alphabet.substring(0,key);
        for (int i = 0; i < input.length(); i++) {
            char currChar = input.charAt(i);
            char shiftedChar = currChar;
            if (Character.isLetter(currChar)) {
                int indexCurrChar = alphabet.indexOf(Character.toLowerCase(currChar));
                shiftedChar = cryptographicKey.charAt(indexCurrChar);
            }
            if (Character.isUpperCase(currChar)) {
                output += Character.toUpperCase(shiftedChar);
            }
            else {
                output += shiftedChar;
            }
        }
        return output;
    }
    public String encryptTwoKeys(String input, int key1, int key2) {
        String output = "";
        String oddChars = "";
        String evenChars = "";

        for (int i = 0; i < input.length(); i++) {
            if ((i+1) % 2 == 0) {
                evenChars += input.charAt(i);
            }
            else {
                oddChars += input.charAt(i);
            }
        }
        String encryptedOddChars = encrypt(oddChars, key1);
        String encryptedEvenChars = encrypt(evenChars, key2);

        for (int i = 0; i< input.length(); i++) {
            if ((i+1) % 2 == 0) {
                output += encryptedEvenChars.charAt(0);
                encryptedEvenChars = encryptedEvenChars.substring(1);
            }
            else {
                output += encryptedOddChars.charAt(0);
                encryptedOddChars = encryptedOddChars.substring(1);
            }
        }
        return output;
    }
    public void testEncrypt() {
        String[] inputArr1 = {"FIRST LEGION ATTACK EAST FLANK!",
                              "First Legion",
                              "First Legion",
                              "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!"};
        Integer[] inputArr2 = {23,23,17,15};
        String[] outputArr = {"CFOPQ IBDFLK XQQXZH BXPQ CIXKH!",
                              "Cfopq Ibdflk",
                              "Wzijk Cvxzfe",
                              "Pi cddc qt xc iwt rdcutgtcrt gddb lxiw ndjg wpi dc udg p hjgegxht epgin. NTAA ADJS!"};
        List<String> inputList1 = Arrays.asList(inputArr1);
        List<Integer> inputList2 = Arrays.asList(inputArr2);
        List<String> outputList = Arrays.asList(outputArr);
        
        for (int i = 0; i < inputList1.size(); i++) {
            String currInput1 = inputList1.get(i);
            int currInput2 = inputList2.get(i);
            String answer = outputList.get(i);
            String currResult = encrypt(currInput1, currInput2);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
            }
        }
        System.out.println("encrypt Tests Completed");
    }
    public void testEncryptTwoKeys() {
        String[] inputArr1 = {"First Legion",
                              "AbAbAb",
                              "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!"};
        Integer[] inputArr2 = {23,1,8};
        Integer[] inputArr3 = {17,2,21};
        String[] outputArr = {"Czojq Ivdzle",
                              "BdBdBd",
                              "Io iwjv jz dv bcm kjvammmikz mwju edbc twpz pvb wi awm v ncmxmqnm xvzog. TMGT TJCY!"};
        List<String> inputList1 = Arrays.asList(inputArr1);
        List<Integer> inputList2 = Arrays.asList(inputArr2);
        List<Integer> inputList3 = Arrays.asList(inputArr3);
        List<String> outputList = Arrays.asList(outputArr);
        
        for (int i = 0; i < inputList1.size(); i++) {
            String currInput1 = inputList1.get(i);
            int currInput2 = inputList2.get(i);
            int currInput3 = inputList3.get(i);
            String answer = outputList.get(i);
            String currResult = encryptTwoKeys(currInput1, currInput2,currInput3);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
            }
        }
        System.out.println("encryptTwoKeys Tests Completed");
    }
    public static void main(String[] args) {
        CaesarCipher c = new CaesarCipher();
        c.testEncrypt();
        c.testEncryptTwoKeys();
    }
}

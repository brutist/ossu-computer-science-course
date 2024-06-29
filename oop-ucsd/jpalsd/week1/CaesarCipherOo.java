package jpalsd.week1;

public class CaesarCipherOo {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;
    public CaesarCipherOo(int key) {
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
        mainKey = key;
    }
    public String encrypt(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            char currChar = input.charAt(i);
            char shiftedChar = currChar;
            if (Character.isLetter(currChar)) {
                int indexCurrChar = alphabet.indexOf(Character.toLowerCase(currChar));
                shiftedChar = shiftedAlphabet.charAt(indexCurrChar);
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
    public String decrypt(String input) {
        CaesarCipher cc = new CaesarCipher();

        return cc.encrypt(input, 26-mainKey);
    }
    public void testEncrypt() {
        String[] inputArr1 = {"FIRST LEGION ATTACK EAST FLANK!",
                              "First Legion"};
        String[] outputArr = {"CFOPQ IBDFLK XQQXZH BXPQ CIXKH!",
                              "Cfopq Ibdflk"};
        boolean testResult = true;
        int i = 0;   
        for (; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            String answer = outputArr[i];
            String currResult = encrypt(currInput1);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
            }
        }
        if (testResult) {
            System.out.println(String.format("encrypt: %d Tests Completed",i));
        }
    }
    public void testDecrypt(){
        String[] inputArr1 = {"CFOPQ IBDFLK XQQXZH BXPQ CIXKH!"};
        String[] outputArr = {"FIRST LEGION ATTACK EAST FLANK!"};
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
    public static void main(String[] args) {
        CaesarCipherOo coo = new CaesarCipherOo(23);
        coo.testEncrypt();
        coo.testDecrypt();
    }
}

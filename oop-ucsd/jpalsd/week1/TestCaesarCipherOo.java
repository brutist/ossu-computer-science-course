package jpalsd.week1;
import edu.duke.FileResource;

public class TestCaesarCipherOo extends CaesarBreaker {
    public void simpleTests() {
        String message = new FileResource().asString();
        CaesarCipherOo coo = new CaesarCipherOo(18);

        String encryptedMessage = coo.encrypt(message);
        String decryptedString = coo.decrypt(encryptedMessage);
        String decryptedString2 = breakCaesarCipher(message);

        // print the message, encrypted and decrypted strings
        System.out.println(String.format("Original Message: %s", message));
        System.out.println(String.format("Encrypted Message: %s", encryptedMessage));
        System.out.println(String.format("Decrypted Message: %s", decryptedString));
        System.out.println(String.format("breakCaesarCipher Message: %s", decryptedString2));
        
        int i = 1;
        if (!message.equals(decryptedString) || !decryptedString2.equals(decryptedString)) {
            System.out.println(String.format("Test %d - Failed", i));
            System.out.println(String.format("Result should be: %s", message));
            System.out.println(String.format("Instead got: %s", decryptedString));
        }
        else {
            System.out.println(String.format("simpleTest %d - Successful", i));
        }
    }
    public String breakCaesarCipher(String input) {
        int key = getKey(input);
        CaesarCipherOo coo = new CaesarCipherOo(key);

        return coo.decrypt(input);
    }
    public static void main(String[] args) {
        TestCaesarCipherOo test = new TestCaesarCipherOo();
        test.simpleTests();
    }
}

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

public class TesterVigenere {
    public void testBreakVigenere() {
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
    public void testTryKeyLength() {
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource();
        String message = fr.asString();
        int[] key = vb.tryKeyLength(message, 4, 'e');

        for (int i = 0; i < key.length; i++) {
            System.out.printf(" %d ", key[i]);
        }
    }
    
    public void testMostCommonCharIn() {
        char[] answers = new char[] {'e','e','e','e','e','a','a','a'};
        String source = "dictionaries/";
        String[] Testfiles = new String[] {source+"Danish",source+"Dutch",source+"English",
                                            source+"French",source+"German",source+"Italian",
                                            source+"Portuguese",source+"Spanish"};
        int i = 0;
        boolean testResult = true;
        for (; i < answers.length; i++) {
            VigenereBreaker breaker = new VigenereBreaker();
            char currResult = breaker.mostCommonCharIn(breaker.readDictionary(new FileResource(Testfiles[i])));
            if (currResult != answers[i]) {
                System.out.printf("testMostCommonCharIn Test %d failed\n", i + 1);
                System.out.printf("Result should be %s but got %s instead\n", answers[i],currResult);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testMostCommonCharIn %d tests successful \n", i);
        }
    }

    public void breakVigenereToFile() {
        DirectoryResource dr = new DirectoryResource();
        String path = "oop/jpalsd/week4/DecryptedMessages/";
        VigenereBreaker breaker = new VigenereBreaker();
        HashMap<String,HashSet<String>> allLangDictsMap = breaker.createLangDictionaries();
        for (File f : dr.selectedFiles()) {
            String currFileName = f.getName();
            String encryptedMessage = new FileResource(f).asString();
            String decryptedMessage = breaker.breakForAllLangs(encryptedMessage, allLangDictsMap);

            try {
                FileWriter wr = new FileWriter(path+currFileName);
                wr.write(decryptedMessage);
                wr.close();
            }
            catch (Exception IOException) {
                IOException.printStackTrace();
            }
        }
    }
    
    public void testAidaDecryption() {
        String encrypted = new FileResource().asString();
        
        VigenereBreaker breaker = new VigenereBreaker();
        HashSet<String> italianDictionary = breaker.readDictionary(new FileResource("dictionaries/Italian"));
        String decrypted = breaker.breakForLanguage(encrypted, italianDictionary);

        System.out.println(decrypted);
    }
    public static void main(String[] args) {
        TesterVigenere t = new TesterVigenere();
        //t.testBreakVigenere();
        //t.testMostCommonCharIn();
        t.testAidaDecryption();
        //t.breakVigenereToFile();
    }
}

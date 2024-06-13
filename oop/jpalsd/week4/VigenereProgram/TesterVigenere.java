import edu.duke.FileResource;

public class TesterVigenere {
    public void testBreakVigenere() {
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere("English");
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
    public static void main(String[] args) {
        TesterVigenere t = new TesterVigenere();
        t.testBreakVigenere();
        //t.testTryKeyLength();
    }
}

import edu.duke.FileResource;

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

    public static void main(String[] args) {
        TesterVigenere t = new TesterVigenere();
        t.testBreakVigenere();
        //t.testMostCommonCharIn();
    }
}

package week2.StringsFirstAssignments;

public class Part3 {
    
    public boolean twoOccurrences(String stringA, String stringB) {
        boolean present = false;
        int firstOccurence = stringB.indexOf(stringA);

        if (firstOccurence < 0) {
            return present;
        }
        else if (stringB.indexOf(stringA,firstOccurence+stringA.length()) < 0) {
            return present;
        }
        else {
            return true;
        }
    }
    public void testing() {
        boolean testA = twoOccurrences("by", "A story by Abby Long");
        boolean answerA = true;
        if (testA == answerA) { System.out.println("TEST 1 - PASSED"); }
        else { System.out.println("TEST 1 - FAILED"); }
        
        testA = twoOccurrences("a", "banana");
        if (testA == answerA) { System.out.println("TEST 2 - PASSED"); }
        else { System.out.println("TEST 2 - FAILED"); }

        testA = twoOccurrences("atg", "ctgtatgta");
        answerA = false;
        if (testA == answerA) { System.out.println("TEST 3 - PASSED"); }
        else { System.out.println("TEST 3 - FAILED"); }

        testA = twoOccurrences("kiss", "ctgtatgta");
        if (testA == answerA) { System.out.println("TEST 4 - PASSED"); }
        else { System.out.println("TEST 4 - FAILED"); }

        String testB = lastPart("an","banana");
        String answerB = "ana";
        if (testB.equals(answerB)) { System.out.println("TEST 5 - PASSED"); }
        else { System.out.println("TEST 5 - FAILED"); }

        testB = lastPart("zoo","forest");
        answerB = "forest";
        if (testB.equals(answerB)) { System.out.println("TEST 6 - PASSED"); }
        else { System.out.println("TEST 6 - FAILED"); }

    }   
    public String lastPart(String stringA, String stringB) {
        int occurIndex = stringB.indexOf(stringA);
        String result = stringB;
        if (occurIndex > -1) {
            result = stringB.substring(occurIndex+stringA.length(), stringB.length());
        }
        return result;
    }
    public static void main(String[] args) {
        Part3 test = new Part3();
        test.testing();
    }
}

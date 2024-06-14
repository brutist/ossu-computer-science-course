package jpsps.week2.StringsSecondAssignments;

public class Part2 {
    public int howMany(String stringA, String stringB) {
        int occurence = 0;
        int lengthA = stringA.length();
        int startIndex = stringB.indexOf(stringA);
        while (startIndex != -1) {
            occurence += 1;
            startIndex = stringB.indexOf(stringA,startIndex+lengthA);
        }
        return occurence;
    }
    public void testHowMany() {
        String stringA = "AA";
        String stringB = "ATAAAA";
        int answer = 2;
        int result = howMany(stringA, stringB);
        if (result != answer) { System.out.println("Test 1 - Failed"); }

        stringA = "GAA";
        stringB = "ATGAACGAATTGAATC";
        answer = 3;
        result = howMany(stringA, stringB);
        if (result != answer) { System.out.println("Test 2 - Failed"); }

        stringA = "A";
        stringB = "ATGAACGAATTGAATC";
        answer = 7;
        result = howMany(stringA, stringB);
        if (result != answer) { System.out.println("Test 3 - Failed"); }

        stringA = "AA";
        stringB = "AAACCCAAAAAAACC";
        answer = 4;
        result = howMany(stringA, stringB);
        if (result != answer) { System.out.println("Test 4 - Failed"); }

        System.out.println("All Test Completed");
    }
    public static void main(String[] args) {
        Part2 test = new Part2();
        test.testHowMany();
    }
}

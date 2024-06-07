package jpsps.week2.StringsThirdAssignments;

public class Part2 {
    // Assumes there is at least 1 C and 1 G
    public float cgRatio(String dna) {
        int countC = 0;
        int countG = 0;
        dna = dna.toUpperCase();
        for (int i = 0; i < dna.length(); i++) {
            char letter = dna.charAt(i);

            if (letter == 'C') {
                countC += 1;                
            }
            else if (letter == 'G') {
                countG += 1;
            }
        }
        return (float)countC/countG;
    }
    public int countCTG(String dna) {
        int countGTG = 0;
        int startIndex = dna.indexOf("CTG");
        while (startIndex != -1) {
            countGTG += 1;
            startIndex = dna.indexOf("CTG", startIndex+3);
        }
        return countGTG;
    }
    public void testcgRatio() {
        String dna = "CGC";
        Float result = cgRatio(dna);
        Float answer = 2.0f;
        if (! (answer.equals(result))) { System.out.println("Test 1 - Failed"); }

        dna = "CGCG";
        result = cgRatio(dna);
        answer = 1.0f;
        if (! (answer.equals(result))) { System.out.println("Test 2 - Failed"); }

        dna = "ATGCTG";
        result = cgRatio(dna);
        answer = 0.5f;
        if (! (answer.equals(result))) { System.out.println("Test 3 - Failed"); }

        dna = "ATGGCTG";
        result = cgRatio(dna);
        answer = (1/3.0f);
        if (! (answer.equals(result))){ System.out.println("Test 4 - Failed"); }

        System.out.println("cgRatio Tests Completed");
    }
    public void testcountCTG() {
        String dna = "GTC";
        int result = countCTG(dna);
        int answer = 0;
        if (answer != result) { System.out.println("Test 1 - Failed"); }

        dna = "CTG";
        result = countCTG(dna);
        answer = 1;
        if (answer != result) { System.out.println("Test 2 - Failed"); }

        dna = "CTGCTGCTG";
        result = countCTG(dna);
        answer = 3;
        if (answer != result) { System.out.println("Test 3 - Failed"); }

        dna = "CTGATGCTG";
        result = countCTG(dna);
        answer = 2;
        if (answer != result) { System.out.println("Test 4 - Failed"); }

        dna = "CGATGTG";
        result = countCTG(dna);
        answer = 0;
        if (answer != result) { System.out.println("Test 5 - Failed"); }

        System.out.println("testcountCTG Tests Completed");
    }

    public static void main(String[] args) {
        Part2 t = new Part2();
        t.testcgRatio();
        t.testcountCTG();
    }
}

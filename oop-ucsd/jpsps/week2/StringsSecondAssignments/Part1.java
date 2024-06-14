package jpsps.week2.StringsSecondAssignments;

public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int dnaLength = dna.length();
        int currIndex = dna.indexOf(stopCodon);
        while (currIndex != -1) {
            boolean validGene = ((currIndex+3) - startIndex) % 3 == 0;
            if (validGene) {
                return currIndex;
            } 
            else {
                currIndex = dna.indexOf(stopCodon,currIndex+1);
            }
        }
        return dnaLength;
    }
    public void testFindStopCodon() {
        //            0123456789
        String dna = "ATAATATGAC";
        int startIndex = 0;
        String stopCodon = "TAA";
        int result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 10) { System.out.println("Test 1 - Failed");}

        dna = "ATACACTAAGAC";
        startIndex = 0;
        stopCodon = "TAA";
        result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 6) { System.out.println("Test 2 - Failed");}

        dna = "ATACACTAGAC";
        startIndex = 0;
        stopCodon = "TAA";
        result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 11) { System.out.println("Test 3 - Failed");}

        dna = "ATACACTAAGAC";
        startIndex = 7;
        stopCodon = "TAA";
        result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 12) { System.out.println("Test 4 - Failed");}

        dna = "ATACACTAAGAC";
        startIndex = 5;
        stopCodon = "TAA";
        result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 12) { System.out.println("Test 5 - Failed");}

        dna = "ATACACTAAGAC";
        startIndex = 3;
        stopCodon = "TAA";
        result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 6) { System.out.println("Test 6 - Failed");}

        dna = "ATACACTAAGAC";
        startIndex = 5;
        stopCodon = "TGA";
        result = findStopCodon(dna, startIndex, stopCodon);
        if (result != 12) { System.out.println("Test 7 - Failed");}

        System.out.println("All Tests Completed");
    }
    public String findGene(String dna) {
        String gene = "";
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return gene;
        }
        
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");

        int endIndex = Math.min(taaIndex, Math.min(tagIndex,tgaIndex));
        
        if (endIndex == dna.length()) {
            return gene;
        }
        gene = dna.substring(startIndex, endIndex+3);
        return gene;
    }
    public void testFindGene() {
        String dna = "AACGCGTAATACGGT";
        String answer = "";
        String result = findGene(dna);
        if (result.equals(answer)) {System.out.println("TEST 1 - PASSED");}
        // else {System.out.println("TEST 1 - FAILED");}
       
        dna = "CGCGCGTCATACGGT";
        result = findGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 2 - PASSED");}
        // else {System.out.println("TEST 2 - FAILED");}

        dna = "TAAGCGTCATACGGT";
        result = findGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 3 - PASSED");}
        // else {System.out.println("TEST 3 - FAILED");}

        dna = "ATGGCGCATAATACGGT";
        result = findGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 4 - PASSED");}
        // else {System.out.println("TEST 4 - FAILED");}

        dna = "AAATGCCCTAGCTAGATTAAGAAACC";
        answer = "ATGCCCTAG";
        result = findGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 5 - PASSED");}
        // else {System.out.println("TEST 5 - FAILED");
        // System.out.println(result);}
    }
    public void printAllGenes(String dna) {
        int startIndex = dna.indexOf("ATG");
        while (startIndex != -1) {
            String gene = findGene(dna.substring(startIndex));
            System.out.println(gene);
            startIndex = dna.indexOf("ATG",startIndex + gene.length() + 1);
        }
    }
    public static void main(String[] args) {
        Part1 test = new Part1();
        test.testFindStopCodon();
        test.testFindGene();
        test.printAllGenes("ATGTGCCCCTAAGCTATGGATTAAGAAACC");
        test.printAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        test.printAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    }
}

package jpsps.week2.lecture;

public class FindGeneWhile {
    public String findGene(String dna) {
        String result = "";
        // Find first occurence of "ATG"
        int startIndex = dna.indexOf("ATG");

        // Find the "TAA" starting after the "ATG"
        int currIndex = dna.indexOf("TAA",startIndex+3);

        // As long as currIndex is not equal to -1 loop through the
        //      entire dna until you find a valid gene (endIndex-startIndex % 3 == 0)
        while (currIndex != -1) {
            boolean validGene = (((currIndex+3) - startIndex) % 3) == 0;
            if (validGene) {
                result = dna.substring(startIndex, currIndex+3);
                return result;
            }
            else {
                currIndex = dna.indexOf("TAA", currIndex+1);
            }
        }
        return result;
    }
    public void testing() {
        String dna = "ATGATAAACATAAGCTAACGATCA";
        String answer = "ATGATAAACATAAGCTAA";
        String result = findGene(dna);
        System.out.println("DNA given = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) { System.out.println("TEST 1 - PASSED"); }
        else { System.out.println("TEST 1 - FAILED"); }

        dna = "AATGCGTAATATGGT";
        answer = "";
        result = findGene(dna);
        System.out.println("DNA given = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) { System.out.println("TEST 2 - PASSED"); }
        else { System.out.println("TEST 2 - FAILED"); }


        dna = "AATGCTAGGGTAATATGGT";
        answer = "ATGCTAGGGTAA";
        result = findGene(dna);
        System.out.println("DNA given = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) { System.out.println("TEST 2 - PASSED"); }
        else { System.out.println("TEST 2 - FAILED"); }


        dna = "ATCCTATGCTTCGGCTGCTCTAATATGGT";
        answer = "ATGCTTCGGCTGCTCTAA";
        result = findGene(dna);
        System.out.println("DNA given = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) { System.out.println("TEST 2 - PASSED"); }
        else { System.out.println("TEST 2 - FAILED"); }

        dna = "TTATAA";
        answer = "";
        result = findGene(dna);
        System.out.println("DNA given = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) { System.out.println("TEST 2 - PASSED"); }
        else { System.out.println("TEST 2 - FAILED"); }
    }
    public static void main(String[] args) {
        FindGeneWhile test = new FindGeneWhile();
        test.testing();
    }
}

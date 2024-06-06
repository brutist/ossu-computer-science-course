package week2.StringsFirstAssignments;

public class Part2 {

    public String findSimpleGene(String dna, String startCodon, String stopCodon) {
        String result = "";
        // identify whether you're given uppercase or lowercase strings and 
        //  change the startCodon accordingly
        if (dna.toLowerCase().equals(dna)) {
            startCodon = startCodon.toLowerCase();
            stopCodon = stopCodon.toLowerCase();
        }
        else {
            dna = dna.toUpperCase();
            startCodon = startCodon.toUpperCase();
            stopCodon = stopCodon.toUpperCase();
        }

        int startIndex = dna.indexOf(startCodon);
        int endIndex = dna.indexOf(stopCodon,startIndex);
        boolean validStartIndex = startIndex > -1;
        boolean validGene = startIndex < endIndex && ((endIndex+3) - startIndex) % 3 == 0;
        if (validStartIndex && validGene) { 
            result = dna.substring(startIndex, endIndex+3);
        }
        return result;
    }
    public void testSimpleGene() {
        String startCodon = "ATG";
        String stopCodon = "TAA";

        String dna = "AACGCGTAATACGGT";
        String answer = "";
        String result = findSimpleGene(dna,startCodon,stopCodon);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 1 - PASSED");}
        else {System.out.println("TEST 1 - FAILED");}
       
        dna = "CGCGCGTCATACGGT";
        result = findSimpleGene(dna,startCodon,stopCodon);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 2 - PASSED");}
        else {System.out.println("TEST 2 - FAILED");}

        dna = "TAAGCGTCATACGGT";
        result = findSimpleGene(dna,startCodon,stopCodon);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 3 - PASSED");}
        else {System.out.println("TEST 3 - FAILED");}

        dna = "ATGGCGCATAATACGGT";
        result = findSimpleGene(dna,startCodon,stopCodon);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 4 - PASSED");}
        else {System.out.println("TEST 4 - FAILED");}

        dna = "AAATGCCCTAACTAGATTAAGAAACC";
        answer = "ATGCCCTAA";
        result = findSimpleGene(dna,startCodon,stopCodon);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 5 - PASSED");}
        else {System.out.println("TEST 5 - FAILED");
        System.out.println(result);}

        dna = "ATGGCGTCATAATACGGT".toLowerCase();
        answer = "ATGGCGTCATAA".toLowerCase();
        result = findSimpleGene(dna,startCodon,stopCodon);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 6 - PASSED");}
        else {System.out.println("TEST 6 - FAILED");
        System.out.println(result);}


    }
    public static void main(String[] args) {
        Part2 test = new Part2();
        test.testSimpleGene();
    }
}

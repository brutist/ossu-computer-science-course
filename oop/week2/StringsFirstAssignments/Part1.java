package week2.StringsFirstAssignments;

public class Part1 {
    
    public String findSimpleGene(String dna) {
        String result = "";
        int startIndex = dna.indexOf("ATG");
        int endIndex = dna.indexOf("TAA",startIndex);
        boolean validStartIndex = startIndex > -1;
        boolean validGene = startIndex < endIndex && ((endIndex+3) - startIndex) % 3 == 0;
        if (validStartIndex && validGene) { 
            result = dna.substring(startIndex, endIndex+3);
        }
        return result;
    }
    public void testSimpleGene() {
        String dna = "AACGCGTAATACGGT";
        String answer = "";
        String result = findSimpleGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 1 - PASSED");}
        else {System.out.println("TEST 1 - FAILED");}
       
        dna = "CGCGCGTCATACGGT";
        result = findSimpleGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 2 - PASSED");}
        else {System.out.println("TEST 2 - FAILED");}

        dna = "TAAGCGTCATACGGT";
        result = findSimpleGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 3 - PASSED");}
        else {System.out.println("TEST 3 - FAILED");}

        dna = "ATGGCGCATAATACGGT";
        result = findSimpleGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 4 - PASSED");}
        else {System.out.println("TEST 4 - FAILED");}

        dna = "ATGGCGTCATAATACGGT";
        answer = "ATGGCGTCATAA";
        result = findSimpleGene(dna);
        System.out.println("Given dna = " + dna + "\n" + "Answer = " + result);
        if (result.equals(answer)) {System.out.println("TEST 5 - PASSED");}
        else {System.out.println("TEST  - FAILED");
        System.out.println(result);}
    }
    public static void main(String[] args) {
        Part1 test =  new Part1();
        test.testSimpleGene();
    }
}

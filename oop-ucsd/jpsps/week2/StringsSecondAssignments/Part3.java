package jpsps.week2.StringsSecondAssignments;

public class Part3 {
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
    public void printAllGenes(String dna) {
        int startIndex = dna.indexOf("ATG");
        while (startIndex != -1) {
            String gene = findGene(dna.substring(startIndex));
            System.out.println(gene);
            startIndex = dna.indexOf("ATG",startIndex + gene.length() + 1);
        }
    }
    public int countGenes(String dna) {
        int noOfGenes = 0;
        int startIndex = dna.indexOf("ATG");
        while (startIndex != -1) {
            String gene = findGene(dna.substring(startIndex));
            if (gene.isEmpty()) {
                break;
            }
            startIndex = dna.indexOf("ATG",startIndex + gene.length() + 1);
            noOfGenes += 1;
        }
        return noOfGenes;        
    }
    public void testCountGenes() {
        String dna = "ATGTAAGATGCCCTAGT";
        int result = countGenes(dna);
        int answer = 2;
        if (result != answer) { System.out.println("Test 1 - Failed"); }

        dna = "ATGTGCCCCTAAGCTATGGATTAAGAAACC";
        result = countGenes(dna);
        answer = 2;
        if (result != answer) { System.out.println("Test 2 - Failed"); }

        dna = "ATGTGCCCCTAAGCTATGGTTAAGAAACC";
        result = countGenes(dna);
        answer = 1;
        if (result != answer) { System.out.println("Test 3 - Failed"); }

        dna = "ATGTCCCCTAAGCTATGGTTAAGAAACC";
        result = countGenes(dna);
        answer = 0;
        if (result != answer) { System.out.println("Test 4 - Failed"); }

        System.out.println("All Tests Completed");
    }
    public static void main(String[] args) {
        Part3 test = new Part3();
        test.testCountGenes();
    }
}

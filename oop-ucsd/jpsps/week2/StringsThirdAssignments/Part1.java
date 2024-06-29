package jpsps.week2.StringsThirdAssignments;

import edu.duke.FileResource;
import edu.duke.StorageResource;

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
    // Part 3 of StringThird Assignment
    public void processGenes(StorageResource sr) {
        String longestGene = "";
        float ratio = 0.35f;
        StorageResource longerThan9 = new StorageResource();
        int longerThan9counter = 0;
        StorageResource highCGRatio = new StorageResource();
        int highCGRatiocounter = 0;
        StorageResource longerThan60 = new StorageResource();
        int longerThan60counter = 0;

        int totalGenes = 0;

        for (String s : sr.data()) {
            int currLength = s.length();

            if (currLength > 9) {
                longerThan9.add(s);
                longerThan9counter += 1;
            }
            if (cgRatio(s) > ratio) {
                highCGRatio.add(s);
                highCGRatiocounter += 1;
            }
            if (currLength > longestGene.length()) {
                longestGene = s;
            }
            if (currLength > 60) {
                longerThan60.add(s);
                longerThan60counter += 1;
            }
            totalGenes += 1;
        }
        System.out.println("All longer than 9 characters genes");
        //printStorageResource(longerThan9);
        System.out.println("Total longer than 9 characters genes = " + longerThan9counter);

        System.out.println("All genes with C-G-ratio higher than 0.35");
        //printStorageResource(highCGRatio);
        System.out.println("Total with C-G-ratio higher than 0.35 = " + highCGRatiocounter);

        System.out.println("All longer than 60 characters genes");
        //printStorageResource(longerThan60);
        System.out.println("Total longer than 60 characters genes = " + longerThan60counter);

        System.out.println("Total genes = " + totalGenes);
    }
    public void printStorageResource(StorageResource sr) {
        for (String s : sr.data()) {
            System.out.println(s);
        }
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
    public StorageResource getAllGenes(String dna) {
        int startIndex = dna.indexOf("ATG");
        StorageResource geneList = new StorageResource();
        while (startIndex != -1) {
            String gene = findGene(dna.substring(startIndex));
            if (gene.isEmpty()) {
                break;
            }
            geneList.add(gene);
            startIndex = dna.indexOf("ATG",startIndex + gene.length() + 1);
        }
        return geneList;
    }
    public void testGetAllGenes(String dna) {
        StorageResource gene = getAllGenes(dna);
        for (String s : gene.data()) {
            System.out.println(s);
        }
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
    public void testProcessGenes() {
        // DNA has gene longer than 9 characters
        String dnaA = "ATGTGCCCCTAAGCTATAGGATAAGAAACC";  // ATGTGCCCCTAA

        // DNA no gene than 9 characters
        String dnaB = "ATGTAAAATGTAAT";                  // ATGTAA, ATGTAA

        // DNA has gene C-G-ratio is higher than 0.35
        String dnaC = "ATGCCCTAAAATGCCCTAAT";            // ATGCCCTAA, ATGCCCTAA

        // DNA has some gene whose C-G-ratio is lower than 0.35.
        String dnaD = "ATGTAAAATGTAAT";                  // ATGTAA, ATGTAA

        // DNA has some gene whose C-G-ratio is lower than 0.35.
        String dnaE = "ATGCCCTAAAATGTAAT";               // ATGCCCTAA, ATGTAA
    
        StorageResource sr1 = getAllGenes(dnaA);
        StorageResource sr2 = getAllGenes(dnaB);
        StorageResource sr3 = getAllGenes(dnaC);
        StorageResource sr4 = getAllGenes(dnaD);
        StorageResource sr5 = getAllGenes(dnaE);

        FileResource fr = new FileResource();
        String dnaF = fr.asString().toUpperCase();
        StorageResource sr6 = getAllGenes(dnaF);

        processGenes(sr1);
        System.out.println("\n");
        processGenes(sr2);
        System.out.println("\n");
        processGenes(sr3);
        System.out.println("\n");
        processGenes(sr4);
        System.out.println("\n");
        processGenes(sr5);
        System.out.println("\n");
        processGenes(sr6);
    }
    public static void main(String[] args) {
        Part1 t = new Part1();
        t.printAllGenes("ATGTGCCCCTAAGCTATGGATTAAGAAACC");
        t.testGetAllGenes("ATGTGCCCCTAAGCTATGGATTAAGAAACC");
        System.out.println("\n");

        t.printAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        t.testGetAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        System.out.println("\n");

        t.printAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
        t.testGetAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
        System.out.println("\n");

        t.testcgRatio();
        t.testcountCTG();

        t.testProcessGenes();


    }
}

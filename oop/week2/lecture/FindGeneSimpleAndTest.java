package week2.lecture;

public class FindGeneSimpleAndTest {

    public String findGeneSimple(String dna) {
        // start codon is "ATG"
        // stop codon is "TAA"
        String result = "";
        int startPos = dna.indexOf("ATG");
        int endPos = dna.indexOf("TAA",startPos+3);
        boolean validStartPos = startPos != -1 && startPos < endPos;
        boolean validGene = ((endPos + 3) - startPos) % 3 == 0;
        if (validStartPos && validGene) {
            result = dna.substring(startPos,endPos+3);
        }
        return result;
    }
    public void testFindGeneSimple() {
        String dna = "AATGCGTAATATGGT";
        System.out.println("DNA strand is " + dna);
        String gene = findGeneSimple(dna);
        System.out.println("Gene is " + gene);

        dna = "AATGCTAGGGTAATATGGT";
        System.out.println("DNA strand is " + dna);
        gene = findGeneSimple(dna);
        System.out.println("Gene is " + gene);

        dna = "ATCCTATGCTTCGGCTGCTCTAATATGGT";
        System.out.println("DNA strand is " + dna);
        gene = findGeneSimple(dna);
        System.out.println("Gene is " + gene);

        dna = "TTATAA";
        System.out.println("DNA strand is " + dna);
        gene = findGeneSimple(dna);
        System.out.println("Gene is " + gene);
    }
    public static void main(String[] args) {
        FindGeneSimpleAndTest test = new FindGeneSimpleAndTest();
        test.testFindGeneSimple();
    }
}

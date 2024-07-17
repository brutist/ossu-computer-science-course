import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.TreeMap;

public class WordNet {
    private Digraph wordNet;
    private TreeMap<String, Integer> synsetWords;
    private TreeMap<String, Integer> synsets;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("cannot have null arguments in WordNet constructor");

        // instantiate the instance variables
        wordNet = new Digraph();
        synsetWords = new TreeMap<>();
        this.synsets = new TreeMap<>();

        // synsets stream
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",", 2);  // synset[0] is id, synset[1] is the synset words
            int id = Integer.parseInt(line[0]);
            String synset =  line[1];
            String[] nouns = synset.split(" ");

            // keep track of the nouns in the synset and its id
            for (String noun : nouns) {
                synsetWords.put(noun, id);
            }
            // keep track of the synsets
            this.synsets.put(synset, id);
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetWords.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("isNoun() cannot accept null args");

        return synsetWords.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("distance() cannot accept null args");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("distance() args should be WordNet nouns");

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in the shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("sap() cannot accept null args");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("sap() args should be WordNet nouns");


    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
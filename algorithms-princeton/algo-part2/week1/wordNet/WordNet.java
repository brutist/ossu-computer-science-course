import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.TreeMap;

public class WordNet {
    private final Digraph wordNet;                          // synset graph
    private final TreeMap<String, Integer> synsetWords;     // noun-id pair
    private final TreeMap<String, Integer> synsets;         // synset-id pair
    private final int V;                                    // no. of vertices

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("cannot have null arguments inSynsets WordNet constructor");

        // instantiate the instance variables
        synsetWords = new TreeMap<>();
        this.synsets = new TreeMap<>();
        int max = 0;

        // synsets stream
        In inSynsets = new In(synsets);
        while (inSynsets.hasNextLine()) {
            String[] line = inSynsets.readLine().split(",", 2);  // synset[0] is id, synset[1] is the synset words
            int id = Integer.parseInt(line[0]);
            String synset =  line[1];
            String[] nouns = synset.split(" ");

            // keep track of the nouns inSynsets the synset and its id
            for (String noun : nouns) {
                synsetWords.put(noun, id);
            }
            // keep track of the synsets
            this.synsets.put(synset, id);

            //keep track of the largest id, also the maximum vertex number
            if (!inSynsets.hasNextLine()) max = id;
        }

        // create the digraph
        this.V = max;
        wordNet = new Digraph(V);
        In inConnections = new In(hypernyms);
        while (inConnections.hasNextLine()) {
            String[] line = inConnections.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                wordNet.addEdge(v, w);
            }
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
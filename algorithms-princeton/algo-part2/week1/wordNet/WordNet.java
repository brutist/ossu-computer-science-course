import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class WordNet {
    // this assumes that no synset contains equal nouns
    private final TreeMap<String, HashSet<Integer>> nounSet;    // noun-id pair
    private final String[] synsets;                             // synset with ith index
    private final SAP sap;                                      // shortest ancestral path

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("cannot have null arguments inSynsets WordNet constructor");

        // instantiate the instance variables
        nounSet = new TreeMap<>();
        ArrayList<String> synonyms = new ArrayList<>();
        int max = 0;

        // synsets stream
        In inSynsets = new In(synsets);
        while (inSynsets.hasNextLine()) {
            // synset[0] is id, synset[1] is the synset words
            String[] line = inSynsets.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            String synset =  line[1];
            String[] nouns = synset.split(" ");

            // keep track of the nouns inSynsets the synset and its ids
            for (String noun : nouns) {
                HashSet<Integer> ids;
                if (nounSet.containsKey(noun)) {
                    ids = nounSet.get(noun);
                }
                else {
                    ids = new HashSet<>();
                }
                ids.add(id);
                nounSet.put(noun, ids);
            }
            // keep track of the synonyms
            synonyms.add(synset);

            //keep track of the largest id, also the maximum vertex number
            if (!inSynsets.hasNextLine()) max = id;
        }

        // create the synset array
        this.synsets = synonyms.toArray(new String[0]);

        // create the digraph
        Digraph wordNet = new Digraph(max + 1);
        In inConnections = new In(hypernyms);
        while (inConnections.hasNextLine()) {
            String[] line = inConnections.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                wordNet.addEdge(v, w);
            }
        }

        // check for cycles
        DirectedCycle checkCycle = new DirectedCycle(wordNet);
        if (checkCycle.hasCycle())
            throw new IllegalArgumentException("SAP cannot process cyclic graphs");
        // check if the dag is rooted
        boolean rootedDAG = false;
        for (int i = 0; i < wordNet.V(); i++) {
            if (rootedDAG && wordNet.outdegree(i) == 0)
                throw new IllegalArgumentException("SAP can only process one-root DAGs");
            if (wordNet.outdegree(i) == 0)
                rootedDAG = true;
        }
        if (!rootedDAG) throw new IllegalArgumentException("SAP can only process rooted DAGs");

        // instantiate the shortest ancestor path
        sap = new SAP(wordNet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounSet.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("isNoun() cannot accept null args");

        return nounSet.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("distance() cannot accept null args");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException
                    (String.format("distance() args '%s', '%s' should be WordNet nouns", nounA, nounB));

        HashSet<Integer> A = nounSet.get(nounA);        // A <set of synsets in which nounA appears>
        HashSet<Integer> B = nounSet.get(nounB);        // B <set of synsets in which nounB appears>
        return sap.length(A, B);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in the shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("sap() cannot accept null args");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("sap() args should be WordNet nouns");

        HashSet<Integer> A = nounSet.get(nounA);        // A <set of synsets in which nounA appears>
        HashSet<Integer> B = nounSet.get(nounB);        // B <set of synsets in which nounB appears>
        // find the synset id that is the common ancestor of the words in A and B
        int s = sap.ancestor(A, B);
        return synsets[s];
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
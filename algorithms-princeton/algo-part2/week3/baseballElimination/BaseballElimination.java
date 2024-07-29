import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class BaseballElimination {
    // 'i' represents the index of the team name
    private final HashMap<String, Integer> teams;     // name-i pair
    private final int[] w;                            // win record for every i
    private final int[] l;                            // loss record for every i
    private final int[] r;                            // remaining games for every i
    private final int[][] g;                          // games left of i with j
    private final String[] names;                     // team name of for each i

    private static class Pair {
        private final double maxCapacity;
        private final FordFulkerson ff;

        public Pair(double max, FordFulkerson ff) {
            maxCapacity = max;
            this.ff = ff;
        }
        public double max() {
            return maxCapacity;
        }
        public FordFulkerson maxFlow() {
            return ff;
        }
    }

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        teams = new HashMap<>();
        names = new String[N];
        w = new int[N];
        l = new int[N];
        r = new int[N];
        g = new int[N][N];

        int i = 0;
        in.readLine();
        while (!in.isEmpty()) {
            String line = in.readLine().trim();
            String[] attributes = line.split(" +");
            teams.put(attributes[0], i);
            names[i] = attributes[0];
            w[i]        = Integer.parseInt(attributes[1]);
            l[i]        = Integer.parseInt(attributes[2]);
            r[i]        = Integer.parseInt(attributes[3]);

            for (int k = 4; k < attributes.length; k++) {
                g[i][k - 4] = Integer.parseInt(attributes[k]);
            }
            i++;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
        int id = teams.get(team);
        return w[id];
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
        int id = teams.get(team);
        return l[id];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
        int id = teams.get(team);
        return r[id];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeams(team1, team2);
        int id1 = teams.get(team1);
        int id2 = teams.get(team2);
        return g[id1][id2];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);

        // calculate if trivially eliminated
        ArrayDeque<String> trivialCertificate = triviallyEliminatedCertificate(team);
        if (!trivialCertificate.isEmpty())  return true;

        int id = teams.get(team);
        // create a flow-network and solve max-flow using ford-fulkerson

        Pair pair = createMaxFlowPair(id);
        return pair.max() != pair.maxFlow().value();
    }

    // calculate the max flow of the team with the given id
    private Pair createMaxFlowPair(int id) {
        // there are two kinds of node in this network
        //  * game vertices - remaining divisional games (g[i][j])
        //  * team vertices - id of the teams
        /*  Approach - Connect an artificial source vertex to each game vertex and
        *               set its capacity to g[i][j]. Then, connect each game vertex
        *               with the two opposing team vertices (i and j) to ensure that
        *               one of the two teams earns a win, capacity here could be
        *               infinite. Lastly, connect team vertex to a sink.        */
        // team vertices - [0 -> n)             n = no. of teams
        // game vertices - [n -> n + k)         k = no. of game pairings
        // source vertex = n + k
        // sink vertex = n + k + 1
        // total number of vertex = n + g + 1 + 1 (last 2 are sink and source)
        int k = g.length * g[0].length;
        int n = teams.size();
        int source = n + k;
        int sink = source + 1;

        int gameVertex = n;
        FlowNetwork flowNetwork = new FlowNetwork(n + k + 2);
        double maxCapacity = 0.0;
        // represents all the i-j pairs that has been added to the flowNetwork
        HashSet<Integer> pairings = new HashSet<>();
        int idWin = w[id] + r[id];
        for (int i = 0; i < g.length; i++) {
            if (i == id) {
                gameVertex++;
                continue;
            }

            // connect team vertex to the sink except the id
            flowNetwork.addEdge(new FlowEdge(i, sink, Math.max(0.0, idWin - w[i])));

            for (int j = 0; j < g[i].length; j++) {
                if (j == id) {
                    gameVertex++;
                    continue;
                }
                // don't add the games that team id plays,
                //  because we assume that team id wins all of those games
                int cantorPair = cantorPairing(i, j);
                if (i != j && (!pairings.contains(cantorPair)))     {
                    // connect the source vertex to the game vertex
                    flowNetwork.addEdge(new FlowEdge(source, gameVertex, g[i][j]));
                    // connect the game vertex to the team vertex
                    flowNetwork.addEdge(new FlowEdge(gameVertex, i, Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(gameVertex, j, Double.POSITIVE_INFINITY));

                    // update variables
                    maxCapacity += g[i][j];
                    pairings.add(cantorPair);
                }
                gameVertex++;
            }
        }
        FordFulkerson ff = new FordFulkerson(flowNetwork, source, sink);
        return new Pair(maxCapacity, ff);
    }

    private int cantorPairing(int i, int j) {
        int k1 = Math.max(i, j);
        int k2 = Math.min(i, j);
        return (((k1 + k2) * (k1 + k2 +1) / 2) + k2);
    }

    // assumes team has been validated
    private ArrayDeque<String> triviallyEliminatedCertificate(String team) {
        int id = teams.get(team);
        // if at least one team has a larger win than this teams possible maximum win
        //  then this team is eliminated
        ArrayDeque<String> deque = new ArrayDeque<>();
        int maxPossibleWin = w[id] + r[id];
        for (int i = 0; i < w.length; i++) {
            if (i != id && maxPossibleWin < w[i]) {
                deque.addLast(names[i]);
            }
        }
        return deque;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);

        // calculate if trivially eliminated
        ArrayDeque<String> trivialCertificate = triviallyEliminatedCertificate(team);
        if (!trivialCertificate.isEmpty())  return trivialCertificate;

        int id = teams.get(team);
        Pair maxFlowPair = createMaxFlowPair(id);
        if (maxFlowPair.max() == maxFlowPair.maxFlow().value())
            return null;

        ArrayDeque<String> deque = new ArrayDeque<>();
        FordFulkerson maxFlow = maxFlowPair.maxFlow();
        for (int i = 0; i < teams.size(); i++) {
            if (i != id && maxFlow.inCut(i)) {
                deque.addLast(names[i]);
            }
        }

        return deque;
    }

    private void validateTeam(String team) {
        if (team == null) {
            throw new IllegalArgumentException("team should not be null");
        }
        if (!this.teams.containsKey(team)) {
            throw new IllegalArgumentException("invalid team name");
        }
    }

    private void validateTeams(String teamA, String teamB) {
        validateTeam(teamA);
        validateTeam(teamB);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                System.out.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    System.out.print(t + " ");
                }
                System.out.println("}");
            }
        }
    }
}


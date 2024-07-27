import java.util.HashSet;

public class BaseballElimination {
    private HashSet<String> teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

    }

    // number of teams
    public int numberOfTeams() {

    }

    // all teams
    public Iterable<String> teams() {
        return teams;
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeams(team1, team2);
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
    }

    private void validateTeam(String team) {
        if (team == null) {
            throw new IllegalArgumentException("team should not be null");
        }
        if (!this.teams.contains(team)) {
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
            else {
                System.out.println(team + " is not eliminated");
            }
        }
    }
}


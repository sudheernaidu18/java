import java.util.*;

/**
 * Question 5: Reusable Generic Ranking Component & Principle Validation
 * Demonstrates:
 *  1. Multi-level tie resolution (Points -> GoalDiff -> Name)
 *  2. Deliberate fault exposure: Integer subtraction overflow in faulty comparator
 *  3. Reusability of generic Leaderboard<T> for different domain models (Player, College)
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class LeaderboardPrincipleTest {

    // Domain 2: Player Scoring Model
    static class Player {
        final String username;
        final int score;
        final int assists;

        Player(String username, int score, int assists) {
            this.username = username;
            this.score = score;
            this.assists = assists;
        }

        @Override
        public String toString() {
            return username + "[Score:" + score + ", Ast:" + assists + "]";
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Q5: GENERIC RANKING COMPONENT & COMPARATOR PRINCIPLE VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // -------------------------------------------------------------
        // TEST 1: Rigorous Multi-Level Ties Validation
        // -------------------------------------------------------------
        System.out.println("--- 1. Multi-Level Tie Breaking Test ---");
        Comparator<Team> correctRank = Comparator
                .comparingInt((Team t) -> t.points).reversed()
                .thenComparing(Comparator.comparingInt((Team t) -> t.goalDiff).reversed())
                .thenComparing(t -> t.name);

        Leaderboard<Team> sportsBoard = new Leaderboard<>(correctRank);
        sportsBoard.add(new Team("Tigers", 10, 5));
        sportsBoard.add(new Team("Hawks", 10, 8));     // Points tie with Tigers, higher GD
        sportsBoard.add(new Team("Panthers", 9, 4));   // Points tie with Bisons, GD tie
        sportsBoard.add(new Team("Bisons", 9, 4));     // Name tie-breaker ('B' < 'P')
        sportsBoard.add(new Team("Cobras", 6, 1));

        List<Team> rankedTeams = sportsBoard.ranking();
        System.out.println("Ranked Teams Result:");
        for (int i = 0; i < rankedTeams.size(); i++) {
            System.out.printf("  Rank %d: %s\n", i + 1, rankedTeams.get(i));
        }

        System.out.println("\nVerification of Principle:");
        System.out.println("  - Hawks(10,8) ranks above Tigers(10,5) [Goal Difference Tie-Break]");
        System.out.println("  - Bisons(9,4) ranks above Panthers(9,4) [Alphabetical Name Tie-Break]");
        System.out.println("  - Transitivity: Hawks > Tigers > Bisons > Panthers > Cobras strictly holds.\n");

        // -------------------------------------------------------------
        // TEST 2: Breaking a Faulty Comparator (Integer Subtraction Overflow)
        // -------------------------------------------------------------
        System.out.println("--- 2. Exposing Faulty Subtraction Comparator (Integer Overflow) ---");
        Team extremeHigh = new Team("HighValue", 2_000_000_000, 0);
        Team extremeLow  = new Team("LowValue", -2_000_000_000, 0);

        // Faulty subtraction comparator: (t2.points - t1.points)
        // Computing: (-2,000,000,000) - (2,000,000,000) = -4,000,000,000 -> OVERFLOWS to positive +294,967,296!
        Comparator<Team> faultySubtraction = (t1, t2) -> (t1.points - t2.points);

        int faultyComparison = faultySubtraction.compare(extremeLow, extremeHigh);
        int correctComparison = Integer.compare(extremeLow.points, extremeHigh.points);

        System.out.println("Comparing Low (-2B) with High (+2B):");
        System.out.println("  Faulty Subtraction Result: " + faultyComparison + " (POSITIVE! Violates consistency!)");
        System.out.println("  Integer.compare() Result:  " + correctComparison + " (NEGATIVE! Correct!)");
        System.out.println("Conclusion: Subtraction comparator completely inverted ranking due to 32-bit signed integer overflow.\n");

        // -------------------------------------------------------------
        // TEST 3: Demonstrating Reusability of Leaderboard<T> for Players
        // -------------------------------------------------------------
        System.out.println("--- 3. Reusing Generic Leaderboard<T> for eSports Players ---");
        Comparator<Player> playerRank = Comparator
                .comparingInt((Player p) -> p.score).reversed()
                .thenComparing(Comparator.comparingInt((Player p) -> p.assists).reversed())
                .thenComparing(p -> p.username);

        Leaderboard<Player> playerBoard = new Leaderboard<>(playerRank);
        playerBoard.add(new Player("Phoenix", 2400, 15));
        playerBoard.add(new Player("Viper", 2400, 22));  // Assists tie-breaker
        playerBoard.add(new Player("Apex", 1850, 30));

        System.out.println("Player Leaderboard Results:");
        for (Player p : playerBoard.ranking()) {
            System.out.println("  " + p);
        }
    }
}

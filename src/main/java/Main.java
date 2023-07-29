import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONObject;

public class Main {

    /*
    * Comment / Uncomment a section to use and test it
    * A Riot API Key is required, base API Keys can be acquired for free at https://developer.riotgames.com/
    * These basic keys only have a 24h lifetime
    * Enter the API Key in the Player class
    * */
    public static void main(String[] args) throws Exception {

        // Create a Player using their ign + lane (top/jg/mid/bot/sup)
        // Player p = new Player("enter_ign", "mid");
        // Display the player's pUUID and SummonerID
        // System.out.println("PUUID: " + p.puuid);
        // System.out.println("SummonerID: " + p.summonerID);


        // Retrieve the player most played champions
//        List<Map.Entry<String, Integer>> mp =  p.mostPlayedChampions();
//        Champion c1 = new Champion(mp.get(0).getKey());
//        Champion c2 = new Champion(mp.get(1).getKey());

        // Display all champions from the player, in descending order of Mastery Points
        // System.out.println(mp);

        // Or just the first 2 champs
        // System.out.println(c1.name + " " + c2.name);

        // Check out the WR, KDA, PickRate of champ1 against champ2, or with champ2 if true
        // System.out.println(c1.get1v1Stats(c2, false));
        // Same thing, but for the said champion against/with all the others
        // System.out.println(c1.getAllStats(c1, false));


        // Create two teams of 5 players
        Player p1 = new Player("KaIoow", "sup");
        Player p2 = new Player("HxPulse", "bot");
        Player p3 = new Player("RBK5howdown", "top");
        Player p4 = new Player("BabyTimmy", "jg");
        Player p5 = new Player("Xodogaron", "mid");

        Player p6 = new Player("KaIoow", "sup");
        Player p7 = new Player("HxPulse", "bot");
        Player p8 = new Player("RBK5howdown", "top");
        Player p9 = new Player("BabyTimmy", "jg");
        Player p10 = new Player("Xodogaron", "mid");

        ArrayList<Player> team1 = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5));
        ArrayList<Player> team2 = new ArrayList<>(Arrays.asList(p6, p7, p8, p9, p10));
        // Create the list of banned / unavailable / rerolled champions
        ArrayList<String> unavailable = new ArrayList<>();
        // unavailable.add("Yuumi");

        // Create the list of champions already picked by your team
        ArrayList<String> teamPicks = new ArrayList<>();

        // Create the list of champions already picked by the enemy team
        ArrayList<String> enemyPicks = new ArrayList<>();

        // Get the recommended Pick for a given player, considering both teams players and the banned champions
        System.out.println(p1.recommendedPick(unavailable, teamPicks, enemyPicks).get(0));

        // Get the recommended Ban for a given player, considering the champions already banned
        System.out.println(p1.recommendedBan(unavailable).get(0));

        // Simulate a draft phase between the 2 teams previously created and using the banned champions
        Draft.draftPhase(team1, team2, unavailable);

        // For each player of a team, returns the 3 best recommended bans
        // Draft.teamBans(team1);

        // Compare the gameplay of 2 players using their challenges data, and a coefficient representing the distance between their stats
        // ArrayList<ArrayList<String>> res = Draft.compareThroughChallenges(p2, p1, 1.5);

        System.out.println("I'm done ---------------------------");
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws Exception {

//        List<Map.Entry<String, Integer>> mp =  p.mostPlayedChampions(30);
//        Champion c1 = new Champion(mp.get(0).getKey());
//        Champion c2 = new Champion(mp.get(1).getKey());
//
//        System.out.println("PUUID: " + p.puuid);
//        System.out.println("SummonerID: " + p.summonerID);
//        System.out.println(mp);
//        System.out.println(c1.get1v1Stats(c2, false));
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
        Draft.draftPhase(team1, team2);

        System.out.println("I'm done ---------------------------");
    }
}
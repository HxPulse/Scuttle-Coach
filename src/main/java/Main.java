import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws Exception {
        Player p = new Player("hxpulse");
//        List<Map.Entry<String, Integer>> mp =  p.mostPlayedChampions(30);
//        Champion c1 = new Champion(mp.get(0).getKey());
//        Champion c2 = new Champion(mp.get(1).getKey());
//
//        System.out.println("PUUID: " + p.puuid);
//        System.out.println("SummonerID: " + p.summonerID);
//        System.out.println(mp);
//        System.out.println(c1.get1v1Stats(c2, false));

        ArrayList<String> unavailable = new ArrayList<>();
        ArrayList<String> teammates = new ArrayList<>();
        ArrayList<String> enemies = new ArrayList<>();
        String lane = "top"; // Lane of the player : top jg mid bot or sup

        unavailable.add("Poppy"); // banned champions, if reroll it'll add the champ to this list
        // unavailable.add(Insert Unavailable champ 2)
        // etc..

        teammates.add("Xayah"); // example
        // teammates.add(Insert Teammate 2)
        // teammates.add(Insert Teammate 3)
        // teammates.add(Insert Teammate 4)

        enemies.add("Illaoi"); // example
        // enemies.add(Insert Enemy 2);
        // enemies.add(Insert Enemy 3);
        // enemies.add(Insert Enemy 4);
        // enemies.add(Insert Enemy 5);
        List<Map.Entry<String, Double>> res = p.recommendedPick(lane, unavailable, teammates, enemies);

        System.out.println("We recommend you to pick : " + res.get(0).getKey() + ", it receives a score of : " + res.get(0).getValue().toString());
        System.out.println(res);
        System.out.println("I'm done ---------------------------");
    }
}
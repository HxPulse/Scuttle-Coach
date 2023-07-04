import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;



public class Main {

    public static void main(String[] args) {
        Player p = new Player("hxpulse");
        List<Map.Entry<String, Integer>> mp =  p.mostPlayedChampions(30);
        Champion c1 = new Champion(mp.get(0).getKey());
        Champion c2 = new Champion(mp.get(1).getKey());

        System.out.println("PUUID: " + p.puuid);
        System.out.println("SummonerID: " + p.summonerID);
        System.out.println(mp);
        System.out.println(c1.get1v1Stats(c2, false));


        System.out.println("I'm done");
    }
}
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Player {

    String apiKey = "RGAPI-99a5ce79-b02c-4df4-ade7-f280a81495db";
    String name;
    String puuid;
    String summonerID;
    int rank;

    public Player(String name) {
        this(name, null, null);
    }

    public Player(String name, String division, String tier) {
        this.name = name;
        this.rank = convertRankIntoInt(division, tier);
        this.puuid = getID("puuid");
        this.summonerID = getID("id");
    }

    public List<Map.Entry<String, Integer>> mostPlayedChampions(int amount) {
        HashMap <String, Integer> champList = new HashMap<>();
        JSONArray res = apiCallArray("https://euw1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + this.summonerID + "?api_key=" + this.apiKey);

        for (int i = 0; i < amount; i++) {
            JSONObject champion = res.getJSONObject(i);
            String c = Champion.championFromID((int) champion.get("championId"));
            int points = (int) champion.get("championPoints");
            champList.put(c, points);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(champList.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }

    public int convertRankIntoInt(String division, String tier) {
        // Transforme une division + tier en int pour faciliter les calculs
        // -1 = UNRANKED, 0 = IRON IV, +1 par division, jusqu'à 30 = CHALLENGER
        ArrayList<String> lowDivisions = new ArrayList<>(Arrays.asList("IRON", "BRONZE", "SILVER", "GOLD", "PLATINUM", "EMERALD", "DIAMOND"));
        ArrayList<String> highDivisions = new ArrayList<>(Arrays.asList("MASTER", "GRANDMASTER", "CHALLENGER"));

        int convertedRank = 0;

        if (lowDivisions.contains(division)) {
            convertedRank = lowDivisions.indexOf(division) * 4;
        } else if (highDivisions.contains(division)) {
            convertedRank = highDivisions.indexOf(division) + 28;
        } else {
            return -1;
        }
        if (convertedRank >= 28 || tier.equals("IV")) {
            return convertedRank;
        }
        convertedRank += 4 - tier.length();
        return convertedRank;
    }

    public String getID(String saidID) {
        JSONObject response = apiCall("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + this.name + "?api_key=" + this.apiKey);
        return (String) response.get(saidID);
    }

    public JSONArray apiCallArray(String urlGiven) {
        StringBuilder res = new StringBuilder();
        try {
            URL url = new URL(urlGiven);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray(res.toString());
    }

    public JSONObject apiCall(String urlGiven) {
        StringBuilder res = new StringBuilder();
        try {
            URL url = new URL(urlGiven);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject(res.toString());
    }

    public static List<Map.Entry<String, Integer>> mostPlayedChamps(HashMap<String, Integer> hashMap) {

        List<Map.Entry<String, Integer>> list = new ArrayList<>(hashMap.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        return list;
    }
}

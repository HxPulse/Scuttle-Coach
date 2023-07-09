import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Player {

    String apiKey = "RGAPI-41a1ec91-1bba-4f73-9608-f5035cf5cd8b";
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

    public List<Map.Entry<String, Integer>> mostPlayedChampions() {
        // Récupère la liste dans l'ordre décroissant des Mastery Points des champions du joueur
        HashMap<String, Integer> champList = new HashMap<>();
        JSONArray res = apiCallArray("https://euw1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + this.summonerID + "?api_key=" + this.apiKey);

        int i = 0;
        while (res.length() > i && res.getJSONObject(i) != null) {
            JSONObject champion = res.getJSONObject(i);
            String c = Champion.championFromID((int) champion.get("championId"));
            int points = (int) champion.get("championPoints");
            champList.put(c, points);
            i++;
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

    public Integer getIndexOf(List<Map.Entry<String, Integer>> list, String s) {
        // Récup l'index de s dans list
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(s)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public List<Map.Entry<String, Double>> recommendedPick(String lane, ArrayList<String> unavailable, ArrayList<String> teammates, ArrayList<String> enemies) throws Exception {
        // Fonction principale pour trouver le pick le plus adapté
        Map<String, Double> results = new HashMap<>();
        ListOfChampions l = new ListOfChampions();
        ArrayList<String> possiblePicks = (ArrayList<String>) ListOfChampions.class.getField(lane + "Champions").get(l);
        possiblePicks.removeAll(teammates);
        possiblePicks.removeAll(enemies);
        possiblePicks.removeAll(unavailable);
        if (possiblePicks.isEmpty()) {
            results.put("No champion left !", 0.0);
            List<Map.Entry<String, Double>> sortedList = new ArrayList<>(results.entrySet());
            sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            return sortedList;
        }
        List<Map.Entry<String, Integer>> mostPlayedChamps = this.mostPlayedChampions();
        ArrayList<ArrayList<String>> matchups = l.txtToArray("src/main/java/lists/" + lane + "Matchups.txt");
        ArrayList<ArrayList<String>> synergies = l.txtToArray("src/main/java/lists/" + lane + "Synergies.txt");
        for (String c : possiblePicks) {
            int index = getIndexOf(mostPlayedChamps, c);
            results.put(c, mostPlayedChamps.size() - index + 0.0);
        }
        results = getScores(lane, results, enemies, matchups, possiblePicks);
        results = getScores(lane, results, teammates, synergies, possiblePicks);

        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(results.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sortedList;
    }

    public Map<String, Double> getScores(String lane, Map<String, Double> res, ArrayList<String> champPool, ArrayList<ArrayList<String>> stats, ArrayList<String> possiblePicks) throws Exception {
        // Fonction de calcul des rangs des champions, utilisée dans la fonction recommendedPick
        ListOfChampions l = new ListOfChampions();
        Double coefficient = 1.0;
        ArrayList<String> sameLane = (ArrayList<String>) ListOfChampions.class.getField(lane + "Champions").get(l);
        for (String str : champPool) {
            if (sameLane.contains(str)) {
                coefficient = 1.2;
            }
            ArrayList<ArrayList<String>> whatWeCareAbout = new ArrayList<>();
            for (ArrayList<String> s : stats) {
                if(possiblePicks.contains(s.get(0)) && s.get(1).equals(str)){
                    whatWeCareAbout.add(s);
                }
            }
            ArrayList<String> wr = sortByWR(whatWeCareAbout);
            ArrayList<String> kda = sortByKDA(whatWeCareAbout);
            for (String s : wr) {
                res.put(s, res.get(s) + (wr.size() - wr.indexOf(s)) * coefficient);
            }
            for (String s1 : kda) {
                res.put(s1, res.get(s1) + (kda.size() - kda.indexOf(s1)) * coefficient);
            }
        }
        return res;
    }

    public static ArrayList<String> sortByWR(ArrayList<ArrayList<String>> arrayL) {
        // Classe les champions par WR pour un matchup/synergie
        ArrayList<ArrayList<String>> sortedList = new ArrayList<>(arrayL);
        Collections.sort(sortedList, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> list1, ArrayList<String> list2) {
                double thirdElement1 = Double.parseDouble(list1.get(2));
                double thirdElement2 = Double.parseDouble(list2.get(2));
                return Double.compare(thirdElement2, thirdElement1);
            }
        });

        ArrayList<String> return1 = new ArrayList<>();
        for (ArrayList<String> list : sortedList) {
            return1.add(list.get(0));
        }

        return return1;
    }

    public static ArrayList<String> sortByKDA(ArrayList<ArrayList<String>> arrayL) {
        // Classe les champions par KDA pour un matchup/synergie
        ArrayList<ArrayList<String>> sortedList = new ArrayList<>(arrayL);
        Collections.sort(sortedList, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> list1, ArrayList<String> list2) {
                double fourthElement1 = Double.parseDouble(list1.get(3));
                double fourthElement2 = Double.parseDouble(list2.get(3));
                return Double.compare(fourthElement2, fourthElement1);
            }
        });

        ArrayList<String> return2 = new ArrayList<>();
        for (ArrayList<String> list : sortedList) {
            return2.add(list.get(0));
        }

        return return2;
    }

    public String getID(String saidID) {
        // Récupérer summonerID ou puuid à partir de l'ign
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

}

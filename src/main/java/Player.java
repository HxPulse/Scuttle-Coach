import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Player {

//    String apiKey = "RGAPI-a21b3483-072d-441c-a0af-5adc0b0faef3";
    String apiKey = "RGAPI-ee067c8d-9bc6-4d93-b5b4-dc8686960648";
    String name;
    String puuid;
    String summonerID;
    String lane;
    int rank;

    public Player(String name) {
        this(name, null, null, null);
    }

    public Player(String name, String lane) {
        this(name, lane, null, null);
    }

    public Player(String name, String lane, String division, String tier) {
        this.name = name;
        this.lane = lane;
        this.rank = convertRankIntoInt(division, tier);
        this.puuid = getID("puuid");
        this.summonerID = getID("id");
    }

    public List<Map.Entry<String, Integer>> mostPlayedChampions() {
        // Returns in descending order the Mastery Points for each champion played by the player
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
        // Transforms a division + tier into an int to make computing easier
        // -1 = UNRANKED, 0 = IRON IV, +1 per division, max is 30 = CHALLENGER
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
        // Retrieves index of s in list
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(s)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public List<Map.Entry<String, Double>> recommendedPick(ArrayList<String> unavailable, ArrayList<String> teammates, ArrayList<String> enemies) throws Exception {
        // Main function to find the most optimized pick
        Map<String, Double> results = new HashMap<>();
        ListOfChampions l = new ListOfChampions();
        ArrayList<String> possiblePicks = (ArrayList<String>) ListOfChampions.class.getField(this.lane + "Champions").get(l);
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
        results = getScores(results, enemies, matchups, possiblePicks);
        results = getScores(results, teammates, synergies, possiblePicks);

        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(results.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sortedList;
    }

    public Map<String, Double> getScores(Map<String, Double> res, ArrayList<String> champPool, ArrayList<ArrayList<String>> stats, ArrayList<String> possiblePicks) throws Exception {
        // Rank computing function of champions, used in recommendedPick()
        ListOfChampions l = new ListOfChampions();
        Double coefficient = 0.8;
        ArrayList<String> sameLane = (ArrayList<String>) ListOfChampions.class.getField(this.lane + "Champions").get(l);
        for (String str : champPool) {
            if (sameLane.contains(str)) {
                coefficient = 1.0;
            }
            ArrayList<ArrayList<String>> whatWeCareAbout = new ArrayList<>();
            for (ArrayList<String> s : stats) {
                if (possiblePicks.contains(s.get(0)) && s.get(1).equals(str)){
                    whatWeCareAbout.add(s);
                }
            }
            ArrayList<String> wr = sortByWR(whatWeCareAbout);
            ArrayList<String> kda = sortByKDA(whatWeCareAbout);
            for (String s : wr) {
                res.put(s, res.get(s) + (l.allChampions.size() - wr.indexOf(s)) * coefficient);
            }
            for (String s1 : kda) {
                res.put(s1, res.get(s1) + (l.allChampions.size() - kda.indexOf(s1)) * coefficient);
            }
        }
        return res;
    }

    public List<Map.Entry<String, Double>> recommendedBans(ArrayList<String> unavailable) throws Exception {
        // Main function to find the most optimized pick
        Map<String, Double> results = new HashMap<>();
        ListOfChampions l = new ListOfChampions();
        ArrayList<String> allChamps = l.allChampions;
        ArrayList<String> possiblePicks = (ArrayList<String>) ListOfChampions.class.getField(this.lane + "Champions").get(l);

        allChamps.removeAll(unavailable);
        possiblePicks.removeAll(unavailable);

        ArrayList<ArrayList<String>> matchups = l.txtToArray("src/main/java/lists/" + lane + "Matchups.txt");
        ArrayList<ArrayList<String>> synergies = l.txtToArray("src/main/java/lists/" + lane + "Synergies.txt");

        List<Map.Entry<String, Integer>> mostPlayedChamps = this.mostPlayedChampions();
        for (String c : possiblePicks) {
            int index = getIndexOf(mostPlayedChamps, c);
            results.put(c, mostPlayedChamps.size() - index + 0.0);
        }
        results = computeBans(results, matchups, possiblePicks, allChamps);
        results = computeBans(results, synergies, possiblePicks, allChamps);
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(results.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return sortedList;
    }

    public Map<String, Double> computeBans(Map<String, Double> res, ArrayList<ArrayList<String>> matchups, ArrayList<String> possiblePicks, ArrayList<String> allChampions) {
        Double coefficient = 0.8;
        ListOfChampions l = new ListOfChampions();
        ArrayList<ArrayList<String>> db = new ArrayList<>();
        for (String s : possiblePicks) {
            Double avgWR = 0.0;
            Double avgKDA = 0.0;
            int count = 0;

            for (ArrayList<String> matchUp : matchups) {
                if (matchUp.get(0).equals(s) && allChampions.contains(matchUp.get(1))) {
                    avgWR += Double.parseDouble(matchUp.get(2));
                    avgKDA += Double.parseDouble(matchUp.get(3));
                    count++;
                }
            }
            if (count == 0) {
                count++;
            }

            ArrayList<String> champStats = new ArrayList<>(Arrays.asList(s, s, Double.toString(avgWR/count), Double.toString(avgKDA/count)));
            db.add(champStats);
        }
        ArrayList<String> wr = sortByWR(db);
        ArrayList<String> kda = sortByKDA(db);
        for (String s : wr) {
            res.put(s, res.get(s) + (l.allChampions.size() - wr.indexOf(s)) * coefficient);
        }
        for (String s1 : kda) {
            res.put(s1, res.get(s1) + (l.allChampions.size() - kda.indexOf(s1)) * coefficient);
        }
        return res;
    }

    public static ArrayList<String> sortByWR(ArrayList<ArrayList<String>> arrayL) {
        // Ranks champions by WR for a given matchup or synergy
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
        // // Ranks champions by KDA for a given matchup or synergy
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
        // Retrieves summonerID or puuid using ign
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

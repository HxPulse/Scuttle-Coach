import java.lang.reflect.Array;
import java.util.*;
import java.text.DecimalFormat;
public class Draft {

    public static void draftPhase(ArrayList<Player> team1, ArrayList<Player> team2, ArrayList<String> unavailable) throws Exception {

        ArrayList<String> teammates = new ArrayList<>();
        ArrayList<String> enemies = new ArrayList<>();

        Player blue1 = team1.get(0);
        Player blue2 = team1.get(3);
        Player blue3 = team1.get(2);
        Player blue4 = team1.get(1);
        Player blue5 = team1.get(4);

        Player red1 = team2.get(0);
        Player red2 = team2.get(3);
        Player red3 = team2.get(2);
        Player red4 = team2.get(1);
        Player red5 = team2.get(4);

        unavailable = playerBan(red1, unavailable, 1);
        unavailable = playerBan(blue1, unavailable, 2);
        unavailable = playerBan(red2, unavailable, 1);
        unavailable = playerBan(blue2, unavailable, 2);
        unavailable = playerBan(red3, unavailable, 1);
        unavailable = playerBan(blue3, unavailable, 2);

        System.out.println("");
        teammates = playerPick(blue1, unavailable, teammates, enemies, 1);

        System.out.println("");
        enemies = playerPick(red1, unavailable, enemies, teammates, 2);
        enemies = playerPick(red2, unavailable, enemies, teammates, 2);

        System.out.println("");
        teammates = playerPick(blue2, unavailable, teammates, enemies, 1);
        teammates = playerPick(blue3, unavailable, teammates, enemies, 1);

        System.out.println("");
        enemies = playerPick(red3, unavailable, enemies, teammates,2);

        System.out.println("");

        unavailable.addAll(teammates);
        unavailable.addAll(enemies);
        unavailable = playerBan(red4, unavailable, 1);
        unavailable = playerBan(blue4, unavailable, 2);
        unavailable = playerBan(red5, unavailable, 1);
        unavailable = playerBan(blue5, unavailable, 2);

        System.out.println("");
        enemies = playerPick(red4, unavailable, enemies, teammates,2);

        System.out.println("");
        teammates = playerPick(blue4, unavailable, teammates, enemies,1);
        teammates = playerPick(blue5, unavailable, teammates, enemies,1);

        System.out.println("");
        enemies = playerPick(red5, unavailable, enemies, teammates,2);
    }


    public static ArrayList<String> playerPick(Player p, ArrayList<String> unavailable, ArrayList<String> teammates, ArrayList<String> enemies, int number) throws Exception {
        Map.Entry<String, Double> pick = p.recommendedPick(unavailable, teammates, enemies).get(0);     // Récup élt #1 dans la liste des picks conseillés
        Double bestScorePossible = computeBestScore(p, enemies, teammates);                             // Calcul du meilleur score théorique pour avoir le ratio de précision
        System.out.println("Team " + number + " " + p.name + " picks " + pick.getKey() + " with an accuracy of " + pick.getValue()/bestScorePossible*100 + "%");
        teammates.add(pick.getKey());
        return teammates;
    }

    public static Double computeBestScore(Player p, ArrayList<String> enemies, ArrayList<String> teammates) throws Exception {
        ListOfChampions l = new ListOfChampions();
        Double theoricBestScore = l.allChampions.size() * p.otpCoefficient;
        ArrayList<String> sameLane = (ArrayList<String>) ListOfChampions.class.getField(p.lane + "Champions").get(l);
        for (String ally : teammates) {
            if (sameLane.contains(ally)) {
                theoricBestScore += 2 * l.allChampions.size() * p.laneCoefficient;
            } else {
                theoricBestScore += 2 * l.allChampions.size() * p.otherLanesCoefficient;
            }
        }
        for (String enemy : enemies) {
            if (sameLane.contains(enemy)) {
                theoricBestScore += 2 * l.allChampions.size() * p.laneCoefficient;
            } else {
                theoricBestScore += 2 * l.allChampions.size() * p.otherLanesCoefficient;
            }
        }
        return theoricBestScore;
    }
    public static ArrayList<String> playerBan(Player p, ArrayList<String> unavailable, int number) throws Exception {
        Map.Entry<String, Double> pick = p.recommendedBan(unavailable).get(0);
        ListOfChampions l = new ListOfChampions();
        int bestScorePossible = l.allChampions.size() * 5;
        Double rating = pick.getValue() / bestScorePossible;
        System.out.println("Team " + number + " bans " + pick.getKey() + " with an accuracy of " + rating*100 + "%");
        unavailable.add(pick.getKey());
        return unavailable;
    }

    public static void teamBans(ArrayList<Player> team) throws Exception {
        for (Player p : team) {
            List<Map.Entry<String, Double>> pick = p.recommendedBan(new ArrayList<>());
            System.out.println("Recommended bans for " + p.name + " : " + pick.get(0).getKey() + " "
                    + pick.get(1).getKey() + " " + pick.get(2).getKey());
        }
    }

    public static ArrayList<ArrayList<String>> compareThroughChallenges(Player p1, Player p2, double statsDiffCoef) {
        HashMap<Integer, Double> c1 = p1.getChallenges();   // Récup les challenges
        HashMap<Integer, Double> c2 = p2.getChallenges();
        double nbGames1 = c1.get(402102);                   // Récup nbr de games
        double nbGames2 = c2.get(402102);
        HashMap<Integer, Map.Entry<Player, Double>> map = new HashMap<>();

        for (int i : c1.keySet()) {
            if (c2.keySet().contains(i)) {
                ArrayList<Double> statsDiff = getStatsDiff(nbGames1, nbGames2, c1.get(i), c2.get(i));   // Calcul stats diff
                if (statsDiff.get(1) >= statsDiffCoef) {        // Si on considère la diff importante
                    Double diff = statsDiff.get(1) * 100;
                    if (statsDiff.get(0).equals(c1.get(i))) {   // On regarde qui diff l'autre
                        map.put(i, new AbstractMap.SimpleEntry<>(p1, diff));
                    } else {
                        map.put(i, new AbstractMap.SimpleEntry<>(p2, diff));
                    }
                }
            }
        }
        ArrayList<ArrayList<String>> allDiffs = new ArrayList<>();
        for (int i : map.keySet()) {
            ArrayList<String> thisDiff = new ArrayList<>();
            thisDiff.add(map.get(i).getValue().toString());
            thisDiff.add(displayChallengeDiff(i, map.get(i)));
            allDiffs.add(thisDiff);
        }
        sortChallengeList(allDiffs);    // Classement
        return allDiffs;
    }

    public static void sortChallengeList(ArrayList<ArrayList<String>> example) {
        Collections.sort(example, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> list1, ArrayList<String> list2) {
                double value1 = Double.parseDouble(list1.get(0));
                double value2 = Double.parseDouble(list2.get(0));
                return Double.compare(value2, value1);
            }
        });
    }

    public static ArrayList<Double> getStatsDiff(double nbGames1, double nbGames2, double stats1, double stats2) {
        double avg1 = stats1 / nbGames1;
        double avg2 = stats2 / nbGames2;
        ArrayList<Double> res = new ArrayList<>();

        if (avg1 >= avg2) {
            res.add(stats1);
            res.add(avg1/avg2 - 1);
        } else {
            res.add(stats2);
            res.add(avg2/avg1 - 1);
        }
        return res;
    }

    public static String displayChallengeDiff(int challengeID, Map.Entry<Player, Double> info) {
        HashMap<Integer, String> messages = new HashMap<>();
        String ign = info.getKey().name;
        int data = (int) Math.round(info.getValue());
        String diff = data + "%";
        messages.put(302101, ign + " has +" + diff + " takedowns when invading");
        messages.put(302102, ign + " has +" + diff + " takedowns when ganking early");
        messages.put(203401, ign + " has +" + diff + " early first drag kills");
        messages.put(203402, ign + " counters jungle buffs " + diff + " more");
        messages.put(203403, ign + " afk farms early " + diff + " more");
        messages.put(203404, ign + " puts +" + diff + " priority on the first buff camps");
        messages.put(203405, ign + " puts +" + diff + " priority on the first scuttles");
        messages.put(203407, "When winning, " + ign + " counters jungle " + diff + " more");
        messages.put(203408, ign + " seeks " + diff + " more for early jg 1v1");
        messages.put(203409, ign + " can be consistent at smite stealing, they have +" + diff + " efficiency");
        messages.put(402201, ign + " smite steals " + diff + " more");
        messages.put(402203, ign + " puts +" + diff + " priority on scuttle crabs");
        messages.put(402204, ign + " afk farms " + diff + " more");
        messages.put(402205, ign + " counters jungle " + diff + " more");
        messages.put(402208, ign + " puts +" + diff + " priority on Rift Heralds");
        messages.put(401301, ign + " wins " + diff + " more as Jungle");
        messages.put(302103, ign + " has +" + diff + " takedowns when rotating early");
        messages.put(302104, ign + " rotates through all lanes " + diff + " more");
        messages.put(401304, ign + " wins " + diff + " more as Mid");
        messages.put(401305, ign + " wins " + diff + " more as Top");
        messages.put(402404, ign + " survives " + diff + " more when getting tower dived");
        messages.put(302402, ign + " gets +" + diff + " takedowns when regrouping with their team");
        messages.put(402108, ign + " has +" + diff + " average assists");
        messages.put(302303, ign + " is " + diff + " more clutch when playing enchanters");
        messages.put(301302, ign + " has +" + diff + " efficency when snowballing as support");
        messages.put(204103, ign + " completes their support quest early " + diff + " more");
        messages.put(401302, ign + " wins " + diff + " more as Support");
        messages.put(204202, ign + " protects their wards with +" + diff + " efficiency");
        messages.put(202104, ign + " has a much higher vision score " + diff + " times more");
        messages.put(202202, ign + " hunts the most wards " + diff + " more");
        messages.put(204203, ign + " remember ward spots with +" + diff + " efficiency");
        messages.put(204201, ign + " hunts early wards " + diff + " more");
        messages.put(402402, ign + " places +" + diff + " wards");
        messages.put(402403, ign + " places +" + diff + " Control wards");
        messages.put(402401, ign + " takes downs +" + diff + " wards");
        messages.put(204102, ign + " has a high vision score " + diff + " more often");
        messages.put(402501, ign + " is +" + diff + " at ease with enchanters");
        messages.put(204101, ign + " finish their support quest before their opponent " + diff + " more");
        messages.put(402502, ign + " CC enemies " + diff + " more");
        messages.put(302304, ign + " has +" + diff + " efficiency when CC setting up a kill");
        messages.put(302305, ign + " has +" + diff + " efficiency when Insec setting up a kill");
        messages.put(203203, ign + " prioritizes tanking CC " + diff + " more");
        messages.put(203202, ign + " has +" + diff + " effiency as a tank during teamfights");
        messages.put(202205, ign + " puts +" + diff + " priority on taking damage");
        messages.put(202201, ign + " has highest CC " + diff + " more");
        messages.put(202302, "When hard snowballing early, " + ign + " has +" + diff + " efficiency");
        messages.put(202301, "When hard stomping early, " + ign + " has +" + diff + " efficency");
        messages.put(402106, ign + " has " + diff + " more pentakills");
        messages.put(402104, ign + " has +" + diff + " efficiency when having small snowballs");
        messages.put(201004, ign + " is " + diff + " more bloodthirsty when snowballing early");
        messages.put(210005, ign + " plays for KDA " + diff + " more in Solo Queue");
        messages.put(202305, ign + " has high gold per minute " + diff + " more often");
        messages.put(402107, ign + " has +" + diff + "kills");
        messages.put(202101, ign + " stomp their lane CS-wise with +" + diff + " efficiency");
        messages.put(202203, ign + " carries games " + diff + " more often");
        messages.put(202204, ign + " is a DPS threat with +" + diff + " efficiency");
        messages.put(402408, ign + " has +" + diff + " bounty gold");
        messages.put(402409, ign + " has +" + diff + " gold earned");
        messages.put(402407, ign + " has +" + diff + " minions kills");
        messages.put(103204, ign + " has +" + diff + " wave clear efficiency");
        messages.put(401303, ign + " wins " + diff + " more as Bot");
        messages.put(201002, ign + " is " + diff + " more efficient at early CSing");
        messages.put(402105, ign + " gets +" + diff + "multikills");
        messages.put(201003, ign + " plays for KDA " + diff + " more");
        messages.put(202103, ign + " takes very early leads " + diff + " more often");
        messages.put(202102, ign + " takes early leads " + diff + " more often");
        messages.put(202105, ign + " stomp their lane with +" + diff + " efficiency XP-wise");
        messages.put(203303, ign + " has +" + diff + " more takedowns when tower diving");
        messages.put(302202, ign + " has +" + diff + " early tower takedowns");
        messages.put(302203, ign + " has +" + diff + " early tower first blood");
        messages.put(402209, ign + " has +" + diff + " tower plates");
        messages.put(201001, ign + " has +" + diff + " tower first blood");
        messages.put(402109, ign + " has +" + diff + " first bloods");
        messages.put(103102, ign + " has +" + diff + " early snowball efficiency");
        messages.put(203302, ign + " has +" + diff + " clutch efficiency in lost teamfights");
        messages.put(301205, ign + " has +" + diff + " comeback efficiency through Elder Dragons");
        messages.put(301202, ign + " comebacks " + diff + " more after losing an inhib");
        messages.put(301201, ign + " comebacks " + diff + " more with an open nexus");
        messages.put(301203, ign + " comebacks " + diff + " more when hard losing");
        messages.put(303201, ign + " has +" + diff + " Clash games experience");
        messages.put(303202, ign + " has +" + diff + " Clash tournaments experience");
        messages.put(303204, ign + " plays " + diff + " more with the same Clash team");
        messages.put(303302, ign + " plays " + diff + " more as a 5-premade");
        messages.put(303303, ign + " plays " + diff + " more as premade");
        messages.put(203301, ign + " has +" + diff + " solo kills");
        messages.put(103202, ign + " has +" + diff + " blast cone uses");
        messages.put(203102, ign + " has +" + diff + " dodge efficiency");
        messages.put(203103, ign + " has +" + diff + " cleanse efficiency");
        messages.put(203101, ign + " has +" + diff + " early skillshot aim");
        messages.put(203305, ign + " has +" + diff + " assassin precision");
        messages.put(302105, ign + " puts +" + diff + " deep efficient control wards");
        messages.put(302106, ign + " has +" + diff + " nashor snowball efficiency");
        messages.put(301103, ign + " has +" + diff + " dragon & elder priority");
        messages.put(402210, ign + " has +" + diff + " towers destructed");
        messages.put(402206, ign + " has +" + diff + " dragon takedowns");
        messages.put(402207, ign + " has +" + diff + " baron takedowns");
        messages.put(301104, ign + " takes both Heralds " + diff + " more often");
        messages.put(301105, ign + " hard snowballs " + diff + " more through dragons");
        messages.put(302201, ign + " finds great Heralds opportunities " + diff + " more often");
        messages.put(301303, ign + " goes for early 3-inhib sieges " + diff + " more often");
        messages.put(301306, ign + " has +" + diff + " omega hard stomps");
        messages.put(302404, ign + " teleports into teamfight " + diff + " more often");
        messages.put(301101, ign + " spawn kills Epic Monsters " + diff + " more often");
        messages.put(402202, ign + " takes +" + diff + " towers with Heralds");
        messages.put(303205, ign + " has " + diff + " more wins than loses in Clash");
        messages.put(302301, ign + " likes to camp in brushes " + diff + " more");
        messages.put(210002, ign + " has a Pentakill with " + diff + " more champions");
        messages.put(302302, ign + " has +" + diff + " picks with allies");
        messages.put(103203, ign + " flashes in with " + diff + " more aggressivity");
        messages.put(103301, ign + " is " + diff + " more aggressive when having Elder buff");
        messages.put(103303, ign + " targets junglers with +" + diff + " efficiency when fighting near an Epic Monster");
        messages.put(103201, ign + " takes " + diff + " more fights near Epic Monsters");
        messages.put(301102, ign + " has +" + diff + " early nashors");
        messages.put(203304, ign + " splitpushes with +" + diff + " efficiency");
        messages.put(401106, ign + " wins with " + diff + " more champions");
        messages.put(401306, ign + " wins " + diff + " more when filled");
        messages.put(402406, ign + " wins with " + diff + " more mythic items");
        messages.put(301301, ign + " has +" + diff + " perfect wins");
        messages.put(301107, ign + " struggles to end the game " + diff + " more after first Elder");
        messages.put(301106, ign + " struggles to end the game " + diff + " more after killing Nashor");
        messages.put(103206, ign + " has +" + diff + " assists from Epic Monsters");
        messages.put(210001, ign + " earned S+ with " + diff + " more champions");
        messages.put(210003, ign + " earns S- " + diff + " more often");
        messages.put(103101, ign + " takes recall " + diff + " more risky");
        messages.put(203406, ign + " secures Epic Monsters with +" + diff + " efficiency");
        messages.put(103205, ign + " takes very close fights " + diff + " more often");
        messages.put(301304, ign + " has +" + diff + " early aces");
        messages.put(203104, ign + " smite steals " + diff + " more often when not having Smite");
        messages.put(302401, ign + " has +" + diff + " 5 for 0 fights");
        messages.put(202303, ign + " won with " + diff + " more champions");
        messages.put(103304, ign + " puts +" + diff + " priority on Nexus during base fights");
        messages.put(301305, ign + " breakdances with Herald " + diff + " more often in enemy base");
        messages.put(301204, ign + " wins " + diff + " more often with an AFK teammate");
        messages.put(203201, ign + " handles huge tank fights with +" + diff + " efficiency");
        messages.put(402103, ign + " goes on high killing streaks with +" + diff + " efficiency");
        messages.put(203105, ign + " goes for early Mejais " + diff + " more often");
        return("Watch out ! " + messages.get(challengeID));
    }


}

import java.lang.reflect.Array;
import java.util.*;

public class Draft {

    public static void draftPhase(ArrayList<Player> team1, ArrayList<Player> team2) throws Exception {

        ArrayList<String> unavailable = new ArrayList<>();
        ArrayList<String> teammates = new ArrayList<>();
        ArrayList<String> enemies = new ArrayList<>();

//        ArrayList<String> recommendedBans = recommendedBans(); // banned champions, if reroll it'll add the champ to this list
//        for (String ban : recommendedBans) {
//            unavailable.add(ban);
//        }

        Player blue1 = team1.get(0);
        Player blue2 = team1.get(1);
        Player blue3 = team1.get(2);
        Player blue4 = team1.get(3);
        Player blue5 = team1.get(4);

        Player red1 = team2.get(0);
        Player red2 = team2.get(1);
        Player red3 = team2.get(2);
        Player red4 = team2.get(3);
        Player red5 = team2.get(4);

        for (int i = 0; i < 3; i++){
            unavailable = bestBan(team2, unavailable, 1);
            unavailable = bestBan(team1, unavailable, 2);
        }
        System.out.println("");
        teammates = playerPick(blue1, unavailable, teammates, enemies, 1);
        System.out.println("");
        enemies = playerPick(red1, unavailable, enemies, teammates,2);
        enemies = playerPick(red2, unavailable, enemies, teammates,2);
        System.out.println("");
        teammates = playerPick(blue2, unavailable, teammates, enemies,1);
        teammates = playerPick(blue3, unavailable, teammates, enemies,1);
        System.out.println("");
        enemies = playerPick(red3, unavailable, enemies, teammates,2);
        System.out.println("");

        unavailable.addAll(teammates);
        unavailable.addAll(enemies);
        for (int i = 0; i < 2; i++) {
            unavailable = bestBan(team2, unavailable, 1);
            unavailable = bestBan(team1, unavailable, 2);
        }
        System.out.println("");
        enemies = playerPick(red4, unavailable, enemies, teammates,2);
        System.out.println("");
        teammates = playerPick(blue4, unavailable, teammates, enemies,1);
        teammates = playerPick(blue5, unavailable, teammates, enemies,1);
        System.out.println("");
        enemies = playerPick(red5, unavailable, enemies, teammates,2);
    }

    public static ArrayList<String> playerPick(Player p, ArrayList<String> unavailable, ArrayList<String> teammates, ArrayList<String> enemies, int number) throws Exception {
        Map.Entry<String, Double> pick = p.recommendedPick(unavailable, teammates, enemies).get(0);
        ListOfChampions l = new ListOfChampions();
        int bestScorePossible = l.allChampions.size() * (1 + 2 * (teammates.size() + enemies.size()));
        Double rating = pick.getValue() / bestScorePossible;
        System.out.println("Team " + number + " " + p.name + " picks " + pick.getKey() + " with an accuracy of " + rating*100 + "%");
        teammates.add(pick.getKey());
        return teammates;
    }

    public static ArrayList<String> bestBan (ArrayList<Player> team, ArrayList<String> unavailable, int number) throws Exception {
        List<Map.Entry<String, Double>> teamBans = new ArrayList<>();
        ListOfChampions l = new ListOfChampions();
        for (Player p : team) {
            Map.Entry<String, Double> playerBan = p.recommendedBans(unavailable).get(0);
            teamBans.add(playerBan);
        }
        Collections.sort(teamBans, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                return Double.compare(entry2.getValue(), entry1.getValue());
            }
        });
        Double rating = teamBans.get(0).getValue() / (l.allChampions.size() * 5);
        System.out.println("Team " + number + " bans " + teamBans.get(0).getKey() + " with an accuracy of " + rating*100 + "%");
        unavailable.add(teamBans.get(0).getKey());
        return unavailable;
    }
}

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Draft {

    public static void draftPhase(ArrayList<Player> team1, ArrayList<Player> team2) throws Exception {

        ArrayList<String> unavailable = new ArrayList<>();
        ArrayList<String> teammates = new ArrayList<>();
        ArrayList<String> enemies = new ArrayList<>();

//        ArrayList<String> recommendedBans = recommendedBans(); // banned champions, if reroll it'll add the champ to this list
//        for (String ban : recommendedBans) {
//            unavailable.add(ban);
//        }

        unavailable.add("Alistar");
        unavailable.add("Braum");
        unavailable.add("Jhin");
        unavailable.add("MissFortune");
        unavailable.add("Shen");
        unavailable.add("Maokai");

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

        teammates = playerPick(blue1, unavailable, teammates, enemies, 1);
        System.out.println("");
        enemies = playerPick(red1, unavailable, enemies, teammates,2);
        enemies = playerPick(red2, unavailable, enemies, teammates,2);
        System.out.println("");
        teammates = playerPick(blue2, unavailable, teammates, enemies,1);
        teammates = playerPick(blue3, unavailable, teammates, enemies,1);
        System.out.println("");
        enemies = playerPick(red3, unavailable, enemies, teammates,2);
        enemies = playerPick(red4, unavailable, enemies, teammates,2);
        System.out.println("");
        teammates = playerPick(blue4, unavailable, teammates, enemies,1);
        teammates = playerPick(blue5, unavailable, teammates, enemies,1);
        System.out.println("");
        enemies = playerPick(red5, unavailable, enemies, teammates,2);


    }

    public static ArrayList<String> playerPick(Player p, ArrayList<String> unavailable, ArrayList<String> teammates, ArrayList<String> enemies, int number) throws Exception {
        Map.Entry<String, Double> pick = p.recommendedPick(unavailable, teammates, enemies).get(0);
        System.out.println("Team " + number + " " + p.name + " picks " + pick.getKey() + " with a score of " + pick.getValue().toString());
        teammates.add(pick.getKey());
        return teammates;
    }
}

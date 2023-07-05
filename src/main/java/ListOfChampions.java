import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ListOfChampions {

    ArrayList<String> allChampions = new ArrayList<>();
    ArrayList<String> topChampions = new ArrayList<>();
    ArrayList<String> jgChampions = new ArrayList<>();
    ArrayList<String> midChampions = new ArrayList<>();
    ArrayList<String> botChampions = new ArrayList<>();
    ArrayList<String> supChampions = new ArrayList<>();


    public ListOfChampions() {

        this.allChampions.add("Aatrox");
        this.topChampions.add("Aatrox");

        this.allChampions.add("Ahri");
        this.midChampions.add("Ahri");

        this.allChampions.add("Akali");
        this.topChampions.add("Akali");
        this.midChampions.add("Akali");

        this.allChampions.add("Akshan");
        this.topChampions.add("Akshan");

        this.allChampions.add("Alistar");
        this.supChampions.add("Alistar");

        this.allChampions.add("Amumu");
        this.jgChampions.add("Amumu");
        this.supChampions.add("Amumu");

        this.allChampions.add("Anivia");
        this.midChampions.add("Anivia");

        this.allChampions.add("Annie");
        this.midChampions.add("Annie");
        this.supChampions.add("Annie");

        this.allChampions.add("Aphelios");
        this.botChampions.add("Aphelios");

        this.allChampions.add("Ashe");
        this.botChampions.add("Ashe");
        this.supChampions.add("Ashe");

        this.allChampions.add("AurelionSol");
        this.midChampions.add("AurelionSol");

        this.allChampions.add("Azir");
        this.midChampions.add("Azir");

        this.allChampions.add("Bard");
        this.supChampions.add("Bard");

        this.allChampions.add("BelVeth");
        this.jgChampions.add("BelVeth");

        this.allChampions.add("Blitzcrank");
        this.supChampions.add("Blitzcrank");

        this.allChampions.add("Brand");
        this.midChampions.add("Brand");
        this.supChampions.add("Brand");

        this.allChampions.add("Braum");
        this.supChampions.add("Braum");

        this.allChampions.add("Caitlyn");
        this.botChampions.add("Caitlyn");

        this.allChampions.add("Camille");
        this.topChampions.add("Camille");

        this.allChampions.add("Cassiopeia");
        this.topChampions.add("Cassiopeia");
        this.midChampions.add("Cassiopeia");

        this.allChampions.add("ChoGath");
        this.topChampions.add("ChoGath");
        this.midChampions.add("ChoGath");

        this.allChampions.add("Corki");
        this.midChampions.add("Corki");

        this.allChampions.add("Darius");
        this.topChampions.add("Darius");

        this.allChampions.add("Diana");
        this.jgChampions.add("Diana");
        this.midChampions.add("Diana");

        this.allChampions.add("DrMundo");
        this.topChampions.add("DrMundo");

        this.allChampions.add("Draven");
        this.botChampions.add("Draven");

        this.allChampions.add("Ekko");
        this.jgChampions.add("Ekko");
        this.midChampions.add("Ekko");

        this.allChampions.add("Elise");
        this.jgChampions.add("Elise");

        this.allChampions.add("Evelynn");
        this.jgChampions.add("Evelynn");

        this.allChampions.add("Ezreal");
        this.botChampions.add("Ezreal");

        this.allChampions.add("Fiddlesticks");
        this.jgChampions.add("Fiddlesticks");

        this.allChampions.add("Fiora");
        this.topChampions.add("Fiora");

        this.allChampions.add("Fizz");
        this.midChampions.add("Fizz");

        this.allChampions.add("Galio");
        this.topChampions.add("Galio");
        this.midChampions.add("Galio");

        this.allChampions.add("Gangplank");
        this.topChampions.add("Gangplank");
        this.midChampions.add("Gangplank");

        this.allChampions.add("Garen");
        this.topChampions.add("Garen");

        this.allChampions.add("Gnar");
        this.topChampions.add("Gnar");

        this.allChampions.add("Gragas");
        this.topChampions.add("Gragas");
        this.jgChampions.add("Gragas");
        this.midChampions.add("Gragas");

        this.allChampions.add("Graves");
        this.jgChampions.add("Graves");

        this.allChampions.add("Gwen");
        this.topChampions.add("Gwen");

        this.allChampions.add("Hecarim");
        this.jgChampions.add("Hecarim");

        this.allChampions.add("Heimerdinger");
        this.topChampions.add("Heimerdinger");
        this.midChampions.add("Heimerdinger");
        this.supChampions.add("Heimerdinger");

        this.allChampions.add("Illaoi");
        this.topChampions.add("Illaoi");

        this.allChampions.add("Irelia");
        this.topChampions.add("Irelia");
        this.midChampions.add("Irelia");

        this.allChampions.add("Ivern");
        this.jgChampions.add("Ivern");

        this.allChampions.add("Janna");
        this.supChampions.add("Janna");

        this.allChampions.add("JarvanIV");
        this.topChampions.add("JarvanIV");
        this.jgChampions.add("JarvanIV");

        this.allChampions.add("Jax");
        this.topChampions.add("Jax");
        this.jgChampions.add("Jax");

        this.allChampions.add("Jayce");
        this.topChampions.add("Jayce");
        this.midChampions.add("Jayce");

        this.allChampions.add("Jhin");
        this.botChampions.add("Jhin");

        this.allChampions.add("Jinx");
        this.botChampions.add("Jinx");

        this.allChampions.add("KSante");
        this.topChampions.add("KSante");
        this.midChampions.add("KSante");

        this.allChampions.add("KaiSa");
        this.midChampions.add("KaiSa");
        this.botChampions.add("KaiSa");

        this.allChampions.add("Kalista");
        this.botChampions.add("Kalista");

        this.allChampions.add("Karma");
        this.midChampions.add("Karma");
        this.supChampions.add("Karma");

        this.allChampions.add("Karthus");
        this.jgChampions.add("Karthus");
        this.midChampions.add("Karthus");

        this.allChampions.add("Kassadin");
        this.midChampions.add("Kassadin");

        this.allChampions.add("Katarina");
        this.topChampions.add("Katarina");
        this.midChampions.add("Katarina");

        this.allChampions.add("Kayle");
        this.topChampions.add("Kayle");
        this.midChampions.add("Kayle");

        this.allChampions.add("Kayn");
        this.jgChampions.add("Kayn");

        this.allChampions.add("Kennen");
        this.topChampions.add("Kennen");
        this.midChampions.add("Kennen");

        this.allChampions.add("KhaZix");
        this.jgChampions.add("KhaZix");

        this.allChampions.add("Kindred");
        this.jgChampions.add("Kindred");

        this.allChampions.add("Kled");
        this.topChampions.add("Kled");

        this.allChampions.add("KogMaw");
        this.midChampions.add("KogMaw");
        this.botChampions.add("KogMaw");

        this.allChampions.add("LeBlanc");
        this.midChampions.add("LeBlanc");

        this.allChampions.add("LeeSin");
        this.jgChampions.add("LeeSin");

        this.allChampions.add("Leona");
        this.supChampions.add("Leona");

        this.allChampions.add("Lillia");
        this.jgChampions.add("Lillia");

        this.allChampions.add("Lissandra");
        this.midChampions.add("Lissandra");

        this.allChampions.add("Lucian");
        this.botChampions.add("Lucian");

        this.allChampions.add("Lulu");
        this.supChampions.add("Lulu");

        this.allChampions.add("Lux");
        this.midChampions.add("Lux");
        this.supChampions.add("Lux");

        this.allChampions.add("Malphite");
        this.topChampions.add("Malphite");

        this.allChampions.add("Malzahar");
        this.midChampions.add("Malzahar");

        this.allChampions.add("Maokai");
        this.topChampions.add("Maokai");
        this.jgChampions.add("Maokai");
        this.supChampions.add("Maokai");

        this.allChampions.add("MasterYi");
        this.jgChampions.add("MasterYi");

        this.allChampions.add("Milio");
        this.supChampions.add("Milio");

        this.allChampions.add("MissFortune");
        this.botChampions.add("MissFortune");

        this.allChampions.add("Mordekaiser");
        this.topChampions.add("Mordekaiser");

        this.allChampions.add("Morgana");
        this.jgChampions.add("Morgana");
        this.midChampions.add("Morgana");
        this.supChampions.add("Morgana");

        this.allChampions.add("Nami");
        this.supChampions.add("Nami");

        this.allChampions.add("Nasus");
        this.topChampions.add("Nasus");

        this.allChampions.add("Nautilus");
        this.midChampions.add("Nautilus");
        this.supChampions.add("Nautilus");

        this.allChampions.add("Neeko");
        this.jgChampions.add("Neeko");
        this.midChampions.add("Neeko");

        this.allChampions.add("Nidalee");
        this.jgChampions.add("Nidalee");

        this.allChampions.add("Nilah");
        this.botChampions.add("Nilah");

        this.allChampions.add("Nocturne");
        this.jgChampions.add("Nocturne");
        this.midChampions.add("Nocturne");

        this.allChampions.add("Nunu");
        this.jgChampions.add("Nunu");

        this.allChampions.add("Olaf");
        this.topChampions.add("Olaf");
        this.jgChampions.add("Olaf");

        this.allChampions.add("Orianna");
        this.midChampions.add("Orianna");

        this.allChampions.add("Ornn");
        this.topChampions.add("Ornn");

        this.allChampions.add("Pantheon");
        this.topChampions.add("Pantheon");
        this.midChampions.add("Pantheon");
        this.supChampions.add("Pantheon");

        this.allChampions.add("Poppy");
        this.topChampions.add("Poppy");

        this.allChampions.add("Pyke");
        this.supChampions.add("Pyke");

        this.allChampions.add("Qiyana");
        this.jgChampions.add("Qiyana");
        this.midChampions.add("Qiyana");

        this.allChampions.add("Quinn");
        this.topChampions.add("Quinn");

        this.allChampions.add("Rakan");
        this.supChampions.add("Rakan");

        this.allChampions.add("Rammus");
        this.jgChampions.add("Rammus");

        this.allChampions.add("RekSai");
        this.topChampions.add("RekSai");
        this.jgChampions.add("RekSai");

        this.allChampions.add("Rell");
        this.jgChampions.add("Rell");
        this.supChampions.add("Rell");

        this.allChampions.add("Renata");
        this.supChampions.add("Renata");

        this.allChampions.add("Renekton");
        this.topChampions.add("Renekton");

        this.allChampions.add("Rengar");
        this.jgChampions.add("Rengar");

        this.allChampions.add("Riven");
        this.topChampions.add("Riven");

        this.allChampions.add("Rumble");
        this.topChampions.add("Rumble");
        this.jgChampions.add("Rumble");
        this.midChampions.add("Rumble");

        this.allChampions.add("Ryze");
        this.topChampions.add("Ryze");
        this.midChampions.add("Ryze");

        this.allChampions.add("Samira");
        this.botChampions.add("Samira");

        this.allChampions.add("Sejuani");
        this.jgChampions.add("Sejuani");

        this.allChampions.add("Senna");
        this.botChampions.add("Senna");
        this.supChampions.add("Senna");

        this.allChampions.add("Seraphine");
        this.midChampions.add("Seraphine");
        this.botChampions.add("Seraphine");
        this.supChampions.add("Seraphine");

        this.allChampions.add("Sett");
        this.topChampions.add("Sett");
        this.supChampions.add("Sett");

        this.allChampions.add("Shaco");
        this.topChampions.add("Shaco");
        this.jgChampions.add("Shaco");
        this.supChampions.add("Shaco");

        this.allChampions.add("Shen");
        this.topChampions.add("Shen");
        this.supChampions.add("Shen");

        this.allChampions.add("Shyvana");
        this.topChampions.add("Shyvana");
        this.jgChampions.add("Shyvana");

        this.allChampions.add("Singed");
        this.topChampions.add("Singed");

        this.allChampions.add("Sion");
        this.topChampions.add("Sion");

        this.allChampions.add("Sivir");
        this.botChampions.add("Sivir");

        this.allChampions.add("Skarner");
        this.topChampions.add("Skarner");
        this.jgChampions.add("Skarner");

        this.allChampions.add("Sona");
        this.supChampions.add("Sona");

        this.allChampions.add("Soraka");
        this.supChampions.add("Soraka");

        this.allChampions.add("Swain");
        this.topChampions.add("Swain");
        this.midChampions.add("Swain");

        this.allChampions.add("Sylas");
        this.midChampions.add("Sylas");

        this.allChampions.add("Syndra");
        this.midChampions.add("Syndra");

        this.allChampions.add("TahmKench");
        this.topChampions.add("TahmKench");
        this.supChampions.add("TahmKench");

        this.allChampions.add("Taliyah");
        this.jgChampions.add("Taliyah");
        this.midChampions.add("Taliyah");

        this.allChampions.add("Talon");
        this.jgChampions.add("Talon");
        this.midChampions.add("Talon");

        this.allChampions.add("Taric");
        this.supChampions.add("Taric");

        this.allChampions.add("Teemo");
        this.topChampions.add("Teemo");
        this.midChampions.add("Teemo");
        this.supChampions.add("Teemo");

        this.allChampions.add("Thresh");
        this.supChampions.add("Thresh");

        this.allChampions.add("Tristana");
        this.midChampions.add("Tristana");
        this.botChampions.add("Tristana");

        this.allChampions.add("Trundle");
        this.topChampions.add("Trundle");
        this.jgChampions.add("Trundle");

        this.allChampions.add("Tryndamere");
        this.topChampions.add("Tryndamere");
        this.midChampions.add("Tryndamere");

        this.allChampions.add("TwistedFate");
        this.midChampions.add("TwistedFate");

        this.allChampions.add("Twitch");
        this.botChampions.add("Twitch");

        this.allChampions.add("Udyr");
        this.topChampions.add("Udyr");
        this.jgChampions.add("Udyr");

        this.allChampions.add("Urgot");
        this.topChampions.add("Urgot");

        this.allChampions.add("Varus");
        this.midChampions.add("Varus");
        this.botChampions.add("Varus");

        this.allChampions.add("Vayne");
        this.topChampions.add("Vayne");
        this.botChampions.add("Vayne");

        this.allChampions.add("Veigar");
        this.midChampions.add("Veigar");

        this.allChampions.add("VelKoz");
        this.midChampions.add("VelKoz");
        this.supChampions.add("VelKoz");

        this.allChampions.add("Vex");
        this.midChampions.add("Vex");

        this.allChampions.add("Vi");
        this.jgChampions.add("Vi");

        this.allChampions.add("Viego");
        this.jgChampions.add("Viego");

        this.allChampions.add("Viktor");
        this.midChampions.add("Viktor");

        this.allChampions.add("Vladimir");
        this.topChampions.add("Vladimir");
        this.midChampions.add("Vladimir");

        this.allChampions.add("Volibear");
        this.topChampions.add("Volibear");
        this.jgChampions.add("Volibear");

        this.allChampions.add("Warwick");
        this.topChampions.add("Warwick");
        this.jgChampions.add("Warwick");

        this.allChampions.add("MonkeyKing");
        this.topChampions.add("MonkeyKing");
        this.jgChampions.add("MonkeyKing");

        this.allChampions.add("Xayah");
        this.botChampions.add("Xayah");

        this.allChampions.add("Xerath");
        this.midChampions.add("Xerath");
        this.supChampions.add("Xerath");

        this.allChampions.add("XinZhao");
        this.jgChampions.add("XinZhao");

        this.allChampions.add("Yasuo");
        this.topChampions.add("Yasuo");
        this.midChampions.add("Yasuo");

        this.allChampions.add("Yone");
        this.topChampions.add("Yone");
        this.midChampions.add("Yone");

        this.allChampions.add("Yorick");
        this.topChampions.add("Yorick");

        this.allChampions.add("Yuumi");
        this.supChampions.add("Yuumi");

        this.allChampions.add("Zac");
        this.jgChampions.add("Zac");

        this.allChampions.add("Zed");
        this.midChampions.add("Zed");

        this.allChampions.add("Zeri");
        this.botChampions.add("Zeri");

        this.allChampions.add("Ziggs");
        this.midChampions.add("Ziggs");
        this.botChampions.add("Ziggs");

        this.allChampions.add("Zilean");
        this.midChampions.add("Zilean");
        this.supChampions.add("Zilean");

        this.allChampions.add("Zoe");
        this.midChampions.add("Zoe");

        this.allChampions.add("Zyra");
        this.jgChampions.add("Zyra");
        this.midChampions.add("Zyra");
        this.supChampions.add("Zyra");
    }

    public void laneVSall(String lane) {
        HashMap<String, ArrayList<String>> hashLanes = new HashMap<>();
        hashLanes.put("top", this.topChampions);
        hashLanes.put("jg", this.jgChampions);
        hashLanes.put("mid", this.midChampions);
        hashLanes.put("bot", this.botChampions);
        hashLanes.put("sup", this.supChampions);
        ArrayList<String> laners = hashLanes.get(lane);
        ArrayList<String> otherChamps = this.allChampions;
        otherChamps.removeAll(laners);
        ArrayList<ArrayList<String>> matchups = new ArrayList<>();
        ArrayList<ArrayList<String>> notYetRegistered = new ArrayList<>();
        notYetRegistered.add(new ArrayList<>(Arrays.asList("Gwen", "Milio")));
        notYetRegistered.add(new ArrayList<>(Arrays.asList("KSante", "Milio")));
        notYetRegistered.add(new ArrayList<>(Arrays.asList("Sett", "Milio")));
        notYetRegistered.add(new ArrayList<>(Arrays.asList("Yone", "Milio")));
        notYetRegistered.add(new ArrayList<>(Arrays.asList("Lillia", "Milio")));
        notYetRegistered.add(new ArrayList<>(Arrays.asList("Vex", "Milio")));
        notYetRegistered.add(new ArrayList<>(Arrays.asList("Nilah", "Milio")));
        for (String s1 : laners) {
            System.out.println(s1);
            Champion c1 = new Champion(s1);
            for (String s2 : otherChamps) {
                if (notYetRegistered.contains(Arrays.asList(s1, s2)) || s1.equals("Milio")){
                    continue;
                }
                ArrayList<String> thisChamp = new ArrayList<>();
                Champion c2 = new Champion(s2);
                ArrayList<Double> res = c1.get1v1Stats(c2, false);
                thisChamp.add(c1.name);
                thisChamp.add(c2.name);
                thisChamp.add(res.get(0).toString());
                thisChamp.add(res.get(1).toString());
                matchups.add(thisChamp);
            }
        }
        appendToFile(matchups, "src/main/java/lists/" + lane + "VsAllMatchups.txt");
    }

    public void laneMatchups(String lane) {
        HashMap<String, ArrayList<String>> hashLanes = new HashMap<>();
        hashLanes.put("top", this.topChampions);
        hashLanes.put("jg", this.jgChampions);
        hashLanes.put("mid", this.midChampions);
        hashLanes.put("bot", this.botChampions);
        hashLanes.put("sup", this.supChampions);
        ArrayList<String> laners = hashLanes.get(lane);
        ArrayList<ArrayList<String>> matchups = new ArrayList<>();
        for (String s1 : laners) {
            Champion c1 = new Champion(s1);
            ArrayList<String> thisChamp = new ArrayList<>();
            for (String s2 : laners) {
                if (!s2.equals(s1)) {
                    Champion c2 = new Champion(s2);
                    ArrayList<Double> res = c1.get1v1Stats(c2, false);
                    thisChamp.add(c1.name);
                    thisChamp.add(c2.name);
                    thisChamp.add(res.get(0).toString());
                    thisChamp.add(res.get(1).toString());
                    matchups.add(thisChamp);
                }
            }
        }
        appendToFile(matchups, "java/lists/" + lane + "Matchups.txt");
    }

    public static void appendToFile(ArrayList<ArrayList<String>> matchups, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (ArrayList<String> matchup : matchups) {
                for (String data : matchup) {
                    writer.write(data);
                    writer.write(" ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Champion {
    String name;

    public Champion(String name) {
        this.name = name;
    }

    public ArrayList<Double> get1v1Stats(Champion c, boolean synergy) {
        // Returns WinRate, average KDA and PickRate of the champion,
        // Against the given champion if synergy = false, and with if synergy = true

        String type = "counter";
        String vsORand = "vs";
        if (synergy) {
            type = "synergy";
            vsORand = "and";
        }
        ArrayList<Double> stats = new ArrayList<>();
        try {
            URL url = new URL("https://www.mobachampion.com/" + type + "/" + this.name + "-" + vsORand + "-" + c.name + "/");
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder htmlContent = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }

            String scrapped = htmlContent.toString();
            reader.close();
            String targetString = "tom\">      ";

            int targetStringLength = targetString.length();
            int startIndex = 0;

            while (startIndex != -1) {
                startIndex = scrapped.indexOf(targetString, startIndex);
                if (startIndex != -1) {
                    startIndex += targetStringLength;
                    String extractedText = scrapped.substring(startIndex, startIndex + 5);
                    stats.add(Double.parseDouble(extractedText));
                    startIndex += 5;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    public HashMap<String, ArrayList<Double>> getAllStats(boolean synergy) {
        // function get1v1Stats called for all champions

        HashMap<String, ArrayList<Double>> stats = new HashMap<>();
        ListOfChampions c = new ListOfChampions();
        for (String str : c.allChampions) {
            if (!str.equals(this.name)) {
                Champion champ = new Champion(str);
                stats.put(str, get1v1Stats(champ, synergy));
            }
        }
        return stats;
    }

    public static String championFromID(int champID) {
        HashMap<Integer, String> championMap = new HashMap<>();
        championMap.put(266, "Aatrox");
        championMap.put(103, "Ahri");
        championMap.put(84, "Akali");
        championMap.put(166, "Akshan");
        championMap.put(12, "Alistar");
        championMap.put(32, "Amumu");
        championMap.put(34, "Anivia");
        championMap.put(1, "Annie");
        championMap.put(523, "Aphelios");
        championMap.put(22, "Ashe");
        championMap.put(136, "AurelionSol");
        championMap.put(268, "Azir");
        championMap.put(432, "Bard");
        championMap.put(200, "BelVeth");
        championMap.put(53, "Blitzcrank");
        championMap.put(63, "Brand");
        championMap.put(201, "Braum");
        championMap.put(51, "Caitlyn");
        championMap.put(164, "Camille");
        championMap.put(69, "Cassiopeia");
        championMap.put(31, "ChoGath");
        championMap.put(42, "Corki");
        championMap.put(122, "Darius");
        championMap.put(131, "Diana");
        championMap.put(119, "Draven");
        championMap.put(36, "DrMundo");
        championMap.put(245, "Ekko");
        championMap.put(60, "Elise");
        championMap.put(28, "Evelynn");
        championMap.put(81, "Ezreal");
        championMap.put(9, "Fiddlesticks");
        championMap.put(114, "Fiora");
        championMap.put(105, "Fizz");
        championMap.put(3, "Galio");
        championMap.put(41, "Gangplank");
        championMap.put(86, "Garen");
        championMap.put(150, "Gnar");
        championMap.put(79, "Gragas");
        championMap.put(104, "Graves");
        championMap.put(887, "Gwen");
        championMap.put(120, "Hecarim");
        championMap.put(74, "Heimerdinger");
        championMap.put(420, "Illaoi");
        championMap.put(39, "Irelia");
        championMap.put(427, "Ivern");
        championMap.put(40, "Janna");
        championMap.put(59, "JarvanIV");
        championMap.put(24, "Jax");
        championMap.put(126, "Jayce");
        championMap.put(202, "Jhin");
        championMap.put(222, "Jinx");
        championMap.put(145, "KaiSa");
        championMap.put(429, "Kalista");
        championMap.put(43, "Karma");
        championMap.put(30, "Karthus");
        championMap.put(38, "Kassadin");
        championMap.put(55, "Katarina");
        championMap.put(10, "Kayle");
        championMap.put(141, "Kayn");
        championMap.put(85, "Kennen");
        championMap.put(121, "KhaZix");
        championMap.put(203, "Kindred");
        championMap.put(240, "Kled");
        championMap.put(96, "KogMaw");
        championMap.put(897, "KSante");
        championMap.put(7, "LeBlanc");
        championMap.put(64, "LeeSin");
        championMap.put(89, "Leona");
        championMap.put(876, "Lillia");
        championMap.put(127, "Lissandra");
        championMap.put(236, "Lucian");
        championMap.put(117, "Lulu");
        championMap.put(99, "Lux");
        championMap.put(54, "Malphite");
        championMap.put(90, "Malzahar");
        championMap.put(57, "Maokai");
        championMap.put(11, "MasterYi");
        championMap.put(902, "Milio");
        championMap.put(21, "MissFortune");
        championMap.put(62, "MonkeyKing");
        championMap.put(82, "Mordekaiser");
        championMap.put(25, "Morgana");
        championMap.put(267, "Nami");
        championMap.put(75, "Nasus");
        championMap.put(111, "Nautilus");
        championMap.put(518, "Neeko");
        championMap.put(76, "Nidalee");
        championMap.put(895, "Nilah");
        championMap.put(56, "Nocturne");
        championMap.put(20, "Nunu");
        championMap.put(2, "Olaf");
        championMap.put(61, "Orianna");
        championMap.put(516, "Ornn");
        championMap.put(80, "Pantheon");
        championMap.put(78, "Poppy");
        championMap.put(555, "Pyke");
        championMap.put(246, "Qiyana");
        championMap.put(133, "Quinn");
        championMap.put(497, "Rakan");
        championMap.put(33, "Rammus");
        championMap.put(421, "RekSai");
        championMap.put(526, "Rell");
        championMap.put(888, "Renata");
        championMap.put(58, "Renekton");
        championMap.put(107, "Rengar");
        championMap.put(92, "Riven");
        championMap.put(68, "Rumble");
        championMap.put(13, "Ryze");
        championMap.put(360, "Samira");
        championMap.put(113, "Sejuani");
        championMap.put(235, "Senna");
        championMap.put(147, "Seraphine");
        championMap.put(875, "Sett");
        championMap.put(35, "Shaco");
        championMap.put(98, "Shen");
        championMap.put(102, "Shyvana");
        championMap.put(27, "Singed");
        championMap.put(14, "Sion");
        championMap.put(15, "Sivir");
        championMap.put(72, "Skarner");
        championMap.put(37, "Sona");
        championMap.put(16, "Soraka");
        championMap.put(50, "Swain");
        championMap.put(517, "Sylas");
        championMap.put(134, "Syndra");
        championMap.put(223, "TahmKench");
        championMap.put(163, "Taliyah");
        championMap.put(91, "Talon");
        championMap.put(44, "Taric");
        championMap.put(17, "Teemo");
        championMap.put(412, "Thresh");
        championMap.put(18, "Tristana");
        championMap.put(48, "Trundle");
        championMap.put(23, "Tryndamere");
        championMap.put(4, "TwistedFate");
        championMap.put(29, "Twitch");
        championMap.put(77, "Udyr");
        championMap.put(6, "Urgot");
        championMap.put(110, "Varus");
        championMap.put(67, "Vayne");
        championMap.put(45, "Veigar");
        championMap.put(161, "VelKoz");
        championMap.put(711, "Vex");
        championMap.put(254, "Vi");
        championMap.put(234, "Viego");
        championMap.put(112, "Viktor");
        championMap.put(8, "Vladimir");
        championMap.put(106, "Volibear");
        championMap.put(19, "Warwick");
        championMap.put(498, "Xayah");
        championMap.put(101, "Xerath");
        championMap.put(5, "XinZhao");
        championMap.put(157, "Yasuo");
        championMap.put(777, "Yone");
        championMap.put(83, "Yorick");
        championMap.put(350, "Yuumi");
        championMap.put(154, "Zac");
        championMap.put(238, "Zed");
        championMap.put(221, "Zeri");
        championMap.put(115, "Ziggs");
        championMap.put(26, "Zilean");
        championMap.put(142, "Zoe");
        championMap.put(143, "Zyra");

        return championMap.get(champID);
    }

}

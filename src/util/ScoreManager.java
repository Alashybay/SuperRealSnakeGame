package util;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;


public class ScoreManager {
    private static final Path SCORE_FILE = Paths.get("resources/player_scores.txt");

    public static Map<String, Integer> load() {
        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            for (String line : Files.readAllLines(SCORE_FILE)) {
                String[] parts = line.split(":");
                map.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
        } catch (IOException e) {

        }
        return map;
    }

    public static void save(Map<String, Integer> map) {
        try (BufferedWriter w = Files.newBufferedWriter(SCORE_FILE)) {
            for (var e : map.entrySet()) {
                w.write(e.getKey() + ": " + e.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateScore(String player, int delta) {
        Map<String, Integer> scores = load();
        scores.put(player, Math.max(0, scores.getOrDefault(player, 0) + delta));
        save(scores);
    }
}

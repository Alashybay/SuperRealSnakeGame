package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Demonstrates:
 * - Encapsulation: private fields and controlled access
 * - Static Polymorphism: overloaded methods
 * - Parametric Polymorphism: use of generics (Map<String, Integer>)
 */
public class ScoreManager {
    private static final Path SCORE_FILE = Paths.get("resources/player_scores.txt");

    // Load with default file (overloaded)
    public static Map<String, Integer> load() {
        return load(SCORE_FILE);
    }

    // Static polymorphism: overloaded method
    public static Map<String, Integer> load(Path filePath) {
        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            for (String line : Files.readAllLines(filePath)) {
                String[] parts = line.split(":");
                map.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    // Save with default behavior
    public static void save(Map<String, Integer> map) {
        save(map, false);
    }

    // Static polymorphism: overloaded method with append option
    public static void save(Map<String, Integer> map, boolean append) {
        try (BufferedWriter w = Files.newBufferedWriter(
                SCORE_FILE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                w.write(e.getKey() + ": " + e.getValue());
                w.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update and optionally persist immediately
    public static void updateScore(String player, int delta) {
        updateScore(player, delta, true);
    }

    // Static polymorphism: overloaded updateScore
    public static void updateScore(String player, int delta, boolean persistImmediately) {
        Map<String, Integer> scores = load();
        scores.put(player, Math.max(0, scores.getOrDefault(player, 0) + delta));
        if (persistImmediately) {
            save(scores);
        }
    }
}
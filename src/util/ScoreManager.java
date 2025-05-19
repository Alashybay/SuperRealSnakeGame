package util;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ScoreManager handles score persistence, demonstrating encapsulation, information hiding,
 * polymorphism (overloading, parametric), and exception handling.
 */
public class ScoreManager {
    // Encapsulation: Private static field for file path
    private static final Path SCORE_FILE = Paths.get("resources/player_scores.txt");

    // Abstraction & Information Hiding: Hides file loading logic
    // Parametric Polymorphism: Uses Map<String, Integer> for type safety
    public static Map<String, Integer> load() {
        return load(SCORE_FILE);
    }

    // Polymorphism (Overloading): Overloaded method for custom file path
    // Exception Handling: Handles IOException and NumberFormatException
    public static Map<String, Integer> load(Path filePath) {
        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            for (String line : Files.readAllLines(filePath)) {
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    System.err.println("Invalid score format: " + line);
                    continue;
                }
                try {
                    map.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid score value: " + parts[1]);
                }
            }
        } catch (IOException e) {
            // Exception Handling: User feedback via JOptionPane
            JOptionPane.showMessageDialog(null, "Failed to load scores: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
        return map;
    }

    // Abstraction: Hides file saving logic
    public static void save(Map<String, Integer> map) {
        save(map, false);
    }

    // Polymorphism (Overloading): Overloaded method with append option
    // Exception Handling: Uses try-with-resources for safe file handling
    public static void save(Map<String, Integer> map, boolean append) {
        try (BufferedWriter w = Files.newBufferedWriter(
                SCORE_FILE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                w.write(e.getKey() + ": " + e.getValue());
                w.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save scores: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Abstraction: Hides score update logic
    public static void updateScore(String player, int delta) {
        updateScore(player, delta, true);
    }

    // Polymorphism (Overloading): Overloaded method with persistence option
    public static void updateScore(String player, int delta, boolean persistImmediately) {
        Map<String, Integer> scores = load();
        scores.put(player, Math.max(0, scores.getOrDefault(player, 0) + delta));
        if (persistImmediately) {
            save(scores);
        }
    }
}
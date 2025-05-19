package app;

import util.ScoreManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The MainMenu class represents the main menu of the Snake Adventures game.
 * Demonstrates OOP concepts: encapsulation, information hiding, composition, abstraction,
 * polymorphism (overloading), and extensibility.
 */
public class MainMenu {
    // Encapsulation: Private field restricts direct access, controlled via methods
    private final JFrame frame;
    // Encapsulation: Static constants for panel dimensions, shared across instances
    private static final int PANEL_WIDTH  = 600;
    private static final int PANEL_HEIGHT = 600;

    // Constructor: Initializes the menu, demonstrating composition and encapsulation
    public MainMenu() {
        // Composition: MainMenu contains a JFrame
        frame = new JFrame("Snake Adventures");
        // Encapsulation: Frame configuration is hidden within constructor
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        // Composition: Menu contains JButtons, created using abstracted method
        // Extensibility: New buttons can be added easily
        var btnSingle = makeButton("1: Single Player", () -> launchGame(1), Color.DARK_GRAY);
        btnSingle.setForeground(Color.BLACK);
        var btnLocal  = makeButton("2: Local 2-Player", () -> launchGame(2), Color.DARK_GRAY);
        btnLocal.setForeground(Color.BLACK);
        var btnAI     = makeButton("3: Vs AI", () -> launchGame(3), Color.DARK_GRAY);
        btnAI.setForeground(Color.BLACK);
        var btnScore  = makeButton("S: Scoreboard", () -> showScores());
        btnScore.setForeground(Color.BLACK);

        // Composition: Adding buttons to frame
        frame.add(btnSingle);
        frame.add(btnLocal);
        frame.add(btnAI);
        frame.add(btnScore);
        frame.setVisible(true);
    }

    // Abstraction & Information Hiding: Abstracts button creation, hides configuration details
    // Polymorphism (Overloading): Original method for default button styling
    private JButton makeButton(String text, Runnable action) {
        var btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        // Polymorphism (Inclusion): Lambda enables dynamic behavior for ActionListener
        btn.addActionListener((ActionEvent e) -> action.run());
        return btn;
    }

    // Polymorphism (Overloading): Overloaded method to customize button background color
    private JButton makeButton(String text, Runnable action, Color bgColor) {
        var btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.addActionListener((ActionEvent e) -> action.run());
        return btn;
    }

    // Abstraction: Hides game launch logic, interacts with SnakeGame
    private void launchGame(int mode) {
        frame.dispose();
        SnakeGame.start(mode);
    }

    // Abstraction & Information Hiding: Hides score retrieval and display logic
    // Composition: Uses ScoreManager to load scores
    private void showScores() {
        var scores = ScoreManager.load();
        String msg = scores.entrySet().stream()
            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
            .map(e -> e.getKey() + ": " + e.getValue())
            .collect(Collectors.joining("\n"));
        JOptionPane.showMessageDialog(frame, msg.isEmpty() ? "No scores yet." : msg,
                                      "High Scores", JOptionPane.PLAIN_MESSAGE);
    }

    // Extensibility: Main method allows easy integration with new features
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
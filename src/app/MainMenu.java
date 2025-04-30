package app;

import util.ScoreManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The MainMenu class represents the main menu of the Snake Adventures game.
 * It demonstrates several Object-Oriented Programming (OOP) concepts:
 *
 * 1. **Encapsulation**:
 *    - The `frame` field is private, ensuring that it cannot be accessed directly from outside the class.
 *    - Methods like `makeButton`, `launchGame`, and `showScores` encapsulate specific behaviors, 
 *      hiding implementation details from the rest of the program.
 *
 * 2. **Abstraction**:
 *    - The `makeButton` method abstracts the repetitive process of creating and configuring buttons,
 *      making the code more readable and reusable.
 *    - The `launchGame` and `showScores` methods abstract specific actions triggered by the buttons.
 *
 * 3. **Inheritance**:
 *    - While this class does not directly extend another class, it utilizes inheritance through the 
 *      use of `JFrame` and `JButton`, which are part of the Swing library. These classes inherit 
 *      behavior from their respective parent classes in the Java Swing framework.
 *
 * 4. **Polymorphism**:
 *    - Polymorphism is demonstrated through the use of lambda expressions for `ActionListener` 
 *      (e.g., `(ActionEvent e) -> action.run()`), allowing different behaviors to be passed 
 *      dynamically to buttons.
 *
 * 5. **Static Members**:
 *    - The `PANEL_WIDTH` and `PANEL_HEIGHT` constants are declared as `static final`, ensuring 
 *      that they are shared across all instances of the class and cannot be modified.
 *
 * 6. **Composition**:
 *    - The `MainMenu` class is composed of several Swing components (`JFrame`, `JButton`), 
 *      demonstrating the "has-a" relationship.
 *
 * 7. **Single Responsibility Principle (SRP)**:
 *    - The `MainMenu` class is responsible only for managing the main menu of the game, adhering 
 *      to the SRP principle.
 *
 * This class serves as the entry point for the game, initializing the main menu and handling 
 * user interactions to launch different game modes or display the scoreboard.
 */
public class MainMenu {
    private final JFrame frame;
    private static final int PANEL_WIDTH  = 600;
    private static final int PANEL_HEIGHT = 600;

    public MainMenu() {
        frame = new JFrame("Snake Adventures");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        var btnSingle = makeButton("1: Single Player", () -> launchGame(1));
        btnSingle.setForeground(Color.BLACK);
        var btnLocal  = makeButton("2: Local 2-Player", () -> launchGame(2));
        btnLocal.setForeground(Color.BLACK);
        var btnAI     = makeButton("3: Vs AI", () -> launchGame(3));
        btnAI.setForeground(Color.BLACK);
        var btnScore  = makeButton("S: Scoreboard", () -> showScores());
        btnScore.setForeground(Color.BLACK);

        frame.add(btnSingle);
        frame.add(btnLocal);
        frame.add(btnAI);
        frame.add(btnScore);
        frame.setVisible(true);
    }

    private JButton makeButton(String text, Runnable action) {
        var btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.addActionListener((ActionEvent e) -> action.run());
        return btn;
    }

    private void launchGame(int mode) {
        frame.dispose();
        SnakeGame.start(mode);
    }

    private void showScores() {
        var scores = ScoreManager.load();
        String msg = scores.entrySet().stream()
            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
            .map(e -> e.getKey() + ": " + e.getValue())
            .collect(Collectors.joining("\n"));
        JOptionPane.showMessageDialog(frame, msg.isEmpty() ? "No scores yet." : msg,
                                      "High Scores", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}


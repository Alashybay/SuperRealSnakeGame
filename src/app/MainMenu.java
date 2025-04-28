package app;

import util.ScoreManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.stream.Collectors;

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


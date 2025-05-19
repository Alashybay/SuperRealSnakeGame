package app;

import core.Board;
import core.Food;
import core.GameMode;
import core.StandardGameMode;
import enums.ControlType;
import player.Player;
import player.HumanPlayer;
import player.AIPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * SnakeGame manages the game loop and UI, demonstrating OOP concepts: encapsulation,
 * composition, inheritance, subtyping, polymorphism (coercion, inclusion, parametric),
 * exception handling, and extensibility.
 */
public class SnakeGame extends JPanel implements Runnable {
    // Encapsulation: Private fields with controlled access
    private static final int PANEL_WIDTH  = 800;
    private static final int PANEL_HEIGHT = 650;
    private final Board board;
    private final Food food;
    // Parametric Polymorphism: Generic List<Player> for type-safe player collection
    private final List<Player> players = new ArrayList<>();
    private boolean running;
    private boolean paused = false;
    private final Object pauseLock = new Object();
    private final int mode;
    // Composition & Extensibility: GameMode interface for pluggable game logic
    private final GameMode gameMode;

    // Constructor: Initializes game, demonstrating encapsulation and composition
    private SnakeGame(int mode) {
        this.mode = mode;
        // Inheritance: SnakeGame extends JPanel, inheriting UI capabilities
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);

        // Composition: Contains Board and Food objects
        board = new Board(40, 30, 30);
        food = new Food(board.getCols(), board.getRows());
        // Extensibility: Uses GameMode for flexible game logic
        gameMode = new StandardGameMode();

        // Subtyping: HumanPlayer and AIPlayer as Player subtypes
        String p1 = JOptionPane.showInputDialog("Player1 name:", "Player1");
        var player1 = new HumanPlayer(
            p1, board.getCols()/2, board.getRows()/2, ControlType.ARROWS, Color.GREEN
        );
        player1.getKeyAdapter().ifPresent(this::addKeyListener);
        players.add(player1);

        switch (mode) {
            case 2 -> {
                String p2 = JOptionPane.showInputDialog("Player2 name:", "Player2");
                var player2 = new HumanPlayer(
                    p2, board.getCols()/2, board.getRows()/4, ControlType.WASD, Color.BLUE
                );
                player2.getKeyAdapter().ifPresent(this::addKeyListener);
                players.add(player2);
            }
            case 3 -> {
                var ai = new AIPlayer(
                    "CPU", board.getCols()/2, board.getRows()/4, food
                );
                players.add(ai);
            }
        }

        // Composition: Key listener for pause functionality
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    togglePauseMenu();
                }
            }
        });
    }

    // Abstraction & Information Hiding: Hides pause menu logic
    private void togglePauseMenu() {
        // Encapsulation: Modifies paused state safely
        synchronized (pauseLock) {
            paused = true;
        }
        // Composition: Uses JDialog for pause menu
        Window w = SwingUtilities.getWindowAncestor(this);
        JDialog dlg = new JDialog(w, "Paused", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setLayout(new GridLayout(3,1,5,5));
        JButton btnResume = new JButton("Resume");
        JButton btnRestart = new JButton("Restart");
        JButton btnMenu = new JButton("Main Menu");
        dlg.add(btnResume);
        dlg.add(btnRestart);
        dlg.add(btnMenu);
        dlg.pack();
        dlg.setSize(300, dlg.getHeight());
        dlg.setResizable(false);
        dlg.setLocationRelativeTo(w);

        // Polymorphism (Inclusion): Dynamic behavior via ActionListeners
        btnResume.addActionListener(a -> {
            dlg.dispose();
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
            }
        });
        btnRestart.addActionListener(a -> {
            dlg.dispose();
            w.dispose();
            SnakeGame.start(mode);
        });
        btnMenu.addActionListener(a -> {
            dlg.dispose();
            w.dispose();
            MainMenu.main(null);
        });

        dlg.setVisible(true);
    }

    // Extensibility: Static method to start game, allows new modes
    public static void start(int mode) {
        JFrame wnd = new JFrame("Snake Adventures");
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setResizable(false);
        var game = new SnakeGame(mode);
        // Composition: JFrame contains SnakeGame panel
        wnd.setContentPane(game);
        wnd.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        wnd.setLocationRelativeTo(null);
        wnd.setVisible(true);
        new Thread(game).start();
    }

    // Polymorphism (Coercion): Explicit casting for AI-specific behavior
    public void adjustAIDifficulty(Player player, int difficulty) {
        if (player instanceof AIPlayer aiPlayer) {
            // Hypothetical method to set AI difficulty
            aiPlayer.setDifficulty(difficulty);
        }
    }

    // Game loop: Demonstrates polymorphism (inclusion) and exception handling
    @Override
    public void run() {
        running = true;
        while (running) {
            // Encapsulation: Thread-safe pause handling
            synchronized (pauseLock) {
                while (paused) {
                    // Exception Handling: Catches InterruptedException
                    try { pauseLock.wait(); }
                    catch (InterruptedException ignored) {}
                }
            }

            // Extensibility: Delegates to GameMode for game logic
            // Polymorphism (Inclusion): Calls move() on Player subtypes
            gameMode.update(this);
            repaint();
            // Exception Handling: Handles thread sleep interruption
            try { Thread.sleep(100); }
            catch (InterruptedException ignored) {}
        }
    }

    // Abstraction: Delegates rendering to components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
        food.draw(g, board.getCellSize());
        // Subtyping & Polymorphism: Iterates over Player subtypes
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            g.setColor(p.getColor());
            p.getSnake().draw(g, board.getCellSize());
            g.setColor(Color.BLACK);
            g.drawString(p.getName() + ": " + p.getScore(), 10, 20 + 15 * i);
        }
    }

    // Encapsulation: Getters for GameMode access
    public Board getBoard() { return board; }
    public Food getFood() { return food; }
    public List<Player> getPlayers() { return players; }
    public void setRunning(boolean running) { this.running = running; }
}
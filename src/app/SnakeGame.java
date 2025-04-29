package app;

import core.Board;
import core.Food;
import enums.ControlType;
import util.ScoreManager;
import player.Player;
import player.HumanPlayer;
import player.AIPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame extends JPanel implements Runnable {
    private static final int PANEL_WIDTH  = 800;
    private static final int PANEL_HEIGHT = 650;

    private final Board board;
    private final Food food;
    private final List<Player> players = new ArrayList<>();
    private boolean running;
    private boolean paused = false;
    private final Object pauseLock = new Object();

    // store the mode so we can restart
    private final int mode;

    private SnakeGame(int mode) {
        this.mode = mode;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);

        // pause menu on ESC
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    togglePauseMenu();
                }
            }
        });

        board = new Board(40, 30, 30);
        food  = new Food(board.getCols(), board.getRows());

        String p1 = JOptionPane.showInputDialog("Player1 name:", "Player1");
        var player1 = new HumanPlayer(
            p1,
            board.getCols()/2,
            board.getRows()/2,
            ControlType.ARROWS,
            Color.GREEN     // always green
        );
        player1.getKeyAdapter().ifPresent(this::addKeyListener);
        players.add(player1);

        switch (mode) {
            case 2 -> {
                String p2 = JOptionPane.showInputDialog("Player2 name:", "Player2");
                var player2 = new HumanPlayer(
                    p2,
                    board.getCols()/2,
                    board.getRows()/4,
                    ControlType.WASD,
                    Color.BLUE     // second player blue
                );
                player2.getKeyAdapter().ifPresent(this::addKeyListener);
                players.add(player2);
            }
            case 3 -> {
                var ai = new AIPlayer(
                  "CPU",
                  board.getCols()/2,
                  board.getRows()/4,
                  food
                );
                players.add(ai);
            }
        }
    }

    /** Pauses the loop and shows a modal dialog. */
    private void togglePauseMenu() {
        synchronized (pauseLock) {
            paused = true;
        }
        // find our top‐level frame
        Window w = SwingUtilities.getWindowAncestor(this);

        // build the dialog
        JDialog dlg = new JDialog(w, "Paused", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setLayout(new GridLayout(3,1,5,5));
        JButton btnResume = new JButton("Resume");
        JButton btnRestart = new JButton("Restart");
        JButton btnMenu    = new JButton("Main Menu");
        dlg.add(btnResume);
        dlg.add(btnRestart);
        dlg.add(btnMenu);
        dlg.pack();
        dlg.setSize(300, dlg.getHeight());  
        dlg.setResizable(false); 
        dlg.setLocationRelativeTo(w);

        // wire buttons
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

    public static void start(int mode) {
        JFrame wnd = new JFrame("Snake Adventures");
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setResizable(false);

        var game = new SnakeGame(mode);
        wnd.setContentPane(game);

        // lock size
        wnd.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        wnd.setLocationRelativeTo(null);
        wnd.setVisible(true);

        new Thread(game).start();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            // pause check
            synchronized (pauseLock) {
                while (paused) {
                    try { pauseLock.wait(); }
                    catch (InterruptedException ignored) {}
                }
            }

            for (Player p : players) {
                p.move();
                if (board.checkFood(p.getSnake(), food)) {
                    p.addScore(30);
                    food.respawn();
                }
                if (board.checkCollision(p.getSnake())) {
                    running = false;
                    handleGameOver(p);
                    break;
                }
            }

            repaint();
            try { Thread.sleep(100); }
            catch (InterruptedException ignored) {}
        }
    }

    private void handleGameOver(Player loser) {
        JOptionPane.showMessageDialog(this, loser.getName() + " crashed!");
        for (Player p : players) {
            int delta = (p == loser ? -20 : 15);
            ScoreManager.updateScore(p.getName(), delta);
        }
        MainMenu.main(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
        food.draw(g, board.getCellSize());
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            // set the color from Player
            g.setColor(p.getColor());
            p.getSnake().draw(g, board.getCellSize());

            g.setColor(Color.BLACK);
            g.drawString(p.getName() + ": " + p.getScore(), 10, 20 + 15 * i);
        }
    }
}

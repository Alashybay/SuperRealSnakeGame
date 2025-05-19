package core;

import app.MainMenu;
import app.SnakeGame;
import player.Player;
import util.ScoreManager;
import javax.swing.*;

/**
 * StandardGameMode implements the default game logic, demonstrating extensibility,
 * composition, polymorphism (inclusion), and abstraction.
 */
public class StandardGameMode implements GameMode {
    // Abstraction: Hides initialization logic
    @Override
    public void initialize(SnakeGame game) {
        // No additional initialization needed; handled by SnakeGame constructor
    }

    // Polymorphism (Inclusion): Calls move() on Player subtypes
    // Abstraction: Hides game update logic
    @Override
    public void update(SnakeGame game) {
        // Composition: Interacts with game’s Board, Food, and Players
        for (Player p : game.getPlayers()) {
            p.move();
            if (game.getBoard().checkFood(p.getSnake(), game.getFood())) {
                p.addScore(30);
                game.getFood().respawn();
            }
            if (game.getBoard().checkCollision(p.getSnake())) {
                game.setRunning(false);
                handleGameOver(game, p);
                break;
            }
        }
    }

    // Abstraction: Hides game-over logic
    // Composition: Uses ScoreManager for score updates
    @Override
    public void handleGameOver(SnakeGame game, Player loser) {
        JOptionPane.showMessageDialog(null, loser.getName() + " crashed!");
        for (Player p : game.getPlayers()) {
            int delta = (p == loser ? -20 : 15);
            ScoreManager.updateScore(p.getName(), delta);
        }
        MainMenu.main(null);
    }
}
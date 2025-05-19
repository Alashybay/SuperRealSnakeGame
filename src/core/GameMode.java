package core;

import app.SnakeGame;
import player.Player;

/**
 * GameMode interface defines the contract for game modes, demonstrating abstraction
 * and extensibility.
 */
public interface GameMode {
    // Abstraction: Defines initialization behavior
    void initialize(SnakeGame game);
    // Abstraction: Defines game update logic
    void update(SnakeGame game);
    // Abstraction: Defines game-over handling
    void handleGameOver(SnakeGame game, Player loser);
}

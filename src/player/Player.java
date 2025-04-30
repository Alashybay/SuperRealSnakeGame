package player;

import core.Snake;
import java.awt.Color;
import core.Movable;

/**
 * Abstract class demonstrating:
 * - Abstraction: defines common behavior
 * - Inheritance: subclassed by HumanPlayer and AIPlayer
 * - Encapsulation: private fields with getters/setters
 */
public abstract class Player implements Movable {
    private final String name;
    private final Snake snake;
    private int score;
    private final Color color;

    public Player(String name, int startX, int startY, Color color) {
        this.name = name;
        this.color = color;
        this.snake = new Snake(startX, startY, color);
        this.score = 0;
    }

    // Dynamic polymorphism: subclasses override this method
    @Override
    public abstract void move();

    // Encapsulation via getters
    public Snake getSnake() { return snake; }
    public String getName() { return name; }
    public Color getColor() { return color; }
    public int getScore() { return score; }

    // Encapsulated method to modify state
    public void addScore(int delta) {
        this.score = Math.max(0, this.score + delta);
    }

    // Static polymorphism: overloaded setter
    public void setScore(int score) {
        this.score = Math.max(0, score);
    }
}
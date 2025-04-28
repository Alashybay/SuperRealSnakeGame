package player;

import core.Snake;
import java.awt.Color;

public abstract class Player {
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

    public abstract void move();

    public Snake getSnake() {
        return snake;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int delta) {
        score = Math.max(0, score + delta);
    }
}

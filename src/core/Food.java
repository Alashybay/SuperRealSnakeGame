package core;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Food {
    private Point location;
    private final int cols, rows;
    private final Random rnd = new Random();

    public Food(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        respawn();
    }

    public void respawn() {
        location = new Point(rnd.nextInt(cols), rnd.nextInt(rows));
    }

    public Point getLocation() {
        return location;
    }

    public void draw(Graphics g, int cellSize) {
        g.setColor(Color.RED);
        g.fillOval(location.x * cellSize, location.y * cellSize,
                   cellSize, cellSize);
    }
}


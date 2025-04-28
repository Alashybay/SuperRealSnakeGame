package core;

import java.awt.Graphics;
import java.awt.Color;

public class Board {
    private final int cols, rows, cellSize;

    public Board(int cols, int rows, int cellSize) {
        this.cols = cols;
        this.rows = rows;
        this.cellSize = 600/cellSize;
    }

    public int getCols() { return cols; }
    public int getRows() { return rows; }
    public int getCellSize() { return cellSize; }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= cols; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, rows * cellSize);
        }
        for (int j = 0; j <= rows; j++) {
            g.drawLine(0, j * cellSize, cols * cellSize, j * cellSize);
        }
    }

    public boolean checkCollision(Snake snake) {
        Segment head = snake.getBody().peekFirst();
        int x = head.getPosition().x;
        int y = head.getPosition().y;
        // Wall collision
        if (x < 0 || y < 0 || x >= cols || y >= rows) return true;
        // Self-collision
        int count = 0;
        for (Segment s : snake.getBody()) {
            if (s.getPosition().equals(head.getPosition())) count++;
            if (count > 1) return true;
        }
        return false;
    }

    public boolean checkFood(Snake snake, Food food) {
        Segment head = snake.getBody().peekFirst();
        if (head.getPosition().equals(food.getLocation())) {
            snake.grow();
            return true;
        }
        return false;
    }
}

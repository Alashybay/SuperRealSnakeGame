package core;

import java.awt.Graphics;
import java.awt.Color;

public class Board {
    // Encapsulation: The fields are private, ensuring that they can only be accessed
    // and modified through the public methods provided by the class.
    private final int cols, rows, cellSize;

    // Constructor: Demonstrates encapsulation by initializing the private fields
    // and ensuring that the object is in a valid state when created.
    public Board(int cols, int rows, int cellSize) {
        this.cols = cols;
        this.rows = rows;
        this.cellSize = 600 / cellSize; // Example of abstraction: hiding the calculation logic.
    }

    // Getter methods: Encapsulation is used here to provide controlled access
    // to the private fields.
    public int getCols() { return cols; }
    public int getRows() { return rows; }
    public int getCellSize() { return cellSize; }

    // Polymorphism: The `draw` method uses the `Graphics` object, which is part of
    // Java's AWT library. The actual implementation of `Graphics` is determined
    // at runtime, demonstrating polymorphism.
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= cols; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, rows * cellSize);
        }
        for (int j = 0; j <= rows; j++) {
            g.drawLine(0, j * cellSize, cols * cellSize, j * cellSize);
        }
    }

    // Abstraction: The `checkCollision` method hides the complexity of checking
    // for collisions (wall and self-collision) and provides a simple interface
    // for the caller.
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

    // Abstraction: The `checkFood` method hides the logic of checking if the snake
    // has eaten the food and growing the snake, providing a simple interface.
    public boolean checkFood(Snake snake, Food food) {
        Segment head = snake.getBody().peekFirst();
        if (head.getPosition().equals(food.getLocation())) {
            snake.grow(); // Example of interaction between objects (Board, Snake, and Food).
            return true;
        }
        return false;
    }
}

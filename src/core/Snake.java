package core;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import enums.Direction;

public class Snake {
    // Encapsulation: The fields are private, ensuring that the internal state of the Snake class
    // can only be accessed and modified through public methods.
    private final Deque<Segment> body = new ArrayDeque<>();
    private Direction direction;
    private boolean grow;
    private final Color color;      

    // Constructor: Demonstrates the concept of initialization and encapsulation.
    // The constructor initializes the snake's starting position, direction, and color.
    public Snake(int startX, int startY, Color color) {
        this.direction = Direction.RIGHT;
        this.body.add(new Segment(startX, startY));
        this.color = color;
    } 
    
    // Getter for direction: Encapsulation is used here to provide controlled access to the direction field.
    public Direction getDirection() {
        return direction;
    }

    // Setter for direction: Encapsulation is used to allow controlled modification of the direction field.
    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    // Method to move the snake: Demonstrates behavior encapsulated within the class.
    // The logic for moving the snake is hidden from external classes.
    public void move() {
        Segment head = body.peekFirst();
        int x = head.getPosition().x;
        int y = head.getPosition().y;
        switch (direction) {
            case UP:    y--; break;
            case DOWN:  y++; break;
            case LEFT:  x--; break;
            case RIGHT: x++; break;
        }
        body.addFirst(new Segment(x, y));
        if (!grow) {
            body.removeLast();
        }
        grow = false;
    }

    // Method to grow the snake: Encapsulation is used to modify the internal state of the snake.
    public void grow() {
        this.grow = true;
    }

    // Getter for the body: Encapsulation is used to provide controlled access to the snake's body.
    public Deque<Segment> getBody() {
        return body;
    }

    /** 
     * Draws the snake in its own color.
     * Demonstrates abstraction: The details of how the snake is drawn are hidden from the caller.
     */
    public void draw(Graphics g, int cellSize) {
        g.setColor(color);
        for (Segment s : body) {
            g.fillRect(
                s.getPosition().x * cellSize,
                s.getPosition().y * cellSize,
                cellSize, cellSize
            );
        }
    }
}

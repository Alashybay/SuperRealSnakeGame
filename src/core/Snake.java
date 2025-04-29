package core;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import enums.Direction;

public class Snake {
    private final Deque<Segment> body = new ArrayDeque<>();
    private Direction direction;
    private boolean grow;
    private final Color color;      

    public Snake(int startX, int startY, Color color) {
        this.direction = Direction.RIGHT;
        this.body.add(new Segment(startX, startY));
        this.color = color;
    } 
    
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

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

    public void grow() {
        this.grow = true;
    }

    public Deque<Segment> getBody() {
        return body;
    }

    /** 
     * Draws the snake in its own color.
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

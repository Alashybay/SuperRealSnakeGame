package player;

import core.Food;
import core.Snake;
import core.Segment;
import enums.Direction;
import java.awt.Color;
import java.awt.Point;

/**
 * AIPlayer extends Player, demonstrating inheritance, encapsulation, polymorphism
 * (inclusion), and abstraction.
 */
public class AIPlayer extends Player {
    // Encapsulation: Private fields
    private final Food food;
    private int difficulty = 1; // Added for coercion example

    public AIPlayer(String name, int sx, int sy, Food food) {
        // Inheritance: Calls superclass constructor
        super(name, sx, sy, Color.MAGENTA);
        this.food = food;
    }

    // Polymorphism (Inclusion): Overrides move() for AI behavior
    @Override
    public void move() {
        Snake snake = getSnake();
        Direction current = snake.getDirection();
        Point head = snake.getBody().peekFirst().getPosition();
        int dx = food.getLocation().x - head.x;
        int dy = food.getLocation().y - head.y;
        Direction primary, secondary;
        if (Math.abs(dx) > Math.abs(dy)) {
            primary = dx < 0 ? Direction.LEFT : Direction.RIGHT;
            secondary = dy < 0 ? Direction.UP : Direction.DOWN;
        } else {
            primary = dy < 0 ? Direction.UP : Direction.DOWN;
            secondary = dx < 0 ? Direction.LEFT : Direction.RIGHT;
        }
        Direction chosen = chooseSafeDirection(snake, current, primary, secondary);
        snake.setDirection(chosen);
        snake.move();
    }

    // Abstraction: Hides direction selection logic
    private Direction chooseSafeDirection(Snake snake, Direction current,
                                         Direction d1, Direction d2) {
        Direction[] candidates = {
            d1, d2, current,
            (d1 == Direction.LEFT || d1 == Direction.RIGHT)
                ? (dyPreferred(current) ? Direction.UP : Direction.DOWN)
                : (dxPreferred(current) ? Direction.LEFT : Direction.RIGHT)
        };
        for (Direction d : candidates) {
            if (d != current.opposite() && !wouldSelfCollide(snake, d)) {
                return d;
            }
        }
        return current;
    }

    // Encapsulation: Hides collision check logic
    private boolean wouldSelfCollide(Snake snake, Direction dir) {
        Point head = snake.getBody().peekFirst().getPosition();
        Point next = switch (dir) {
            case UP    -> new Point(head.x, head.y - 1);
            case DOWN  -> new Point(head.x, head.y + 1);
            case LEFT  -> new Point(head.x - 1, head.y);
            case RIGHT -> new Point(head.x + 1, head.y);
        };
        for (Segment seg : snake.getBody()) {
            if (seg.getPosition().equals(next)) {
                return true;
            }
        }
        return false;
    }

    private boolean dxPreferred(Direction dir) {
        return dir == Direction.LEFT || dir == Direction.RIGHT;
    }

    private boolean dyPreferred(Direction dir) {
        return dir == Direction.UP || dir == Direction.DOWN;
    }

    // Encapsulation: Added for coercion polymorphism example
    public void setDifficulty(int difficulty) {
        this.difficulty = Math.max(1, Math.min(10, difficulty));
        // Hypothetical: Adjust AI behavior based on difficulty
    }
}
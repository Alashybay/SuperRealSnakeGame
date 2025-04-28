package player;

import core.Food;
import core.Snake;
import core.Segment;
import enums.Direction;

import java.awt.Color;
import java.awt.Point;

public class AIPlayer extends Player {
    private final Food food;

    public AIPlayer(String name, int sx, int sy, Food food) {
        super(name, sx, sy, Color.MAGENTA);
        this.food = food;
    }

    @Override
    public void move() {
        Snake snake = getSnake();
        Direction current = snake.getDirection();

        // compute vector to food
        Point head = snake.getBody().peekFirst().getPosition();
        int dx = food.getLocation().x - head.x;
        int dy = food.getLocation().y - head.y;

        // primary & secondary targeting
        Direction primary, secondary;
        if (Math.abs(dx) > Math.abs(dy)) {
            primary   = dx < 0 ? Direction.LEFT  : Direction.RIGHT;
            secondary = dy < 0 ? Direction.UP    : Direction.DOWN;
        } else {
            primary   = dy < 0 ? Direction.UP    : Direction.DOWN;
            secondary = dx < 0 ? Direction.LEFT  : Direction.RIGHT;
        }

        // pick a valid direction that won't immediately collide with its own body
        Direction chosen = chooseSafeDirection(snake, current, primary, secondary);
        snake.setDirection(chosen);
        snake.move();
    }

    /**
     * Attempts directions in order, skipping any that would step on its own body.
     */
    private Direction chooseSafeDirection(Snake snake, Direction current,
                                          Direction d1, Direction d2) {
        // list of candidates: primary, secondary, continue straight, then the other axis
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
        // no safe move found, keep going straight
        return current;
    }

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
}

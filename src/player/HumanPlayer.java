package player;

import core.Movable;
import enums.ControlType;
import enums.Direction;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.Optional;

/**
 * Concrete subclass demonstrating:
 * - Inheritance from Player
 * - Method overriding for behavior
 * - Association: uses ControlType and Direction enums
 */
public class HumanPlayer extends Player {
    private Direction nextDir;
    private final ControlType controls;

    public HumanPlayer(String name, int sx, int sy, ControlType controls, Color snakeColor) {
        super(name, sx, sy, snakeColor);
        this.controls = controls;
    }

    @Override
    public void move() {
        if (nextDir != null) getSnake().setDirection(nextDir);
        getSnake().move();
    }

    public Optional<KeyAdapter> getKeyAdapter() {
        return Optional.of(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (controls) {
                    case ARROWS -> {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP:    nextDir = Direction.UP;    break;
                            case KeyEvent.VK_DOWN:  nextDir = Direction.DOWN;  break;
                            case KeyEvent.VK_LEFT:  nextDir = Direction.LEFT;  break;
                            case KeyEvent.VK_RIGHT: nextDir = Direction.RIGHT; break;
                        }
                    }
                    case WASD -> {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_W:     nextDir = Direction.UP;    break;
                            case KeyEvent.VK_S:     nextDir = Direction.DOWN;  break;
                            case KeyEvent.VK_A:     nextDir = Direction.LEFT;  break;
                            case KeyEvent.VK_D:     nextDir = Direction.RIGHT; break;
                        }
                    }
                }
            }
        });
    }
}
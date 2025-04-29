package player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.Optional;

import enums.ControlType;
import enums.Direction;

public class HumanPlayer extends Player {
    private Direction nextDir;
    private final ControlType controls;

    /**
     * @param name     player name
     * @param sx       snake start X cell
     * @param sy       snake start Y cell
     * @param controls which keyset to use (ARROWS or WASD)
     * @param snakeColor the color to draw this player's snake
     */
    public HumanPlayer(String name, int sx, int sy, ControlType controls, Color snakeColor) {
        super(name, sx, sy, snakeColor);
        this.controls = controls;
    }

    @Override
    public void move() {
        if (nextDir != null) {
            getSnake().setDirection(nextDir);
        }
        getSnake().move();
    }

    /**
     * Returns a KeyAdapter that listens either to the arrow keys or WASD, depending on 'controls'.
     */
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

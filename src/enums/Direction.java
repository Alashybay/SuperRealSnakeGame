package enums;

/**
 * The Direction enum demonstrates the use of the following OOP concepts:
 * 
 * 1. **Encapsulation**: The enum encapsulates the possible directions (UP, DOWN, LEFT, RIGHT)
 *    and provides a method `opposite()` to determine the opposite direction. This hides the
 *    implementation details and provides a clean interface for users of the enum.
 * 
 * 2. **Abstraction**: The enum abstracts the concept of directions into a simple and reusable
 *    data type. Users of this enum don't need to worry about how the opposite direction is
 *    calculated; they just call the `opposite()` method.
 * 
 * 3. **Polymorphism**: The `switch` statement in the `opposite()` method demonstrates polymorphism
 *    by behaving differently based on the value of `this` (the current enum instance).
 * 
 * 4. **Immutability**: Enums in Java are inherently immutable. The Direction enum instances
 *    (UP, DOWN, LEFT, RIGHT) cannot be modified after they are created, ensuring thread safety
 *    and consistency.
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * Returns the opposite direction of the current direction.
     * 
     * @return the opposite Direction
     * @throws IllegalStateException if an unexpected value is encountered
     */
    public Direction opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}

// public enum Direction {
//     UP {
//         @Override public Direction opposite() { return DOWN; }
//     },
//     DOWN {
//         @Override public Direction opposite() { return UP; }
//     },
//     // … and so on for LEFT, RIGHT
    
//     public abstract Direction opposite();
// }
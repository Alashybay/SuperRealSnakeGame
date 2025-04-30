package core;

import java.awt.Point;

/**
 * The Segment class represents a segment of a snake in the game.
 * This class demonstrates several Object-Oriented Programming (OOP) concepts:
 * 
 * 1. **Encapsulation**: 
 *    - The `position` field is private, ensuring that it cannot be accessed directly from outside the class.
 *    - Access to the `position` field is provided through the public `getPosition()` method, controlling how the data is exposed.
 * 
 * 2. **Abstraction**:
 *    - The class hides the implementation details of how the position is stored (using a `Point` object) and provides a simple interface (constructor and getter) for interacting with it.
 * 
 * 3. **Constructor**:
 *    - The `Segment` constructor initializes the `position` field, ensuring that every `Segment` object is created with a valid position.
 * 
 * 4. **Composition**:
 *    - The `Segment` class uses composition by including a `Point` object as a field. This demonstrates a "has-a" relationship (a Segment has a position).
 */
public class Segment {
    private final Point position; // Encapsulation: private field

    /**
     * Constructor to initialize the position of the segment.
     * @param x The x-coordinate of the segment's position.
     * @param y The y-coordinate of the segment's position.
     */
    public Segment(int x, int y) {
        this.position = new Point(x, y); // Composition: using Point object
    }

    /**
     * Getter method to retrieve the position of the segment.
     * @return The position of the segment as a Point object.
     */
    public Point getPosition() {
        return position; // Encapsulation: controlled access to the field
    }
}

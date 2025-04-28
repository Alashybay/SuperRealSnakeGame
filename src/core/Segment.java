package core;

import java.awt.Point;

public class Segment {
    private final Point position;

    public Segment(int x, int y) {
        this.position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }
}

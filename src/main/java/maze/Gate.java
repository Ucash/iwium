package maze;

import java.awt.*;

public class Gate {
    private final Point point;
    private final boolean vertical;

    public Gate(Point point, boolean vertical) {
        this.point = point;
        this.vertical = vertical;
    }

    public Point getPoint() {
        return point;
    }

    public boolean isVertical() {
        return vertical;
    }
}

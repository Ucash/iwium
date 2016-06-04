package learning.base;

import java.awt.*;

public class ActionResult {
    private final Point location;
    private final double reward;

    public ActionResult(Point location, double reward) {
        this.location = location;
        this.reward = reward;
    }

    public Point getLocation() {
        return location;
    }

    public double getReward() {
        return reward;
    }
}

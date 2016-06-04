package learning;

import learning.base.ActionResult;
import learning.base.IEnvironment;

import java.awt.*;
import java.util.List;

public class Agent {

    private final IEnvironment environment;
    protected final QLearning qLearning;

    protected Point location;

    public Agent(IEnvironment environment, QLearning qLearning, Point location) {
        this.environment = environment;
        this.qLearning = qLearning;
        this.location = location;
    }

    public Point getLocation(){
        return location;
    }

    public void update(List<Action> possibleActions) {
        Action action = qLearning.pickAction(location, possibleActions);
        ActionResult result = environment.performAction(this, action);
        qLearning.learn(location, action, result.getLocation(), result.getReward());
        this.location = result.getLocation();
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}

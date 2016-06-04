package learning.test;

import learning.Action;
import learning.Agent;
import learning.QLearning;
import visualization.ITracker;

import java.awt.*;

public class TestAgent extends Agent{

    private final ITracker tracker;


    public TestAgent(Point location, ITracker tracker, QLearning qLearning) {
        super(null, qLearning, location);
        this.tracker = tracker;
    }

    public void test(Point destination) throws InterruptedException {
        while(true) {
            if (location.x == destination.x && location.y == destination.y) {
                return;
            }
            Action action = qLearning.pickBestAction(location);
            tracker.onNewAgentLocation(this, location, action);
            location = action.perform(location);
            Thread.sleep(200);
        }
    }

    public boolean hasLearned(int pathLen, Point destination){
        int i = 0;
        while(true) {
            if (location.x == destination.x && location.y == destination.y) {
                return pathLen == i;
            }
            Action action = qLearning.pickBestAction(location);
            tracker.onNewAgentLocation(this, location, action);
            location = action.perform(location);
            i ++;
            if (i > pathLen){
                return false;
            }
        }
    }
}

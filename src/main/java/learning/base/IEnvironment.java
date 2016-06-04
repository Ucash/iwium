package learning.base;

import learning.Agent;
import learning.Action;
import visualization.ITracker;

//TODO: Extract common logic from both environments, make this abstract class
public interface IEnvironment {

    ActionResult performAction(Agent agent, Action action);

    void learn(int episodeCount) throws InterruptedException;

    void test() throws InterruptedException;

    void setTracker(ITracker tracker);

    boolean hasLearn();
}

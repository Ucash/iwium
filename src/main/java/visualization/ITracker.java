package visualization;

import learning.Action;
import learning.Agent;

import java.awt.*;

public interface ITracker {
    void onNewAgentLocation(Agent agent, Point agentLocation, Action action);
    void cleanData();
    int getDelay();
}

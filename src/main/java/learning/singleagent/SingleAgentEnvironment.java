package learning.singleagent;

import learning.*;
import learning.base.ActionResult;
import learning.base.IEnvironment;
import learning.test.TestAgent;
import maze.Gate;
import maze.MazeMap;
import maze.Room;
import visualization.ITracker;

import java.awt.*;

public class SingleAgentEnvironment implements IEnvironment {
    private static final double REWARD = 10.0;

    private ITracker tracker;

    private final MazeMap mazeMap;
    private final QLearning qLearning;
    private final Agent agent;

    public SingleAgentEnvironment(MazeMap mazeMap, ITracker tracker) {
        this.tracker = tracker;
        this.mazeMap = mazeMap;
        this.qLearning = new QLearning(mazeMap.getSize());
        this.agent = new Agent(this, qLearning, mazeMap.getRooms().get(0).getEnters().get(0).getPoint());
    }

    public ActionResult performAction(Agent ignore, Action action) {
        tracker.onNewAgentLocation(agent, agent.getLocation(), action);
        Point newLocation = action.perform(agent.getLocation());
        double reward = calculateReward(newLocation);
        return new ActionResult(newLocation, reward);
    }

    private double calculateReward(Point newLocation) {
        Point destination = mazeMap.findDestination();
        if (destination.x == newLocation.x && destination.y == newLocation.y) {
            return 10.0 * REWARD * mazeMap.getRooms().size();
        }
        Room room = mazeMap.findRoomWithPointInsideWithEnter(newLocation);
        if (actionCorrect(newLocation, room)) {
            return room.getRewardMultiplier() * REWARD;
        }
        return 0.0;
    }

    private boolean actionCorrect(Point newLocation, Room room) {
        int currentDistance = calculateDistance(agent.getLocation(), room);
        int newDistance = calculateDistance(newLocation, room);
        return newDistance < currentDistance;
    }

    private int calculateDistance(Point location, Room room) {
        Gate gate = room.findExitInShortestPath(mazeMap.getShortestPath());
        if (gate == null) {
            return calculateDistance(location, room.getExits().get(0).getPoint());
        }
        return calculateDistance(location, gate.getPoint());
    }

    private int calculateDistance(Point location, Point destination) {
        return (destination.x - location.x) + (destination.y - location.y);
    }

    public void learn(int episodeCount) throws InterruptedException {
        for (int i = 0; i < episodeCount; i++) {
            updateAgent();
            if (tracker.getDelay() > 0) {
                Thread.sleep(tracker.getDelay());
            }
        }
        qLearning.printKnowledge();
        tracker.cleanData();
    }

    public void test() throws InterruptedException {
        while (true) {
            TestAgent testAgent = new TestAgent(mazeMap.getStart(), tracker, qLearning);
            testAgent.test(mazeMap.findDestination());
            tracker.cleanData();
        }
    }

    private void updateAgent() {
        Point location = agent.getLocation();
        if (achieveExit(location)) {
            location = pickEnter();
            agent.setLocation(location);
        }
        Room room = mazeMap.findRoomWithPointInsideWithEnter(location);
        agent.update(Action.getPossibleSingleAgentActions(location, room));
    }

    private Point pickEnter() {
        return mazeMap.getRooms().get(0).getEnters().get(0).getPoint();
    }

    private boolean achieveExit(Point location) {
        Gate exit = mazeMap.getRooms().get(mazeMap.getRooms().size() - 1).getExits().get(0);
        if (location.x == exit.getPoint().x && location.y == exit.getPoint().y) {
            return true;
        }
        return false;
    }

    public void setTracker(ITracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public boolean hasLearn() {
        TestAgent testAgent = new TestAgent(mazeMap.getStart(), new ITracker() {
            @Override
            public void onNewAgentLocation(Agent agent, Point agentLocation, Action action) {
            }
            @Override
            public void cleanData() {
            }
            @Override
            public int getDelay() {
                return 0;
            }
        }, qLearning);
        return testAgent.hasLearned(mazeMap.getPathLen(), mazeMap.findDestination());
    }

}

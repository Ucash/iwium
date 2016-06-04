package learning.multiagent;

import learning.*;
import learning.base.ActionResult;
import learning.base.IEnvironment;
import learning.test.TestAgent;
import maze.Gate;
import maze.MazeMap;
import maze.Room;
import visualization.ITracker;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MultiAgentEnvironment implements IEnvironment {

    private static final double REWARD = 10.0;

    private ITracker tracker;

    private final MazeMap mazeMap;
    private final QLearning qLearning;
    private final Map<Agent, Room> agents;

    public MultiAgentEnvironment(MazeMap mazeMap, ITracker tracker) {
        this.tracker = tracker;
        this.mazeMap = mazeMap;
        this.qLearning = new QLearning(mazeMap.getSize());
        this.agents = new HashMap<>();
        for (Room room : mazeMap.getRooms()) {
            this.agents.put(new Agent(this, qLearning, room.getEnters().get(0).getPoint()), room);
        }
    }

    public ActionResult performAction(Agent agent, Action action) {
        tracker.onNewAgentLocation(agent, agent.getLocation(), action);
        Point newLocation = action.perform(agent.getLocation());
        double reward = calculateReward(agent, newLocation);
        return new ActionResult(newLocation, reward);
    }

    private double calculateReward(Agent agent, Point newLocation) {
        Point destination = mazeMap.findDestination();
        if (destination.x == newLocation.x && destination.y == newLocation.y){
            return 10.0 * REWARD * mazeMap.getRooms().size();
        }
        if (actionCorrect(agent, newLocation)) {
            return agents.get(agent).getRewardMultiplier() * REWARD;
        }
        return 0.0;
    }

    private boolean actionCorrect(Agent agent, Point newLocation) {
        int currentDistance = calculateDistance(agent.getLocation(), agents.get(agent));
        int newDistance = calculateDistance(newLocation, agents.get(agent));
        return newDistance < currentDistance;
    }

    private int calculateDistance(Point location, Room room) {
        Gate gate = room.findExitInShortestPath(mazeMap.getShortestPath());
        if (gate == null){
            return calculateDistance(location, room.getExits().get(0).getPoint());
        }
        return calculateDistance(location, gate.getPoint());
    }

    private int calculateDistance(Point location, Point destination) {
        return (destination.x - location.x) + (destination.y - location.y);
    }

    public void learn(int episodeCount) throws InterruptedException {
        for (int i = 0; i < episodeCount; i++) {
            agents.keySet().forEach(this::updateAgent);
            if (tracker.getDelay() > 0) {
                Thread.sleep(tracker.getDelay());
            }
        }
        qLearning.printKnowledge();
        tracker.cleanData();
    }

    public void test() throws InterruptedException {
        while(true) {
            TestAgent testAgent = new TestAgent(mazeMap.getStart(), tracker, qLearning);
            testAgent.test(mazeMap.findDestination());
            tracker.cleanData();
        }
    }

    private void updateAgent(Agent agent) {
        Point location = agent.getLocation();
        if (achieveExit(agents.get(agent), location)) {
            location = pickEnter(agent);
            agent.setLocation(location);
        }
        agent.update(Action.getPossibleMultiAgentActions(location, agents.get(agent)));
    }

    private Point pickEnter(Agent agent) {
        Room room = agents.get(agent);
        if (room.getEnters().size() == 1){
            return room.getEnters().get(0).getPoint();
        }
        int index = new Random().nextInt(room.getEnters().size());
        return room.getEnters().get(index).getPoint();
    }

    private boolean achieveExit(Room room, Point location) {
        for (Gate exit : room.getExits()){
            if (location.x == exit.getPoint().x && location.y == exit.getPoint().y){
                return true;
            }
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

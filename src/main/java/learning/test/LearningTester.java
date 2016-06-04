package learning.test;

import learning.Action;
import learning.Agent;
import learning.Configuration;
import learning.base.IEnvironment;
import learning.multiagent.MultiAgentEnvironment;
import learning.singleagent.SingleAgentEnvironment;
import maze.MazeMap;
import visualization.ITracker;

import java.awt.*;

public class LearningTester {

    private static final int LEARN_STEP = 1000;
    private static final int LEARN_STEP_LIMIT = 2000000;

    public static void testConfiguration(MazeMap mazeMap) throws InterruptedException {
        int single = learnBySingleAgent(mazeMap);
        int multiple = learnByMultipleAgents(mazeMap);
        printResult(single, multiple, mazeMap.getRooms().size());
    }

    private static int learnBySingleAgent(MazeMap mazeMap) throws InterruptedException {
        return learn(new SingleAgentEnvironment(mazeMap, buildTracker()));
    }

    private static int learnByMultipleAgents(MazeMap mazeMap) throws InterruptedException {
        return learn(new MultiAgentEnvironment(mazeMap, buildTracker()));
    }

    private static int learn(IEnvironment environment) throws InterruptedException {
        int stepCount = LEARN_STEP;
        environment.learn(LEARN_STEP);
        while (!environment.hasLearn()){
            stepCount += LEARN_STEP;
            environment.learn(LEARN_STEP);
        }

        return stepCount;
    }

    private static void printResult(int single, int multiple, int roomSize) {
        System.out.println(String.format("%.2f;%.2f;%.2f;%s;%.5f;%d;%d;%d",
                Configuration.ALPHA,
                Configuration.GAMMA,
                Configuration.EPSILON,
                Configuration.CHANGE_EPSILON ? "t" : "f",
                Configuration.EPSILON_CHANGE_FACTOR,
                single,
                multiple,
                multiple * roomSize));
    }

    private static ITracker buildTracker() {
        return new ITracker() {
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
        };
    }
}

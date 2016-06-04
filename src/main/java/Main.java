import learning.Action;
import learning.Agent;
import learning.Configuration;
import learning.base.IEnvironment;
import learning.multiagent.MultiAgentEnvironment;
import learning.singleagent.SingleAgentEnvironment;
import learning.test.LearningTester;
import maze.MazeBuilder;
import maze.MazeMap;
import visualization.ITracker;
import visualization.MazeVisualizer;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 30; i ++) {
//            LearningTester.testConfiguration(MazeBuilder.complex());
//        }
//        LearningTester.testConfiguration(MazeBuilder.complex());
//        visualizeSimple();
//        visualizeComplex();
//        hiddenSimple();
        hiddenComplex();
    }

    private static void visualizeSimple() throws InterruptedException {
        visualizeLearning(buildSimple());
    }

    private static void visualizeComplex() throws InterruptedException {
        visualizeLearning(buildComplex());
    }

    private static void hiddenSimple() throws InterruptedException {
        hiddenLearning(buildSimple());
    }

    private static void hiddenComplex() throws InterruptedException {
        hiddenLearning(buildComplex());
    }

    private static void visualizeLearning(MazeMap mazeMap) throws InterruptedException {
        MazeVisualizer mazeVisualizer = MazeVisualizer.getInstance();
        mazeVisualizer.open(mazeMap);

        IEnvironment environment = new MultiAgentEnvironment(mazeMap, mazeVisualizer);
        environment.learn(Configuration.EPISODE_COUNT);
        environment.test();
    }

    private static void hiddenLearning(MazeMap mazeMap) throws InterruptedException {
        IEnvironment environment = new MultiAgentEnvironment(mazeMap, new ITracker() {
            @Override
            public void onNewAgentLocation(Agent agent, Point agentLocation, Action action) {
                // do nothing
            }
            @Override
            public void cleanData() {
                // do nothing
            }
            @Override
            public int getDelay() {
                return 0;
            }
        });
        environment.learn(Configuration.EPISODE_COUNT);

        MazeVisualizer mazeVisualizer = MazeVisualizer.getInstance();
        mazeVisualizer.open(mazeMap);
        environment.setTracker(mazeVisualizer);
        System.out.println(environment.hasLearn());
        environment.test();
    }

    private static MazeMap buildSimple(){
        return MazeBuilder.simple();
    }

    private static MazeMap buildComplex(){
        return MazeBuilder.complex();
    }

}


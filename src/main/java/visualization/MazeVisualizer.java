package visualization;

import learning.Action;
import learning.Agent;
import maze.IMazeMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MazeVisualizer extends JFrame implements ITracker{

    private static final Dimension FRAME_SIZE = new Dimension(800, 660);
    private static final int SPLIT_DIVIDER_LOCATION = 600;

    private Map<Agent, State> agentStates;

    private static final MazeVisualizer instance = new MazeVisualizer();

    private MazePanel mazePanel;

    private MazeVisualizer() {
        super("IWIUM");
        this.agentStates = new HashMap<>();
    }

    public static MazeVisualizer getInstance() {
        return instance;
    }

    public MazePanel getMazePanel(){
        return mazePanel;
    }

    public void open(IMazeMap mazeMap) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mazePanel = new MazePanel(mazeMap);
        setContentPane(createSplitPanel());
        setSize(FRAME_SIZE);
        setVisible(true);
        setResizable(false);
    }

    private JSplitPane createSplitPanel() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mazePanel, new Panel());
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(SPLIT_DIVIDER_LOCATION);
        splitPane.setEnabled(false);
        return splitPane;
    }

    @Override
    public void onNewAgentLocation(Agent agent, Point agentLocation, Action action) {
        agentStates.put(agent, new State(agentLocation,action));
        mazePanel.setAgentStates(agentStates.values());
    }

    @Override
    public void cleanData() {
        agentStates.clear();
        mazePanel.setAgentStates(agentStates.values());
    }

    @Override
    public int getDelay() {
        return 75;
    }

    public class State{
        private final Point point;
        private final Action action;

        public State(Point point, Action action) {
            this.point = point;
            this.action = action;
        }

        public Point getPoint() {
            return point;
        }

        public Action getAction() {
            return action;
        }
    }
}

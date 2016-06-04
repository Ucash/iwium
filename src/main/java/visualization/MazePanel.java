package visualization;

import maze.Gate;
import maze.IMazeMap;
import maze.Room;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class MazePanel extends JPanel {

    private final static double MAZE_SIZE = 600.0;

    private final IMazeMap maze;
    private final int tileSize;

    private Collection<MazeVisualizer.State> agentStates;

    public MazePanel(IMazeMap maze) {
        this.maze = maze;
        this.tileSize = (int) Math.floor(MAZE_SIZE / Math.max(maze.getSize().getX(), maze.getSize().getY()));
    }

    public void setAgentStates(Collection<MazeVisualizer.State> agentLocations) {
        this.agentStates = agentLocations;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawMaze(g2);
        drawAgent(g2);
    }


    private void drawMaze(Graphics2D g2) {
        for (Room room : maze.getRooms()) {
            drawRoomWalls(g2, room);
        }
        for (Room room : maze.getRooms()) {
            drawRoomGates(g2, room);
        }
    }

    private void drawAgent(Graphics2D g2) {
        if (agentStates == null) {
            return;
        }
        for (MazeVisualizer.State state : agentStates) {
            g2.setColor(Color.BLUE);
            int offset = tileSize / 10;
            int x = state.getPoint().x * tileSize + offset;
            int y = state.getPoint().y * tileSize + offset;
            g2.drawOval(x, y, tileSize - 2 * offset, tileSize - 2 * offset);
            g2.fillOval(x, y, tileSize - 2 * offset, tileSize - 2 * offset);

            g2.setColor(Color.RED);
            offset = tileSize / 2;
            int x1 = state.getPoint().x * tileSize + offset;
            int y1 = state.getPoint().y * tileSize + offset;
            Point to = state.getAction().perform(state.getPoint());
            int x2 = to.x * tileSize + offset;
            int y2 = to.y * tileSize + offset;
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawRoomWalls(Graphics2D g2, Room room) {
        drawWall(g2, room.getFrom(), new Point(room.getFrom().x, room.getTo().y));
        drawWall(g2, room.getFrom(), new Point(room.getTo().x, room.getFrom().y));
        drawWall(g2, room.getTo(), new Point(room.getFrom().x, room.getTo().y));
        drawWall(g2, room.getTo(), new Point(room.getTo().x, room.getFrom().y));

    }

    private void drawRoomGates(Graphics2D g2, Room room) {
        for (Gate enter : room.getEnters()) {
            drawGate(g2, enter);
        }
        for (Gate exit : room.getExits()) {
            drawGate(g2, exit);
        }
    }

    private void drawWall(Graphics2D g2, Point from, Point to) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(normalize(from.x), normalize(from.y), normalize(to.x), normalize(to.y));
    }

    private void drawGate(Graphics2D g2, Gate gate) {
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(3));
        int x = gate.getPoint().x * tileSize;
        int y = gate.getPoint().y * tileSize;
        int offset = tileSize / 2;
        if (gate.isVertical()) {
            g2.drawLine(x + offset, y, x + offset, y + tileSize);
        } else {
            g2.drawLine(x, y + offset, x + tileSize, y + offset);
        }
    }

    private int normalize(int x) {
        return (x * tileSize) + (tileSize / 2);
    }
}

package maze;

import java.awt.*;
import java.util.List;

public class Room {

    private final double rewardMultiplier;
    private final Point from;
    private final Point to;
    private final List<Gate> enters;
    private final List<Gate> exits;
    private final boolean isFinal;

    public Room(double rewardMultiplier, Point from, Point to, List<Gate> enters, List<Gate> exits, boolean isFinal) {
        this.rewardMultiplier = rewardMultiplier;
        this.from = from;
        this.to = to;
        this.enters = enters;
        this.exits = exits;
        this.isFinal = isFinal;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    public List<Gate> getEnters() {
        return enters;
    }

    public List<Gate> getExits() {
        return exits;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public Gate entryOrExit(int x, int y) {
        for (Gate gate : enters) {
            if (gate.getPoint().x == x && gate.getPoint().y == y) {
                return gate;
            }
        }
        for (Gate gate : exits) {
            if (gate.getPoint().x == x && gate.getPoint().y == y) {
                return gate;
            }
        }
        return null;
    }

    public boolean isPointInside(int x, int y) {
        return x < to.x && x > from.x && y < to.y && y > from.y;
    }

    public Gate findExitInShortestPath(List<Point> shortestPath){
        for (Point point : shortestPath){
            for(Gate exit : exits){
                if (point.x == exit.getPoint().x && point.y == exit.getPoint().y){
                    return exit;
                }
            }
        }
        return null;
    }

    public boolean isExit(int x, int y) {
        for (Gate gate : exits) {
            if (gate.getPoint().x == x && gate.getPoint().y == y) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnter(int x, int y) {
        for (Gate gate : enters) {
            if (gate.getPoint().x == x && gate.getPoint().y == y) {
                return true;
            }
        }
        return false;
    }

    public Gate findGate(int x, int y) {
        for (Gate gate : exits) {
            if (gate.getPoint().x == x && gate.getPoint().y == y) {
                return gate;
            }
        }
        for (Gate gate : enters) {
            if (gate.getPoint().x == x && gate.getPoint().y == y) {
                return gate;
            }
        }
throw new RuntimeException();
    }
}

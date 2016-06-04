package learning;

import maze.Gate;
import maze.Room;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public enum Action {
    UP {
        @Override
        public boolean isPossibleMultiAgent(int x, int y, Room room) {
            return isPossibleMultiAgent(x, y, x, y - 1, room);
        }

        @Override
        public boolean isPossibleSingleAgent(int x, int y, Room room) {
            return isPossibleSingleAgent(x, y, x, y - 1, room);
        }

            @Override
        public Point perform(Point point) {
            return new Point(point.x, point.y - 1);
        }
    },
    DOWN {
        @Override
        public boolean isPossibleMultiAgent(int x, int y, Room room) {
            return isPossibleMultiAgent(x, y, x, y + 1, room);
        }

        @Override
        public boolean isPossibleSingleAgent(int x, int y, Room room) {
            return isPossibleSingleAgent(x, y, x, y + 1, room);
        }

        @Override
        public Point perform(Point point) {
            return new Point(point.x, point.y + 1);
        }
    },
    LEFT {
        @Override
        public boolean isPossibleMultiAgent(int x, int y, Room room) {
            if (x == 0) {
                return false;
            }
            return isPossibleMultiAgent(x, y, x - 1, y, room);
        }

        @Override
        public boolean isPossibleSingleAgent(int x, int y, Room room) {
            return isPossibleSingleAgent(x, y, x - 1, y, room);
        }

        @Override
        public Point perform(Point point) {
            return new Point(point.x - 1, point.y);
        }
    },
    RIGHT {
        @Override
        public boolean isPossibleMultiAgent(int x, int y, Room room) {
            return isPossibleMultiAgent(x, y, x + 1, y, room);
        }

        @Override
        public boolean isPossibleSingleAgent(int x, int y, Room room) {
            return isPossibleSingleAgent(x, y, x + 1, y, room);
        }

        @Override
        public Point perform(Point point) {
            return new Point(point.x + 1, point.y);
        }
    };

    public abstract boolean isPossibleMultiAgent(int x, int y, Room room);

    public abstract boolean isPossibleSingleAgent(int x, int y, Room room);

    public abstract Point perform(Point point);

    protected boolean isPossibleMultiAgent(int x, int y, int newX, int newY, Room room){
        boolean insideOrExit = room.isPointInside(newX, newY) || room.isExit(newX, newY);
        if (room.isEnter(x,y)) {
            return insideOrExit;
        }
        return insideOrExit || room.isEnter(newX, newY);
    }

    protected boolean isPossibleSingleAgent(int x, int y, int newX, int newY, Room room){
        if (newX < 0 || newY < 0){
            return false;
        }
        if (room.isPointInside(newX, newY) || room.isExit(newX, newY) || room.isEnter(newX, newY)){
            return true;
        }
        if (room.isEnter(x,y) || room.isExit(x,y)) {
            Gate gate = room.findGate(x,y);
            return gate.isVertical() ? y == newY : x == newX;
        }
        return false;
    }

    public static List<Action> getPossibleMultiAgentActions(Point point, Room room) {
        List<Action> actions = new ArrayList<>();
        for (Action action : Action.values()) {
            if (action.isPossibleMultiAgent(point.x, point.y, room)) {
                actions.add(action);
            }
        }
        return actions;
    }

    public static List<Action> getPossibleSingleAgentActions(Point point, Room room) {
        List<Action> actions = new ArrayList<>();
        for (Action action : Action.values()) {
            if (action.isPossibleSingleAgent(point.x, point.y, room)) {
                actions.add(action);
            }
        }
        return actions;
    }
}

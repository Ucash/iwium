package maze;

import java.awt.*;
import java.util.List;

public class MazeMap implements IMazeMap {

    private final int pathLen;
    private final Point size;
    private final List<Room> rooms;
    private final List<Point> shortestPath;

    protected MazeMap(int pathLen, Point size, List<Room> rooms, List<Point> shortestPath) {
        this.pathLen = pathLen;
        this.size = size;
        this.shortestPath = shortestPath;
        this.rooms = rooms;
    }

    @Override
    public Point getSize() {
        return size;
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    public List<Point> getShortestPath() {
        return shortestPath;
    }

    public Room findRoomWithPointInside(Point point) {
        for (Room room : rooms) {
            if (room.isPointInside(point.x, point.y)) {
                return room;
            }
        }
        throw new RuntimeException("Room not found - should not happened");
    }

    public Point findDestination() {
        for (Room room : rooms) {
            if (room.isFinal()) {
                return room.getExits().get(0).getPoint();
            }
        }
        throw new RuntimeException("Destination not found - should not happened");

    }

    public Room findRoomWithPointInsideWithEnter(Point point) {
        for (Room room : rooms) {
            if (room.isPointInside(point.x, point.y)) {
                return room;
            }
            for (Gate gate : room.getEnters()){
                if (point.x == gate.getPoint().x && point.y == gate.getPoint().y){
                    return room;
                }
            }
        }
        throw new RuntimeException("Room not found - should not happened");

    }

    public Point getStart() {
        return rooms.get(0).getEnters().get(0).getPoint();
    }

    public int getPathLen() {
        return pathLen;
    }
}

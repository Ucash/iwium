package maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeBuilder {

    public static MazeMap complex() {
        List<Room> rooms = new ArrayList<>();
        Gate g0 = new Gate(new Point(0, 1), true);
        Gate g1 = new Gate(new Point(5, 2), true);
        Gate g2 = new Gate(new Point(1, 4), false);
        Gate g3 = new Gate(new Point(10, 4), false);
        Gate g4 = new Gate(new Point(4, 7), false);
        Gate g5 = new Gate(new Point(8, 9), true);
        Gate g6 = new Gate(new Point(2, 11), false);
        Gate g7 = new Gate(new Point(4, 14), true);
        Gate g8 = new Gate(new Point(8, 12), true);
        Gate g9 = new Gate(new Point(11, 11), false);
        Gate g10 = new Gate(new Point(12, 14), true);

        List<Gate> room1Enter = new ArrayList<>();
        room1Enter.add(g0);

        List<Gate> room1Exit = new ArrayList<>();
        room1Exit.add(g1);
        room1Exit.add(g2);

        List<Gate> room2Enter = new ArrayList<>();
        room2Enter.add(g1);

        List<Gate> room2Exit = new ArrayList<>();
        room2Exit.add(g3);

        List<Gate> room3Enter = new ArrayList<>();
        room3Enter.add(g2);
        room3Enter.add(g3);

        List<Gate> room3Exit = new ArrayList<>();
        room3Exit.add(g4);

        List<Gate> room4Enter = new ArrayList<>();
        room4Enter.add(g4);

        List<Gate> room4Exit = new ArrayList<>();
        room4Exit.add(g5);
        room4Exit.add(g6);

        List<Gate> room5Enter = new ArrayList<>();
        room5Enter.add(g5);

        List<Gate> room5Exit = new ArrayList<>();
        room5Exit.add(g9);

        List<Gate> room6Enter = new ArrayList<>();
        room6Enter.add(g6);

        List<Gate> room6Exit = new ArrayList<>();
        room6Exit.add(g7);

        List<Gate> room7Enter = new ArrayList<>();
        room7Enter.add(g7);

        List<Gate> room7Exit = new ArrayList<>();
        room7Exit.add(g8);

        List<Gate> room8Enter = new ArrayList<>();
        room8Enter.add(g9);
        room8Enter.add(g8);

        List<Gate> room8Exit = new ArrayList<>();
        room8Exit.add(g10);

        Room room1 = new Room(1.0, new Point(0, 0), new Point(5, 4), room1Enter, room1Exit, false);
        Room room2 = new Room(0.5, new Point(5, 0), new Point(12, 4), room2Enter, room2Exit, false);
        Room room3 = new Room(2.0, new Point(0, 4), new Point(12, 7), room3Enter, room3Exit, false);
        Room room4 = new Room(3.0, new Point(0, 7), new Point(8, 11), room4Enter, room4Exit, false);
        Room room5 = new Room(5.0, new Point(8, 7), new Point(12, 11), room5Enter, room5Exit, false);
        Room room6 = new Room(4.0, new Point(0, 11), new Point(4, 15), room6Enter, room6Exit, false);
        Room room7 = new Room(5.0, new Point(4, 11), new Point(8, 15), room7Enter, room7Exit, false);
        Room room8 = new Room(6.0, new Point(8, 11), new Point(12, 15), room8Enter, room8Exit, true);

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
        rooms.add(room8);

        List<Point> path = new ArrayList<>();
        path.add(new Point(1, 4));
        path.add(new Point(4, 7));
        path.add(new Point(8, 9));
        path.add(new Point(11, 11));
        path.add(new Point(12, 14));

        return new MazeMap(25, new Point(13, 16), rooms, path);
    }

    public static MazeMap simple() {
        List<Room> rooms = new ArrayList<>();
        Gate g0 = new Gate(new Point(0, 1), true);
        Gate g1 = new Gate(new Point(5, 3), true);
        Gate g2 = new Gate(new Point(10,7), true);

        List<Gate> pass1 = new ArrayList<>();
        pass1.add(g0);

        List<Gate> pass2 = new ArrayList<>();
        pass2.add(g1);

        List<Gate> pass3 = new ArrayList<>();
        pass3.add(g2);

        rooms.add(new Room(1, new Point(0, 0), new Point(5, 8), pass1, pass2, false));
        rooms.add(new Room(2, new Point(5, 0), new Point(10, 8), pass2, pass3, true));

        List<Point> path = new ArrayList<>();
        path.add(new Point(5, 3));
        path.add(new Point(10,7));
        return new MazeMap(16, new Point(11, 9), rooms, path);
    }

}

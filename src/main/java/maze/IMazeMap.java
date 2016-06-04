package maze;

import java.awt.*;
import java.util.List;

public interface IMazeMap {

    Point getSize();
    List<Room> getRooms();
}

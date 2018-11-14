package buildings.interfaces;

import java.util.Iterator;

public interface Building extends Iterable<Floor> {

    int floorsAmount();

    int spacesAmount();

    double totalArea();

    int totalRooms();

    Floor[] getFloors();

    Floor getFloor(int num);

    void setFloor(int num, Floor floor);

    Space getSpace(int num);

    void setSpace(int num, Space space);

    void addSpace(int num, Space space);

    void deleteSpace(int num);

    Space getBestSpace();

    Space[] spacesSortedByAreaDescending();

    Object clone() throws CloneNotSupportedException;

    @Override
    Iterator<Floor> iterator();

}

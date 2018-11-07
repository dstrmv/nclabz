package buildings;

public interface Building {

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

    Space getBestArea();

    Space[] spacesSortedByAreaDescending();

    Object clone() throws CloneNotSupportedException;

}

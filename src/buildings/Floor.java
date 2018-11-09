package buildings;

public interface Floor  {

    int spacesAmount();

    double totalArea();

    int totalRooms();

    Space[] getSpaces();

    Space getSpace(int num);

    void setSpace(int num, Space space);

    void addSpace(int num, Space space);

    void deleteSpace(int num);

    Space getBestSpace();

    Object clone() throws CloneNotSupportedException;

}

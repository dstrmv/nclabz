package buildings;

import java.util.Iterator;

public interface Floor extends Iterable<Space>, Comparable<Floor> {

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

    @Override
    Iterator<Space> iterator();

    @Override
    default int compareTo(Floor o) {
        return Integer.compare(this.spacesAmount(), o.spacesAmount());
    }
}

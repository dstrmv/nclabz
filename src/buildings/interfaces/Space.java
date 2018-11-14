package buildings.interfaces;

public interface Space extends Comparable<Space> {

    int getRoomsAmount();

    void setRoomsAmount(int roomsAmount);

    double getArea();

    void setArea(double area);

    Object clone() throws CloneNotSupportedException;

    @Override
    default int compareTo(Space o) {
        return Double.compare(this.getArea(), o.getArea());
    }
}

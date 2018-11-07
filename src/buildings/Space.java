package buildings;

public interface Space {

    int getRoomsAmount();

    void setRoomsAmount(int roomsAmount);

    double getArea();

    void setArea(double area);

    Object clone() throws CloneNotSupportedException;

}

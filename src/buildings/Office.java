package buildings;

import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.InvalidSpaceAreaException;

import java.io.Serializable;

public class Office implements Space, Serializable, Cloneable {

    private static final int DEFAULT_ROOMS = 1;
    private static final double DEFAULT_AREA = 250.0;

    private double area;
    private int roomsAmount;

    public Office(double area, int roomsAmount) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException();
        }

        if (roomsAmount <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.roomsAmount = roomsAmount;
        this.area = area;
    }

    public Office(double area) {
        this(area, DEFAULT_ROOMS);
    }

    public Office() {
        this(DEFAULT_AREA, DEFAULT_ROOMS);
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public void setArea(double area) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException();
        }
        this.area = area;
    }

    @Override
    public int getRoomsAmount() {
        return roomsAmount;
    }

    @Override
    public void setRoomsAmount(int roomsAmount) {
        if (roomsAmount <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.roomsAmount = roomsAmount;
    }

    @Override
    public String toString() {
        return String.format("Office: area = %f rooms = %d", area, roomsAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Office)) return false;
        Office office = (Office) obj;
        return (office.area == this.area && office.roomsAmount == this.roomsAmount);
    }

    @Override
    public int hashCode() {
        return this.roomsAmount ^ ((int) Double.doubleToLongBits(area)) ^ ((int) (Double.doubleToLongBits(area) >> 32));
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        Office office = (Office) super.clone();
        return office;
    }
}

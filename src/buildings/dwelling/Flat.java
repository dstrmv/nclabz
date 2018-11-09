package buildings.dwelling;

import buildings.Space;
import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.InvalidSpaceAreaException;

import java.io.Serializable;

public class Flat implements Space, Serializable, Cloneable {

    private static final double DEFAULT_AREA = 50;
    private static final int DEFAULT_ROOMS_AMOUNT = 2;

    private double area;
    private int roomsAmount;

    public Flat(double area, int roomsAmount) {

        if (area <= 0) {
            throw new InvalidSpaceAreaException();
        }

        if (roomsAmount <= 0) {
            throw new InvalidRoomsCountException();
        }

        this.area = area;
        this.roomsAmount = roomsAmount;
    }

    public Flat(double area) {
        this(area, DEFAULT_ROOMS_AMOUNT);
    }

    public Flat() {
        this(DEFAULT_AREA, DEFAULT_ROOMS_AMOUNT);
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public int getRoomsAmount() {
        return roomsAmount;
    }

    @Override
    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    @Override
    public String toString() {
        return String.format("Flat: area = %f rooms = %d", area, roomsAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Flat)) return false;
        Flat flat = (Flat) obj;
        return (flat.area == this.area && flat.roomsAmount == this.roomsAmount);
    }

    @Override
    public int hashCode() {
        return this.roomsAmount ^ ((int) Double.doubleToLongBits(area)) ^ ((int) (Double.doubleToLongBits(area) >> 32));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Flat flat = (Flat) super.clone();
        return flat;
    }
}

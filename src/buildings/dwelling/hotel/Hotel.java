package buildings.dwelling.hotel;

import buildings.Floor;
import buildings.Space;
import buildings.dwelling.Dwelling;
import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import util.functional.Tuple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Hotel extends Dwelling {

    public Hotel(Floor[] floors) {
        super(floors);
    }

    public Hotel(int floorsAmount, int[] flatsOnFloorAmount) {
        super(floorsAmount, flatsOnFloorAmount);
    }

    public Stars getStars() {

        Stars stars = HotelFloor.DEFAULT_STARS;

        for (Floor f : this.getFloors()) {
            if (!(f instanceof HotelFloor)) {
                continue;
            }

            if (((HotelFloor) f).getStars().compareTo(stars) > 0) {
                stars = ((HotelFloor) f).getStars();
            }
        }

        return stars;
    }

    @Override
    public Space getBestSpace() {

        if (getFloors().length == 0) {
            throw new FloorIndexOutOfBoundsException("zero floors");
        }
        if (this.spacesAmount() == 0) {
            throw new SpaceIndexOutOfBoundsException("zero flats");
        }

        Space best = Arrays.stream(this.getFloors())
                .filter(f -> f instanceof HotelFloor)
                .map(f -> (HotelFloor) f)
                .map(hf -> new Tuple<>(hf.getBestSpace(), hf.getStars()))
                .max(Comparator.comparingDouble(x -> x.getFirst().getArea() * x.getSecond().coef()))
                .get().getFirst();

        if (best == null) {
            throw new NullPointerException("there are no hotelfloors");
        }

        return best;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hotel");
        sb.append("\n").append("Floors: ").append(this.floorsAmount());
        sb.append("\n").append("Stars: ").append(this.getStars().value());
        for (Floor f : getFloors()) {
            sb.append("\n").append(f.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Hotel)) return false;
        Hotel hotel = (Hotel) obj;
        if (this.getStars() != hotel.getStars()) return false;

        return super.equals(hotel);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.getStars().value();
    }
}

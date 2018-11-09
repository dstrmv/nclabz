package buildings.dwelling.hotel;

import buildings.Space;
import buildings.dwelling.DwellingFloor;

import java.util.Iterator;

import static buildings.dwelling.hotel.Stars.*;

public class HotelFloor extends DwellingFloor {

    public static final Stars DEFAULT_STARS = ONE_STAR;

    private Stars stars;


    public HotelFloor(Space[] spaces) {
        super(spaces);
        stars = DEFAULT_STARS;
    }

    public HotelFloor(int capacity) {
        super(capacity);
        stars = DEFAULT_STARS;
    }

    public Stars getStars() {
        return stars;
    }

    public void setStars(Stars stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("HotelFloor");
        sb.append("\n").append("Floors: ").append(this.spacesAmount());
        sb.append("\n").append("Stars: ").append(this.stars.value());
        for (Space s: getSpaces()) {
            sb.append("\n").append(s.toString());
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof HotelFloor)) return false;
        HotelFloor hotelFloor = (HotelFloor) obj;
        if (this.getStars() != hotelFloor.getStars()) return false;

        return super.equals(hotelFloor);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.getStars().value();
    }

}

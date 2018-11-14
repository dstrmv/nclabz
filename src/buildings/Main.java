package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.dwelling.hotel.Stars;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.Buildings;
import util.comparators.SpaceComparator;

import java.util.Comparator;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Space[] flats = new Space[5];
        Space[] flats2 = new Space[5];
        Space[] flats3 = new Space[5];

        for (int i = 0; i < flats.length; i++) {
            flats[i] = new Flat(100 * i + 0.1, i + 1); //1
            flats2[i] = new Flat(10000 * i + 0.1, i + 100);//101
            flats3[i] = new Flat(1000000*i + 0.1, i + 1000000);
        }

        Floor fl = new HotelFloor(flats);

        Floor flcopy = new HotelFloor(flats);

//        System.out.println(fl.equals(flcopy));


        Floor fl2 = new HotelFloor(flats2);
        Floor fl3 = new HotelFloor(flats3);

        Space[] ar = fl.getSpaces();
        var res = Buildings.sortComp(ar, new SpaceComparator());
        Stream.of(res).forEach(System.out::println);

        System.out.println(fl2.compareTo(fl));

//        ((HotelFloor) fl).setStars(Stars.FIVE_STARS);
        ((HotelFloor) fl2).setStars(Stars.ONE_STAR);

        Hotel hotel = new Hotel(new Floor[] {fl,fl2});

        Floor[] floors = {fl, fl2, fl3};

        Building b1 = new Dwelling(floors);


    }


}

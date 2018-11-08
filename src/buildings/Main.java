package buildings;

import buildings.exceptions.InexchangeableFloorsException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Space[] offices = new Space[10];
        Space[] flats = new Space[10];

        for (int i = 0; i < offices.length; i++) {
            offices[i] = new Office(100 * i + 0.1, i + 1); //1
            flats[i] = new Flat(10000 * i + 0.1, i + 100);//101
        }

        Floor df = new DwellingFloor(offices);
        Floor df2 = new DwellingFloor(flats);

        Floor[] floors = {df, df2};

        Building building = new Dwelling(floors);

        Flat flat = new Flat(1.1, 1);
        Flat flat2 = new Flat(2.2, 2);
        Flat flat3 = new Flat(3.3, 3);

        OfficeFloor fl = new OfficeFloor(new Space[]{flat, flat2, flat3});

        try {
            //Building building2 = (Building) building.clone();
            //System.out.println(building);
            //System.out.println();
            //System.out.println(building2);

            //building.setSpace(0, flat);

            //System.out.println();
            //System.out.println(building);
            //System.out.println(building2);

            System.out.println(fl);
            System.out.println();

            Floor fl2 = (Floor) fl.clone();
            System.out.println(fl2);
            System.out.println();

            fl.setSpace(1, new Flat(111111,111111));
            System.out.println(fl);
            System.out.println(fl2);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }


}

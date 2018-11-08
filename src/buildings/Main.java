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

        Floor fl = new OfficeFloor(offices);
        Floor fl2 = new OfficeFloor(flats);

        Floor[] floors = {fl, fl2};

        Building b1 = new OfficeBuilding(floors);

        Flat flat = new Flat(1.1, 1);
        Flat flat2 = new Flat(2.2, 2);
        Flat flat3 = new Flat(3.3, 3);

        OfficeFloor fl3 = new OfficeFloor(new Space[]{flat, flat2, flat3});

        try {


            System.out.println(b1);
            System.out.println();
            Building b2 = (Building) b1.clone();
            System.out.println(b2);
            System.out.println();

            b1.setSpace(0, new Flat(11111,111111));

            //b1.setFloor(0,fl3);
            System.out.println(b1);
            System.out.println(b2);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }


}

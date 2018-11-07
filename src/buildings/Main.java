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

        Floor df = new OfficeFloor(offices);
        Floor df2 = new DwellingFloor(flats);

        Floor[] floors = {df, df2};

        Building building = new OfficeBuilding(floors);
        Building building2 = null;

        //System.out.println(building.getFloors().length);
        //Arrays.stream(building.getFloors()).map(Floor::getSpaces).flatMap(Arrays::stream).forEach(System.out::println);

        // Files.createFile(Paths.get(".\\file.txt"));
        File file = new File(".\\file.txt");
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream writer = new FileOutputStream(file);
             InputStream reader = new FileInputStream(file)) {

            Buildings.serializeBuilding(building, writer);
            building = Buildings.deserializeBuilding(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(building);

        System.out.println();

        Flat flat = new Flat(123, 456);
        Flat flat2 = new Flat(1230, 4560);
        Flat flat3 = new Flat(7777, 77777);

        try {
            DwellingFloor dwelflor = new DwellingFloor(new Flat[] {flat, flat2});
            System.out.println(dwelflor);
            DwellingFloor dwelflor2 = (DwellingFloor) dwelflor.clone();
            System.out.println(dwelflor2);
            dwelflor.setSpace(1, flat3);
            System.out.println(dwelflor);
            System.out.println(dwelflor2);


        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }


}

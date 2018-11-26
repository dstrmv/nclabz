package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.dwelling.hotel.Stars;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;
import buildings.threads.*;
import util.Buildings;
import util.comparators.SpaceComparator;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Flat[][] flats = new Flat[5][5];
        for (int i = 0; i < flats.length; i++) {
            for (int j = 0; j < flats[i].length; j++) {
                flats[i][j] = new Flat(i * j * 20 + i + j + 1, i * j + 1);
            }
        }

        Floor[] floors = new Floor[flats.length];
        for (int i = 0; i < flats.length; i++) {
            floors[i] = new DwellingFloor(flats[i]);
        }

        Building building = new Dwelling(floors);
        Building officebuilding = new OfficeBuilding(floors.clone());
        Building hotel = new Hotel(floors.clone());
        Building[] array = new Building[]{building, officebuilding, hotel};
        //System.out.println(Buildings.createFloor(flats[0], DwellingFloor.class));


        try (PrintWriter pw = new PrintWriter(new FileWriter("buildinginfo.txt"));
             PrintWriter pwt = new PrintWriter(new FileWriter("buildingtypes.txt"));
             Reader in = new FileReader("buildinginfo.txt");
             ObjectOutputStream outputObject = new ObjectOutputStream(new FileOutputStream("buildings.ser"));
             ObjectInputStream inputObject = new ObjectInputStream(new FileInputStream("buildings.ser"))

        ) {

            //Buildings.writeBuildings(new Building[] {building, officebuilding, hotel}, pw);
            //Buildings.writeBuildingsTypes(new Building[] {building, officebuilding, hotel}, pwt);

            outputObject.writeObject(array);
            try {
                Building[] inputArray = (Building[]) inputObject.readObject();
                System.out.println(Arrays.deepEquals(array, inputArray));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

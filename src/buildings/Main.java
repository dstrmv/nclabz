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
import buildings.threads.*;
import util.Buildings;
import util.comparators.SpaceComparator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Flat[][] flats = new Flat[10][50];
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

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File("buildinginfo.txt")));
             PrintWriter pwt = new PrintWriter(new FileWriter(new File("buildingtypes.txt")))) {

            Buildings.writeBuilding(building, pw);
            Buildings.writeBuildingTypes(building, pwt);
        } catch (IOException e) {

        }

    }


}

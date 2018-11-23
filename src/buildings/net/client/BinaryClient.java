package buildings.net.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinaryClient {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 1099);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Reader fileinfoIn = new FileReader("buildinginfo.txt");
             Reader filetypesIn = new FileReader("buildingtypes.txt");
        ) {

            Scanner info = new Scanner(fileinfoIn);
            Scanner types = new Scanner(filetypesIn);

            int floors;
            int spaces;
            double area;
            int rooms;

            String type = types.nextLine();
            while (!type.isEmpty()) {
                writer.println(type);
                floors = info.nextInt();
                writer.println(floors);
                for (int i = 0; i < floors; i++) {
                    spaces = info.nextInt();
                    writer.println(spaces);
                    for (int j = 0; j < spaces; j++) {
                        area = info.nextDouble();
                        writer.println(area);
                        rooms = info.nextInt();
                        writer.println(rooms);
                    }
                }

                type = types.nextLine();
            }

            String[] costs = readCosts(reader);
            writeCostsToFile(costs);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCostsToFile(String[] costs) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("buildingcost.txt"));
            for (String c : costs) {
                pw.println(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] readCosts(BufferedReader reader) {
        List<String> costs = new ArrayList<>();
        String cost = "";
        do {
            try {
                cost = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!cost.isEmpty());
        return costs.toArray(new String[0]);
    }


}

package buildings.net.client;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class BinaryClient {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
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
            try {
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
            } catch (NoSuchElementException e) {

            }

            writer.flush();
            System.out.println("buildings are sent");
            String[] costs = readCosts(reader);
            System.out.println("cost are readed");
            writeCostsToFile(costs);
            System.out.println("writed to file");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCostsToFile(String[] costs) {
        File file = new File("buildingcosts.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for (String c : costs) {
                pw.println(c);
            }
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static String[] readCosts(Reader reader) {
        List<String> costs = new ArrayList<>();
        Scanner sc = new Scanner(reader);
        try {
            String cost = sc.nextLine();
            while (!cost.equals(";;;")) {
                costs.add(cost);
                cost = sc.nextLine();
            }
        } catch (NoSuchElementException e) {
        }

        return costs.toArray(new String[0]);
    }


}

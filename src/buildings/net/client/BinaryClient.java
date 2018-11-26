package buildings.net.client;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.*;

public class BinaryClient {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Socket clientSocket = new Socket("localhost", 1099);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader info = new BufferedReader(new FileReader("buildinginfo.txt"));
             BufferedReader types = new BufferedReader(new FileReader("buildingtypes.txt"));
        ) {

            sendBuildingInfoTypesFromFile(writer, info, types);
            //System.out.println("buildings are sent");
            String[] costs = readCosts(reader);
            //System.out.println("cost are readed");
            writeCostsToFile(costs);
            //System.out.println("writed to file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendBuildingInfoTypesFromFile(PrintWriter writer, BufferedReader info, BufferedReader types) throws IOException {
        int floors;
        int spaces;
        double area;
        int rooms;
        String type;

        while ((type = types.readLine()) != null) {

            System.out.println(type);
            writer.println(type);
            floors = Integer.parseInt(info.readLine());
            System.out.println(floors);
            writer.println(floors);
            for (int i = 0; i < floors; i++) {
                spaces = Integer.parseInt(info.readLine());
                writer.println(spaces);
                System.out.println(spaces);
                for (int j = 0; j < spaces; j++) {
                    area = Double.parseDouble(info.readLine());
                    System.out.println(area);
                    writer.println(area);
                    rooms = Integer.parseInt(info.readLine());
                    writer.println(rooms);
                    System.out.println(rooms);
                }
            }
        }
        writer.println("PEPEGA");
        writer.flush();
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
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> costs = new ArrayList<>();
        String input = null;
        try {
            while ((input = bufferedReader.readLine()) != null ) {
                if (input.equals("PEPEGA")) break;
                costs.add(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return costs.toArray(new String[0]);
    }
}
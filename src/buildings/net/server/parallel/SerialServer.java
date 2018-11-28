package buildings.net.server.parallel;

import buildings.interfaces.Building;
import buildings.net.server.BuildingUnderArrestException;
import buildings.net.server.Servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    public static void main(String[] args) {


        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1099);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("connected");

                Thread thread = new Thread(() -> {
                    try (ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
                         ObjectInputStream reader = new ObjectInputStream(client.getInputStream())) {

                        Building[] buildings = (Building[]) reader.readObject();
                        Object[] result = Servers.generateCosts(buildings);
                        writer.writeObject(result);
                        writer.flush();

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

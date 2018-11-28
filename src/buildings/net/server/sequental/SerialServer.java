package buildings.net.server.sequental;

import buildings.interfaces.Building;
import buildings.net.server.BuildingUnderArrestException;
import buildings.net.server.Servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    public static void main(String[] args) {
        try {
            System.out.println("started");
            ServerSocket serverSocket = new ServerSocket(1099);
            Socket client = null;
            ObjectInputStream reader = null;
            ObjectOutputStream writer = null;
            while (true) {
                client = serverSocket.accept();
                System.out.println("connected");
                writer = new ObjectOutputStream(client.getOutputStream());
                reader = new ObjectInputStream(client.getInputStream());
                Building[] buildings = null;

                try {
                    buildings = (Building[]) reader.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println("Building received");

                writer.writeObject(Servers.generateCosts(buildings));
                writer.flush();
                reader.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

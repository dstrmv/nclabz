package buildings.net.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BinaryClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = null;
            PrintWriter writer = null;
            BufferedReader reader = null;
            try {
                clientSocket = new Socket("localhost", 1099);
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientSocket.close();
                writer.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

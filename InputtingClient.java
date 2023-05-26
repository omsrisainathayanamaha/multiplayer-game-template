import java.io.*;
import java.net.*;

public class InputtingClient {

    public static void main(String[] args) {
        try {

            Server s = new Server(808);
            Client c = new Client("localhost", 808);
            Socket a = c.createSocket();
            int[] one = { 15, 16 };
            int[] two = { 10, 11 };

            s.acceptConnection();
            s.write(new Data(one, two));
            Data n = (Data) c.read(a);
            if (n == null) {
                System.out.print("Data is not correct.");
                return;
            }
            System.out.print(n);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
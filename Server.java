
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket ss;
    Socket client;
    ObjectOutputStream out;

    public ServerSocket getSs() {
        return ss;
    }

    public void setSs(ServerSocket ss) {
        this.ss = ss;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public Server(int portNum) {
        try {
            ss = new ServerSocket(portNum);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Socket acceptConnection() {
        try {
            client = ss.accept();

            return client;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;

    }

    public void write(Serializable a) {
        try {

            out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(a);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            out.close();
            client.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

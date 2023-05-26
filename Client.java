import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client {
    String ip;
    int port;
    Socket start;
    ObjectInputStream in;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getStart() {
        return start;
    }

    public void setStart(Socket start) {
        this.start = start;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Client(String ips, int ports) {
        ip = ips;
        port = ports;

    }

    public Socket createSocket() {
        try {
            start = new Socket(ip, port);
            return start;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return start;

    }

    public Serializable read(Socket from) {
        Serializable a;
        try {

            in = new ObjectInputStream(from.getInputStream());
            a = (Serializable) in.readObject();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeClient() {
        try {
            in.close();
            start.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
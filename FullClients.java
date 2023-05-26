/*
 * A couple assumptions:
 * The name of the class that contains all data is Main. On the server, we must
 * create our own EntitySpawner (that's a class already
 * written by Jack with method SpawnEntity() and new EntitySpawner(). Study the
 * code of Server.java and Client.java. InputtingClient
 * is a tester class. An Entity has methods described in the Enemy Behavior
 * Google doc. The two types of entities are HotBallsMaster
 * and Swordsman, with Boss extends HotBallsMaster. Vector3f is a class that describes
 * 3D coords.
 * 
 */

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class FullClients {
    /*
     * Methods to write: postPlayer() (see description at addPlayer()),
     * addSelfToFullServer()
     */
    private FullServers myServer;
    private Player myPlayer;
    private CurrentPositions currPos;
    private Client myClient;
    private Socket readingSocket;
    public int myIndex;

    public FullServers getMyServer() {
        return myServer;
    }

    public void setMyServer(FullServers myServer) {
        this.myServer = myServer;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public CurrentPositions getCurrPos() {
        return currPos;
    }

    public void setCurrPos(CurrentPositions currPos) {
        this.currPos = currPos;
    }

    public Client getMyClient() {
        return myClient;
    }

    public void setMyClient(Client myClient) {
        this.myClient = myClient;
    }

    public Socket getReadingSocket() {
        return readingSocket;
    }

    public void setReadingSocket(Socket readingSocket) {
        this.readingSocket = readingSocket;
    }

    public int getMyIndex() {
        return myIndex;
    }

    public void setMyIndex(int myIndex) {
        this.myIndex = myIndex;
    }

    public FullClients() {

        myServer = null;
        currPos = null;
        myPlayer = null;
        myClient = null;
        readingSocket = null;
        myIndex = -1;
    }

    public FullClients(FullServers mine, Player mPlayer) {
        myServer = mine;
        myPlayer = mPlayer;
        currPos = null;
        myClient = new Client(myServer.getIP(), FullServers.PORT_NUMBER);
        readingSocket = myClient.createSocket();
        myIndex = -1;

    }

    public void postPlayer(Player p, boolean makePMine) {
        String a;
        boolean added = false;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("google.com"), 10002);
            a = socket.getLocalAddress().getHostAddress();
        } catch (Exception e) {

            a = "";
        }
        while (!added) {
            Server sender = new Server(FullServers.PORT_NUMBER);
            added = myServer.addPlayer(a);
            sender.acceptConnection();
            sender.write(p);
            sender.closeServer();
        }
        if (makePMine)
            myPlayer = p;

    }

    public void postPlayer() {
        postPlayer(myPlayer, false);
    }

    public CurrentPositions readServer() {
        currPos = (CurrentPositions) myClient.read(readingSocket);
        return currPos;
    }

}

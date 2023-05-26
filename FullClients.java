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
    private Vector3f playerLoc;
    private double playerDir;

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
        playerLoc = myPlayer.getLocation();
        playerDir = myPlayer.getDirection();

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

    public int getMyIndex() {
        return myIndex = myServer.findPlayerIndex(getMyPlayer().getName());
    }
    // Now, here's the part we need to figure out, Anwar. We need to take
    // differences in the player's location as found in myPlayer.getLocation() and
    // call act player on the server. Then, we need to make a main method the game
    // engine can run every tick, for both FullServers and FullClients.

    public void doTick() {

        readServer();
        Vector3f origin = new Vector3f(0, 0, 0);
        Vector3f loc = myPlayer.getLocation();
        float myX = playerLoc.x;
        float x = loc.x;
        float myY = playerLoc.y;
        float y = loc.y;
        float myZ = playerLoc.z;
        float z = loc.z;

        float changeInY = y - myY;

        String lastMovement = myPlayer.getHasBeenDone();
        boolean forward = lastMovement.toLowerCase().equals("w");

        boolean right = lastMovement.toLowerCase().equals("d");
        boolean horizontal = lastMovement.toLowerCase().equals("a") || lastMovement.toLowerCase().equals("D");

        myServer.makePlayerAct(forward, right, horizontal, myIndex);
        /*
         * 
         * double myDirection = playerDir;
         * double direction = myPlayer.getDirection();
         * double changeInDirection = direction - myDirection;
         * double coTangentOfForward = (double) 1 / Math.tan((direction) * (Math.PI /
         * 180));
         * double slope = x/z;
         * 
         * 
         * /*double coTangentOfForwardChange = (double) 1 / Math.tan((changeInDirection)
         * * (Math.PI / 180));
         * double coTangentOfRight = (double) 1 / Math.tan((direction + 90) * (Math.PI /
         * 180));
         * /*double coTangentOfRightChange = (double) 1 / Math.tan((changeInDirection +
         * 90) * (Math.PI / 180));
         * double coTangentOfBackward = (double) 1 / Math.tan((direction + 180) *
         * (Math.PI / 180));
         * double coTangentOfBackwardChange = (double) 1 / Math.tan((changeInDirection +
         * 180) * (Math.PI / 180));
         * double coTangentOfLeft = (double) 1 / Math.tan((direction - 90) * (Math.PI /
         * 180));
         * double coTangentOfLeftChange = (double) 1 / Math.tan((changeInDirection - 90)
         * * (Math.PI / 180));
         * 
         * boolean isForward = coTangentOfForward == slope;
         * boolean isBackward = coTangentOfBackward == slope;
         */

        myServer.makePlayerJump(myIndex, changeInY);

    }

    public CurrentPositions readServer() {
        currPos = (CurrentPositions) myClient.read(readingSocket);
        return currPos;
    }

}

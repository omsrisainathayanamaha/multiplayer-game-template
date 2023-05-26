/*
 * A couple assumptions:
 * The name of the class that contains all data is Main. On the server, we must
 * create our own EntitySpawner (that's a class already
 * written by Jack with method SpawnEntity() and new EntitySpawner(). Study the
 * code of Server.java and Client.java. InputtingClient
 * is a tester class. An Entity has methods described in the Enemy Behavior
 * Google doc. The two types of entities are HotBallsMaster
 * and Swordsman, with Boss extends HotBallsMaster.
 * 
 */

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

class FullServers /* For Servers. */ {
    public final static int PORT_NUMBER = 5999;
    private int port;
    private EntitySpawner spawner1;
    private EntitySpawner spawner2;
    private EntitySpawner spawner3;
    private EntitySpawner spawner4;
    private ArrayList<Entity> entities;
    private ArrayList<Player> players;
    public ArrayList<FullClients> clients;
    public int wave;
    private Server server;
    private Client receiver;
    private int lastTick;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public EntitySpawner getSpawner1() {
        return spawner1;
    }

    public void setSpawner1(EntitySpawner spawner1) {
        this.spawner1 = spawner1;
    }

    public EntitySpawner getSpawner2() {
        return spawner2;
    }

    public void setSpawner2(EntitySpawner spawner2) {
        this.spawner2 = spawner2;
    }

    public EntitySpawner getSpawner3() {
        return spawner3;
    }

    public void setSpawner3(EntitySpawner spawner3) {
        this.spawner3 = spawner3;
    }

    public EntitySpawner getSpawner4() {
        return spawner4;
    }

    public void setSpawner4(EntitySpawner spawner4) {
        this.spawner4 = spawner4;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<FullClients> getClients() {
        return clients;
    }

    public void setClients(ArrayList<FullClients> clients) {
        this.clients = clients;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Client getReceiver() {
        return receiver;
    }

    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }

    public int getLastTick() {
        return lastTick;
    }

    public void setLastTick(int lastTick) {
        this.lastTick = lastTick;
    }

    public FullServers() {
        spawner1 = new EntitySpawner(EntitySpawner.BACK_LEFT_CORNER);
        spawner2 = new EntitySpawner(EntitySpawner.BACK_RIGHT_CORNER);
        spawner3 = new EntitySpawner(EntitySpawner.FRONT_LEFT_CORNER);
        spawner4 = new EntitySpawner(EntitySpawner.FRONT_RIGHT_CORNER);
        port = PORT_NUMBER;
        wave = 1;
        entities = new ArrayList<Entity>();
        players = new ArrayList<Player>();
        server = new Server(port);
        lastTick = 0;
        receiver = null;
        clients = new ArrayList<FullClients>();
    }

    public String getIP() {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("google.com"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (Exception e) {

            return "";
        }

    }

    public void acceptConnection() {
        server.acceptConnection();
    }

    public static void main(String[] args) { // a tester method
        System.out.println(new FullServers().getIP());

    }

    public boolean addPlayer(String playerInputterIP) { // FullClient method: postPlayer - make a new server, accept a
                                                        // connection, call this method, write, then close the server.
        receiver = new Client(playerInputterIP, PORT_NUMBER);
        try {
            players.add((Player) receiver.read(receiver.createSocket()));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void makePlayerAct(boolean forward, boolean right, boolean horizontal, int playerIndex) {
        Player placeholder = players.get(playerIndex);

        if (forward && !horizontal) {
            placeholder.moveForward(Player.PLAYER_WALK_SPEED);

        } else if (!(forward || horizontal)) {
            placeholder.moveBack(Player.PLAYER_WALK_SPEED);

        } else if (right && horizontal) {

            placeholder.moveRight(Player.PLAYER_WALK_SPEED);

        } else if (!right && horizontal) {
            placeholder.moveLeft(Player.PLAYER_WALK_SPEED);
        }
        players.remove(playerIndex);
        players.add(playerIndex, placeholder);

    }

    public void makePlayerJump(int playerIndex, float amount) {
        Player placeholder = players.remove(playerIndex);
        Vector3f playerLoc = placeholder.getLocation();
        placeholder.setLocation(new Vector3f(playerLoc.x, playerLoc.y + amount, playerLoc.z));
    }

    public int findPlayerIndex(String name) {
        int index = 0;
        for (Player x : players) {
            if (x.getName().equals(name))
                return index;
            index++;
        }
        return -1;
    }

    public void doTick() {
        if (lastTick % (500 / wave) < 1) {
            if (lastTick % (1000 / wave) < 1) {
                entities.add(spawner1.SpawnEntity());
                entities.add(spawner3.SpawnEntity());
            } else {
                entities.add(spawner2.SpawnEntity());
                entities.add(spawner4.SpawnEntity());
            }

        }
        lastTick++;
        server.write(new CurrentPositions(entities, players, wave, lastTick));

    }

}
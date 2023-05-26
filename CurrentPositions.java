import java.util.ArrayList;

public class CurrentPositions implements java.io.Serializable {
    public ArrayList<Entity> entities;
    public ArrayList<Player> players;
    public int wave;
    public int lastTick;

    public CurrentPositions(ArrayList<Entity> e, ArrayList<Player> p, int w, int lt) {
        entities = e;
        players = p;
        wave = w;
        lastTick = lt;
    }
}

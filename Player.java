import java.io.Serializable;

public class Player implements Serializable {
    private String name;

    public static final int PLAYER_WALK_SPEED = 2;

    public Player(String n) {
        name = n;

    }

    public String getName() {
        return name;
    }

    public void moveForward(int i) {
    }

    public void moveBack(int pLAYER_WALK_SPEED2) {
    }

    public void moveRight(int pLAYER_WALK_SPEED2) {
    }

    public void moveLeft(int pLAYER_WALK_SPEED2) {
    }
}

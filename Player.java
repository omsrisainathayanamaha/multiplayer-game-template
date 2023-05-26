import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String hasBeenDone;

    public String getHasBeenDone() {
        return hasBeenDone;
    }

    public void setHasBeenDone(String hasBeenDone) {
        this.hasBeenDone = hasBeenDone;
    }

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

    public Vector3f getLocation() {
        return null;
    }

    public double getDirection() {
        return 0;
    }

    public void setLocation(Vector3f vector3f) {
    }
}

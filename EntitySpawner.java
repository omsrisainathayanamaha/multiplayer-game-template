
public class EntitySpawner {
    public static final Vector3f BACK_LEFT_CORNER = new Vector3f(-250, 0, -250);
    public static final Vector3f BACK_RIGHT_CORNER = new Vector3f(250, 0, -250);
    public static final Vector3f FRONT_LEFT_CORNER = new Vector3f(-250, 0, 250);
    public static final Vector3f FRONT_RIGHT_CORNER = new Vector3f(250, 0, 250);
    private Vector3f myLoc;

    public EntitySpawner(Vector3f loc) {

        myLoc = loc;
    }

    public Vector3f setLoc(Vector3f newOne) {
        Vector3f old = new Vector3f(myLoc);
        myLoc = newOne;
        return old;
    }

    public Entity SpawnEntity() {
        return new Entity();
    }
}

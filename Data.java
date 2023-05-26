import java.io.Serializable;
import java.util.Arrays;

public class Data implements Serializable {

    int data;
    int data2;

    public Data(int[] data, int[] dp2) {

        this.data = data[0];
        this.data2 = dp2[0];
    }

    @Override
    public String toString() {

        return "" + data + data2;
    }
}
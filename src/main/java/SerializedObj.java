import java.io.Serializable;

/**
 * Created by kevinzhong on 25/10/15.
 */
public class SerializedObj<T> implements Serializable {
    int type;
    T value;

    public static final int STACK_PUSH = 1;
    public static final int STACK_POP = 2;
    public static final int STACK_TOP = 3;

    public static final int SET_ADD = 4;
    public static final int SET_CONTAINS = 5;
    public static final int SET_REMOVE = 6;

    public SerializedObj(int type){
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

}

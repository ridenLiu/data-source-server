import java.util.ArrayList;
import java.util.List;

/**
 * 于业务无关的 测试类
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
       List<String>[]lsa = new ArrayList[10];

    }


}

class Apple<T extends Number> {
    T size;

    public Apple() {
    }

    public Apple(T size) {
        this.size = size;
    }

    public void setSize(T size) {
        this.size = size;
    }

    public T getSize() {
        return this.size;
    }
}








package base;

import java.net.SocketException;

/**
 * Created by WSharkCoder on 2024/07/25. <br/>
 * 读懂Java异常栈
 *
 * @author WSharkCoder
 * @date 2024/07/25
 */
public class ReadExpectation {
    public static void main(String[] args) {
        fun1();
    }

    static void fun1() {
        fun2();
    }

    static void fun2() {
        fun3();
    }

    static void fun3() {
        try {
            fun4();
        } catch (Exception ex) {
            throw new RuntimeException("fun3", ex);
        }
    }

    static void fun4() throws SocketException {
        throw new RuntimeException("fun4", new SocketException());
    }
}

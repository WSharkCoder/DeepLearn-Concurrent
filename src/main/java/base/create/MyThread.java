package base.create;

/**
 * @author WSharkCoder
 * @date 2021/07/24
 * 继承Thread创建线程
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        //...
    }
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}

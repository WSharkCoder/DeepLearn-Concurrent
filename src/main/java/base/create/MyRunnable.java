package base.create;

/**
 * @author WSharkCoder
 * @date 2021/07/24
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        //...
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}

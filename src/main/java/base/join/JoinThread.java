package base.join;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 16:17
 * @ClassName JoinThread
 * Thread.join()方法示例
 */
public class JoinThread {
    public volatile static int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            int target = 1000000000;
            while (i < target) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new AddThread();
        t.start();
        t.join();
        System.out.println(i);

    }

}

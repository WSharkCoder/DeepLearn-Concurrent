package base;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 17:13
 * @ClassName Daemon
 * 守护线程
 */
public class Daemon {
    public static class DemonT extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println("DemonT is alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        Thread t = new DemonT();
////      设置线程为守护线程
//        t.setDaemon(true);
//        t.start();
//
//        Thread.sleep(2000);
        System.out.println( (int)3 / 2);
    }


}

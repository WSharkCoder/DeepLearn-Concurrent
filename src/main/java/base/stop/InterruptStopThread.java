package base.stop;

/**
 * @author WSharkCoder
 * @date 2021/07/21
 * 本示例演示利用线程中断实现DiyStopThread
 * JDK线程中断API:
 * public void Thread.interrupt() //中断线程
 * public boolean Thread.isInterrupted() //判断线程是否被中断
 * public static boolean Thread.interrupted() //判断线程是否中断，并清除中断状态
 * JDK线程中断机制:
 * 线程中断并不会使线程立即退出，而是给线程发送一个通知.
 * 至于被通知线程如何处理中断，则完全由被通知线程自行决定.
 * 注意！:本示例中将抛出异常！
 */
public class InterruptStopThread {
    /**
     * 模拟用户 u
     */
    public static InterruptStopThread.User u = new InterruptStopThread.User();

    public static void main(String[] args) throws InterruptedException {
        new InterruptStopThread.ReadObjectThread().start();
        while (true) {
            ChangeObjectThread changeObjectThread = new ChangeObjectThread();
            changeObjectThread.start();
            Thread.sleep(200);
            changeObjectThread.interrupt();

        }
    }

    /**
     * 线程中断实现的可安全终止的写线程
     */
    public static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println(!u.name.equals(String.valueOf(u.id)) ? u.id + "/" + u.name : "");

                    System.out.println("Thread exit.");
                    break;
                }
                synchronized (u) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    //修改ID
                    u.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    //修改name
                    u.setName(String.valueOf(v));
                }
                //让掉当前线程的CPU时间片，正在运行的线程转变为就绪状态，重新竞争CPU调度
                Thread.yield();
            }
        }
    }

    /**
     * 读线程 当id和name不一致则输出
     */
    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    if (u.getId() != Integer.valueOf(u.getName())) {
                        System.out.println(u.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    /**
     * 用户ID和name应该始终一致
     */
    public static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


}

package base.stop;

/**
 * @author WSharkCoder
 * @date 2021/7/21
 * 自定义安全终止线程方法
 */
public class DiyStopThread {
    /**
     * 模拟用户 u
     */
    public static DiyStopThread.User u = new DiyStopThread.User();

    public static void main(String[] args) throws InterruptedException {
        new DiyStopThread.ReadObjectThread().start();
        while (true) {
            ChangeObjectThread t = new DiyStopThread.ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            //自定义停止线程
            t.stopMe();
        }
    }

    /**
     * 自定义可安全终止的写线程
     */
    public static class ChangeObjectThread extends Thread {
        /**
         * 线程终止标记位
         */
        private volatile boolean stopMe = false;

        /**
         * 设置线程终止标记位
         */
        public void stopMe() {
            stopMe = true;
        }

        @Override
        public void run() {
            while (true) {
                //判断线程终止标记位是否置位
                if (stopMe) {
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
                        e.printStackTrace();
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


}

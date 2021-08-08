package base.stop;

/**
 * @author WSharkCoder
 * @date 2021/7/21
 * 演示Thread.stop()可能导致的数据不一致性.
 * 数据不一致性是由于线程stop后释放持有的锁导致的.
 */
public class StopThread {
    /**
     * 模拟用户 u
     */
    public static StopThread.User u = new User();

    public static void main(String[] args) throws InterruptedException {
        //开启读线程
        new ReadObjectThread().start();
        while (true) {
            //间隔150ms开启和关闭写线程
            Thread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            t.stop();
        }
    }

    /**
     * 用户ID和name本应该始终一致
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
     * 写线程修改ID和Name
     */
    public static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    //随机修改成某个值
                    int v = (int) (System.currentTimeMillis() / 1000);
                    //修改ID
                    u.setId(v);
                    try {
                        //当前线程睡眠100ms
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //修改name
                    u.setName(String.valueOf(v));
                }
                //让出当前线程的CPU时间片，正在运行的线程转变为就绪状态，重新竞争CPU调度
                Thread.yield();
            }
        }
    }

    /**
     * 读线程 若id和name不一致则输出
     */
    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    //判断用户ID和name是否一直不一致则输出
                    if (u.getId() != Integer.valueOf(u.getName())) {
                        System.out.println(u.toString());
                    }
                }
                //让出时间
                Thread.yield();
            }
        }
    }

}

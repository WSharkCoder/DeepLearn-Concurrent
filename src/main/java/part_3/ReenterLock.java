package part_3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WSharkCoder
 * @date 2021/05/08
 * 重入锁案例
 * Re-Entrant-Lock:重入锁
 */
public class ReenterLock implements Runnable {
    /**
     * 实例化重入锁
     */
    public static ReentrantLock lock = new ReentrantLock();
    /**
     * 临界资源
     */
    public static int i = 0;
    /**
     * 迭代次数
     */
    private static final int COUNT = 100000000;

    @Override
    public void run() {
        for (int j = 0; j < COUNT; j++) {
            //加锁
            lock.lock();
            try {
                i++;
            } finally {
                //解锁
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl = new ReenterLock();
        //双线程测试
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}

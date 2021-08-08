package part_3;

import java.nio.file.FileAlreadyExistsException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WSharkCoder
 * @date 2021/05/09
 * 重入锁-公平锁案例
 */
public class FairLock implements Runnable {
    /**
     * 重入锁初始化并设置公平性
     */
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + "获取锁");
            } finally {
                fairLock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1, "Thread-t1");
        Thread t2 = new Thread(r1, "Thread-t2");
        t1.start();
        t2.start();
    }
}

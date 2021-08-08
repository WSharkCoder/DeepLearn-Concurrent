package part_3;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WSharkCoder
 * @date 2021/05/08
 * 重入锁-限时等待案例
 */
public class TimeLock implements Runnable {
    /**
     * 重入锁
     */
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        System.out.println("Thread-" + Thread.currentThread().getId());
        try {
            //等待时长 计时单位
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                Thread.sleep(6000);
            } else {
                System.out.println("Thread" + Thread.currentThread().getId() + ":Get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        TimeLock tl = new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();
    }
}

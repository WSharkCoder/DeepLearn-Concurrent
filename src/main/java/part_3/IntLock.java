package part_3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WSharkCoder
 * @date 2021/05/08
 * 重入锁-中断响应机制-解决死锁问题
 */
public class IntLock implements Runnable {
    /**
     * 重入锁-1
     */
    public static ReentrantLock lock1 = new ReentrantLock();
    /**
     * 重入锁-2
     */
    public static ReentrantLock lock2 = new ReentrantLock();
    /**
     * 加锁顺序==>方便模拟死锁案例
     */
    int lock;

    /**
     * 控制加锁顺序，方便构造死锁
     *
     * @param lock
     */
    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                //锁-1上锁并允许等待中断
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                //锁-2上锁并允许等待中断
                lock2.lockInterruptibly();
            } else {
                //锁-2上锁并允许等待中断
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {

                }
                //锁-1上锁并允许等待中断
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getId() + ":ERROR");
            e.printStackTrace();
        } finally {
            //判断本线程是否持有锁-1,持有则解锁-1
            if (lock1.isHeldByCurrentThread())
                lock1.unlock();
            //判断本线程是否持有锁-2,持有则解锁-2
            if (lock2.isHeldByCurrentThread())
                lock2.unlock();
            System.out.println(Thread.currentThread().getId() + ":线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        //线程中断
        t2.interrupt();
    }
}

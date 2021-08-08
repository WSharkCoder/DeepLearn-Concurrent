package part_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author WSharkCoder
 * @date 2021/05/09
 * 信号量 案例
 */
public class SemaphoreDemo implements Runnable{
    final Semaphore semaphore = new Semaphore(5);
    @Override
    public void run() {
         try{
             semaphore.acquire();
             //模拟耗时操作
             Thread.sleep(2000);
             System.out.println(Thread.currentThread().getId() + ":done!");
             semaphore.release();

         } catch (InterruptedException e) {
             e.printStackTrace();
         }

    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemaphoreDemo demo = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            exec.submit(demo);
        }
    }
}

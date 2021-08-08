package part_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WSharkCoder
 * @date 2021/05/10
 * 线程池案例
 */
public class ThreadPoolDemo {
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            //输出线程ID
            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        MyTask task = new MyTask();
        //固定5线程
//        ExecutorService es = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 10; i++) {
//            es.submit(task);
//        }
//        //线程池关闭后本线程才会关闭
//        es.shutdown();

        ExecutorService ctp = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            ctp.submit(task);
        }
        //关闭线程池
        ctp.shutdown();
    }
}

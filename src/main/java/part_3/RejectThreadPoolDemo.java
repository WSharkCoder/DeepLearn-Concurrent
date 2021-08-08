package part_3;

import java.util.concurrent.*;

/**
 * @author WSharkCoder
 * @date 2021/05/10
 */
public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread ID" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(
                //常驻线程数
                5,
                //最大线程数
                5,
                //存活时间
                0L,
                //存活时间单位
                TimeUnit.MILLISECONDS,
                //有界任务队列
                new LinkedBlockingQueue<Runnable>(10),
                //默认线程工厂
                Executors.defaultThreadFactory(),
                //自定义拒绝策略
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + "is discard");
                    }
                }
        );
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            es.submit(myTask);
            Thread.sleep(10);
        }

        ExecutorService tpe= new ThreadPoolExecutor(
                5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        System.out.println("Create Thread-ID: " + t.getId());
                        return t;
                    }
                }
        );
        for (int i = 0; i < 5; i++) {
            tpe.submit(myTask);
        }
        Thread.sleep(2000);

    }
}

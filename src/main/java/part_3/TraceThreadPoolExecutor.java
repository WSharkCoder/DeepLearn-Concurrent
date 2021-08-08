package part_3;

import java.util.concurrent.*;

/**
 * @author WSharkCoder
 * @date 2021/05/12
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrance(), Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable command) {
        return super.submit(wrap(command, clientTrance(), Thread.currentThread().getName()));
    }

    private Exception clientTrance() {
        return new Exception("Client stack trance");
    }

    /**
     * 异常包装器类
     *
     * @param task
     * @param clientStack
     * @param clientThreadName
     * @return
     */
    private Runnable wrap(final Runnable task, final Exception clientStack, String clientThreadName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pools = new TraceThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
        /**
         * 错误堆栈
         */
        for (int i = 0; i < 5; i++) {
            pools.execute(new DivTask(100, i));
        }
    }

    public static class DivTask implements Runnable {
        int a, b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            double re = a / b;
            System.out.println(re);
        }
    }
}

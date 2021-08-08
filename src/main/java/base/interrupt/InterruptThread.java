package base.interrupt;


/**
 * @author WSharkCoder
 * @date 2021/7/21
 * 前面InterruptStopThread示例中遗留了InterruptException这一异常问题！
 * 利用JDK线程中断机制和Diy标记位两种方式实现Thread.stop()方法的区别在于:
 * 当线程处于WAITING(Thread.wait()方法进入)和TIME_WAITING(Thread.sleep进入)时线程中断机制是如何处理的！
 * (Div标记位处理不了上述两种状态！)
 * 当线程出于WAITING和TIME_WAITING状态时,线程被中断,则被中断线程将抛出InterruptedException异常并清除中断标记位！
 */
public class InterruptThread {
    /**
     * 即使中断标志位被设置，若不进行相应的逻辑处理，线程依旧不会被中断。
     *
     * @throws InterruptedException
     */
    public static void ThreadInterrupt() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        //设置中断标志位
        t1.interrupt();
    }

    /**
     * 逻辑判断中断标志位是否被置位，并返回线程
     *
     * @throws InterruptedException
     */
    public static void ThreadIsInterrupted() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                }
                Thread.yield();
            }
        };
        t1.start();
        Thread.sleep(2000);
        //设置中断标志位
        t1.interrupt();
    }

    public static void SleepThreadInterrupt() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted When Sleep");
                        //设置中断标志位
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadInterrupt();
        ThreadIsInterrupted();
        SleepThreadInterrupt();
    }
}

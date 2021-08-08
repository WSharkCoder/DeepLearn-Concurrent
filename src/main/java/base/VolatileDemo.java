package base;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 16:29
 * @ClassName VolatileDemo
 * volatile
 */
public class VolatileDemo {
    /**
     * volatile 无法保证原子性
     */
    static volatile int i = 0;

    public static class PlusTask implements Runnable {
        @Override
        public void run() {
            int TIMES = 1000;
            for (int cnt = 0; cnt < TIMES; cnt++) {
                i++;
            }
        }
    }

    /**
     * volatile 非原子性演示
     *
     * @throws InterruptedException
     */
    public static void volatileNonAtomicity() throws InterruptedException {
        int N = 10;
        Thread[] threads = new Thread[N];
        for (int idx = 0; idx < N; idx++) {
            threads[idx] = new Thread(new PlusTask());
            threads[idx].start();
        }
        for (int idx = 0; idx < N; idx++) {
            threads[idx].join();
        }
        System.out.println(i);
    }

    public static void main(String[] args) throws InterruptedException {
        volatileNonAtomicity();
    }

}

package part_3;

import java.util.concurrent.*;

/**
 * @author WSharkCoder
 * @date 2021/05/12
 * 线程池吞没线程异常
 */
public class ThreadPoolEatException {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
//            submit()和execute()的区别
//            pool.execute(new DivTask(100, i));
            Future re = pool.submit(new DivTask(100, i));
            re.get();
        }

    }
}

class DivTask implements Runnable {
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

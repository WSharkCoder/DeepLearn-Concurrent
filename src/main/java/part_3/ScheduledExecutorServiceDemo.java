package part_3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WSharkCoder
 * @date 2021/05/10
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {

        ScheduledExecutorService  ses = Executors.newScheduledThreadPool(10);
//        //执行1秒 调度2秒
//        ses.scheduleAtFixedRate(() -> {
//            try {
//                Thread.sleep(8000);
//                System.out.println(System.currentTimeMillis() / 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 0, 2, TimeUnit.SECONDS);
        ses.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(8000);
                System.out.println(System.currentTimeMillis() / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

}

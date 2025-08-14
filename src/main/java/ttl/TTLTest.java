package ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.sun.corba.se.spi.ior.IdentifiableFactory;

import java.lang.reflect.Executable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by WSharkCoder on 2025/08/13. <br/>
 *
 * @author WSharkCoder
 * @date 2025/08/13
 */
public class TTLTest {


   public static  TransmittableThreadLocal<SoaExecute> context = new TransmittableThreadLocal<>();
    public static class SoaExecute {
        private Map<String, String> parameters = new HashMap<>();

        public void put(String key, String val) {
            parameters.put(key, val);
        }

    }


    public static void main(String[] args) {
        // 主线程-SoaExecute-巨大
        SoaExecute soaExecute = new SoaExecute();
        for (int i = 0; i < 10000; i++) {
            soaExecute.put("Key" + i, "Val" + i);
        }

        context.set(soaExecute);
        ExecutorService executorService1 = new ThreadPoolExecutor(50, 100, 60L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "TTL-Thread");
            }
        });
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService1);
        for (int i = 0; i < 100; i++) {
            ttlExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    Thread thread = Thread.currentThread();
                    SoaExecute soaExecute1 = context.get();
                    System.out.println("In Thread Group#" + thread.getThreadGroup().getName() + "#Thread#" + thread.getId());
                    System.out.println(executorService1);
                }
            });
        }

        ExecutorService executorService2 = new ThreadPoolExecutor(50, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread");
            }
        });

        for (int i = 0; i < 100; i++) {
            executorService2.submit(() -> {
                Thread thread = Thread.currentThread();
                SoaExecute soaExecute2 = context.get();
                System.out.println("In Thread Group#" + thread.getThreadGroup().getName() + "#Thread#" + thread.getId());
                System.out.println(executorService2);
            });
        }



    }
}

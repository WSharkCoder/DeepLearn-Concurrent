package base.create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author WSharkCoder
 * @date 2021/07/24
 */
public class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        //...
        return 0;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //任务
        MyCallable callable = new MyCallable();
        //任务适配器
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        //多线程驱动
        Thread thread = new Thread(futureTask);
        thread.start();
        //获取结果
        Integer res = futureTask.get();
    }
}

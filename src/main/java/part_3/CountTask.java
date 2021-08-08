package part_3;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author WSharkCoder
 * @date 2021/05/12
 */
public class CountTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10000;
    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        //和数
        long sum = 0;
        //差值小于
        boolean canCompute = (end - start) < THRESHOLD;
        //间距小于临界值
        if (canCompute) {
            //累加计算
            for (long i = start; i < end; i++) {
                sum += i;
            }
        } else {
            //步长
            long step = (end - start) / 100;
            //子任务
            ArrayList<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) lastOne = end;
                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                //提交ForkJoin线程池
                subTask.fork();
            }
            //累计子任务结果
            for (CountTask t : subTasks) {
                sum += t.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0, 200000l);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        long res = result.get();
        System.out.println("sum=" + res);
    }
}

package base.collection;

import java.util.ArrayList;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 17:49
 * @ClassName ArrayListThreadUnSafe
 * ArrayList 非线程安全演示
 * Vector
 */
public class ArrayListThreadUnSafe {
    static ArrayList<Integer> list = new ArrayList<>(10);
    public static class  AddThread implements  Runnable{
        @Override
        public void run() {
            int MAX = 1000;
            for (int cnt = 0; cnt < MAX; cnt++) {
                list.add(cnt);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(list.size());
    }
}

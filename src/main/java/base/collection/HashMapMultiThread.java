package base.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 17:57
 * @ClassName HashMapMultiThread
 *  HashMap非线程安全
 */
public class HashMapMultiThread {
    static Map<String, String> map = new HashMap<>();

    public static class AddThread implements Runnable {
        int start=0;
        public  AddThread(int start){
            this.start=start;
        }
        @Override
        public void run() {
            int MAX=100000;
            int stepLen=2;
            for (int cnt = start; cnt < MAX; cnt = cnt + stepLen) {
                map.put(Integer.toString(cnt), Integer.toBinaryString(cnt));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}

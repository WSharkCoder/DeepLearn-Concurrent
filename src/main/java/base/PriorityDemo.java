package base;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 17:21
 * @ClassName PriorityDemo
 * 线程优先级
 */
public class PriorityDemo {
    final static int MAX= 10000000;
    public static class HighPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if(count>MAX){
                        System.out.println("HighPriority Thread is complete");
                        break;
                    }
                }
            }
        }
    }
    public static class  LowPriority extends  Thread{
        static  int count=0;

        @Override
        public void run() {
            while(true){
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > MAX) {
                        System.out.println("LowPriority Thread is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread high = new HighPriority();
        Thread low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }

}

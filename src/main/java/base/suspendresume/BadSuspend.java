package base.suspendresume;

/**
 * @Author WSharkCoder
 * @Date 2021/4/16 15:22
 * Thread.suspend()/Thread.resume()
 */
public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (u) {
                System.out.println(" in" + getName());
                //线程挂起
                Thread.currentThread().suspend();
                System.out.println();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.resume();
        //t2.resume() 方法在t2线程中断前执行可能导致线程下不停止问题
        t2.resume();
        t1.join();
        t2.join();
    }
}

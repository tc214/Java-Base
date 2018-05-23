###  Thread:join()  
Thread类中的join方法的主要作用就是**同步**，它可以使得线程之间的并行执行变为串行执行。   
join(long millis)参数说明：  
0 OR 无参数：一直等待。  
整数time：休眠time毫秒。  


###  实现原理    
利用wait()来实现的，看代码：  
```java  
public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);//
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);//
                now = System.currentTimeMillis() - base;
            }
        }
    }  
```  


  
###  e1:  
```java  

class ThreadJoinTest extends Thread {
    public ThreadJoinTest(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}


public class JoinTest {
	public static void main(String [] args) throws InterruptedException {
        ThreadJoinTest ta = new ThreadJoinTest("Thread A");
        ThreadJoinTest tb = new ThreadJoinTest("Thread B");
        /**join方法可以在start方法前调用时，并不能起到同步的作用
         */
        ta.start();
        ta.join();
        tb.start();
    }
}  
```  
运行结果：先打印完“Thread A:*”，再打印“Thread B:*”。在主线程中调用了A线程的join()方法时，表示只有当A线程执行完毕时，主线程才能继续执行，  
之后，在主线程中调用了B线程的start()方法，表示B线程开始执行。
  
###  e2:  
```java  



class ThreadJoinTest extends Thread {
    public ThreadJoinTest(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}


public class JoinTest {
	public static void main(String [] args) throws InterruptedException {
        ThreadJoinTest ta = new ThreadJoinTest("Thread A");
        ThreadJoinTest tb = new ThreadJoinTest("Thread B");
        /**join方法可以在start方法前调用时，并不能起到同步的作用
         */
        ta.start();
        ta.join(10);
        tb.start();
    }
}  
```    

运行结果：先打印一部分“Thread A:*”，然后，交替打印“Thread A:*”、“Thread B:*”。A线程先运行，10ms后，B线程才  
开始运行。  











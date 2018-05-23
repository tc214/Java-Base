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
注意：  wait（）的作用是让“当前线程”等待，而这里的“当前线程”是指当前在CPU上运行的线程，所以，虽然调用子线程的wait（）  
方法，但是它是通过“主线程”去调用的，所以休眠的是主线程。  

      
      
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
  
    
###  e3：  
```java  

public class ThreadA extends Thread {
	private ThreadB b;
	
	public ThreadA(ThreadB b) {
		super();
		this.b = b;
	}
	
	@Override 
	public void run() {
		try {
			synchronized (b) {
				System.out.println("begin A ThreadName=" 
			    + Thread.currentThread().getName() + "  "
			    + System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("  end A ThreadName="
						+ Thread.currentThread().getName() + "  "
						+ System.currentTimeMillis());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}  
  
public class ThreadB extends Thread {
	
	@Override 
	public void run() {
		try {
			System.out.println("begin B ThreadName=" 
		    + Thread.currentThread().getName() + "  "
		    + System.currentTimeMillis());
			Thread.sleep(5000);
			System.out.println("  end B ThreadName="
					+ Thread.currentThread().getName() + "  "
					+ System.currentTimeMillis());
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}    

  
public class TestJoin {
	
	public static void main(String[] args) {
		try {
			ThreadB tb = new ThreadB();
			ThreadA ta = new ThreadA(tb);
			ta.start();
			tb.start();
			tb.join(2000);
			System.out.println("   main thread end" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}  
```  
       
### 分析：  
首先，需要了解几个基本事实：  
####    
1.start方法的作用仅仅是请求JVM启动一个线程，而这个线程具体何时开始运行是由操作系统决定的。     
先start的线程未必就比后start的线程先开始运行。  
2.内部锁的调度仅支持非公平调度，在锁的调度过程中允许“插队”。  
3.执行Thread.sleep不会使当前线程释放任何锁。	  
4.wait方法在其返回前会以原子操作的方式释放该方法所属对象的内部锁并使其执行线程暂停。  
  
###  结果  
可能的结果有多种：  
#### 第一种  

```java  
begin B ThreadName=Thread-0  1527091104855
begin A ThreadName=Thread-1  1527091104855
  end A ThreadName=Thread-1  1527091109855
  end B ThreadName=Thread-0  1527091109855
   main thread end1527091109855  
   ```    
#### 第二种  
```java  
begin A ThreadName=Thread-1  1527091792726
begin B ThreadName=Thread-0  1527091792726
  end B ThreadName=Thread-0  1527091797726
  end A ThreadName=Thread-1  1527091797726
   main thread end1527091797726  
   ```  
   
#### 第三种  
```java  
begin B ThreadName=Thread-0  1527091825895
begin A ThreadName=Thread-1  1527091825895
  end B ThreadName=Thread-0  1527091830896
  end A ThreadName=Thread-1  1527091830896
   main thread end1527091830896
  
  ```
  
  #### 更多结果请以实际运行结果为准。











###  Java的对象锁和类锁
多线程的线程同步机制实际上是靠锁的概念来控制的。  

###  为什么需要锁？  
在Java程序运行时环境中，JVM需要对两类线程共享的数据进行协调同步：
1）保存在堆中的实例变量
2）保存在方法区中的类变量

这两类数据是被**所有线程**共享的。
程序不需要协调 保存在Java栈当中的数据。因为这些数据是属于拥有该栈的线程所私有的。    
  
在java虚拟机中，每个对象和类在逻辑上都是和一个监视器相关联的。
对于对象来说，与其相关联的监视器保护着对象的实例变量。  
对于类来说，监视器保护类的类变量。  
为了实现监视器的排他性监视能力，java虚拟机为每一个对象和类都关联一个锁。  
代表任何时候只允许一个线程拥有的特权。  
但是如果线程获取了锁，那么在它释放这个锁之前，就没有其他线程可以获取同样数据的锁了。  
（锁住一个对象就是获取对象相关联的监视器）  
  
 类锁实际上用对象锁来实现。  
 当虚拟机装载一个class文件的时候，它就会创建一个java.lang.Class类的实例。  
 当锁住一个对象的时候，实际上锁住的是那个类的Class对象。  
 java编程人员不需要自己动手加锁，对象锁是java虚拟机内部使用的。  
在java程序中，只需要使用synchronized块或者synchronized方法就可以标志一个监视区域。  
当每次进入一个监视区域时，java 虚拟机都会自动锁上对象或者类。  

###  区别  
类锁对该类的所有对象都能起作用，而对象锁不能。  

### 实例  
实例均来自于《Java多线程编程核心技术》。  

####  synchronized修饰非静态方法  




####  synchronized修饰静态方法  

```java  

public class Task {
	
	public synchronized static void doLongTimeTaskA() {
        System.out.println("name = " + Thread.currentThread().getName() + ", begain");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("name = " + Thread.currentThread().getName() + ", end");
    }

    public synchronized static void doLongTimeTaskB() {
        System.out.println("name = " + Thread.currentThread().getName() + ", begain");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("name = " + Thread.currentThread().getName() + ", end");
    }

    public synchronized void doLongTimeTaskC() {

        System.out.println("name = " + Thread.currentThread().getName() + ", begain");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("name = " + Thread.currentThread().getName() + ", end");

    }
}


class Thread_A extends Thread {

	    private Task mTask;

	    public Thread_A(Task tk){
	        mTask = tk;
	    }

	    public void run() {
	        mTask.doLongTimeTaskA();
	    }
	}

	class Thread_B extends Thread {

	    private Task mTask;

	    public Thread_B(Task tk){
	        mTask = tk;
	    }

	    public void run() {
	        mTask.doLongTimeTaskB();
	    }
	}

	class Thread_C extends Thread {

	    private Task mTask;

	    public Thread_C(Task tk){
	        mTask = tk;
	    }

	    public void run() {
	        mTask.doLongTimeTaskC();
	    }
	}
	
	
public class SynTest {
	
	public static void main(String[] args) {
		Task mTask = new Task();
        Thread_A ta = new Thread_A(mTask);
        Thread_B tb = new Thread_B(mTask);
        Thread_C tc = new Thread_C(mTask);

        ta.setName("A");
        tb.setName("B");
        tc.setName("C");

        ta.start();
        tb.start();
        tc.start();
	}
	
	
}
```    

执行结果可能是：    
name = C, begain
name = A, begain
name = A, end
name = C, end
name = B, begain
name = B, end

解析:由于doLongTimeTaskA和doLongTimeTaskB都是类锁，即同一个锁，所以A和B是按顺序执行，即同步的。而C是对象锁，和A/B不是同一种锁，
所以C和A、B是 异步执行的。（A、B、C代指上面的3种方法）。    
    
####  同步代码块的synchronized(this) 
  
```java  
public void Method() {
    synchronized (this) {
        System.out.println("我是对象锁");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}  
```  



####  synchronized (非this对象)  
##### 锁 是String常量  
```java  
package org.tc.synchronize;

public class MySynchronized extends Thread
{
    private String name;
     
    private String val;
     
    public MySynchronized(String name, String v)
    {
        this.name = name;
        val = v;
    }
     
    public void printVal()
    {
        synchronized (val)
        {
            while (true)
            {
                System.out.println(name + val);
            }
        }
    }
     
    public void run()
    {
        printVal();
    }
     
    public static void main(String args[])
    {
        MySynchronized f1 = new MySynchronized("Foo 1:", "printVal");
        f1.start();
        MySynchronized f2 = new MySynchronized("Foo 2:", "printVal");
        f2.start();
    }
}  
```  
结果：  
```java  
Foo 1:printVal
Foo 1:printVal
Foo 1:printVal
Foo 1:printVal
Foo 1:printVal
Foo 1:printVal
Foo 1:printVal
Foo 1:printVal  
..  
..  
```  

永远也不会打印：Foo 2:printVal。  
  

####  全局变量作为锁  
对一个全局对象或者类加锁时，对该类的所有对象都起作用。  
```java  
public class MySynchronize {
	private static Object lock = new Object();
	
	
	public void printVal(int v) {
        synchronized (lock) {
            while (true) {
                System.out.println(v);
            }
        }
    }
}  

public class MySynchronize {
	private static Object lock = new Object();
	
	
	public void printVal(int v) {
        synchronized (MySynchronize.class) {
            while (true) {
                System.out.println(v);
            }
        }
    }
}  
```  



####  synchronized (类.class)

```java  
public void getInstance() {
    synchronized (SingleTon.class) {
        System.out.println("我是类锁");
        return sIntance;
     }
}  
```  




###   使用锁实现同步       

Java提供了同步代码块的另一种机制，它比synchronized关键字更强大也更加灵活。这种机制基于Lock接口及其实现类（例如：ReentrantLock）
它比synchronized关键字好的地方：
1、提供了更多的功能。tryLock()方法的实现，这个方法试图获取锁，如果锁已经被其他线程占用，它将返回false并继续往下执行代码。
2、Lock接口允许分离读和写操作，允许多个线程读和只有一个写线程。ReentrantReadWriteLock
3、具有更好的性能
一个锁的使用实例：  
```java  
public class PrintQueue {
    private final Lock queueLock = new ReentrantLock(); //声明一把锁，ReentrantLock（可重入的互斥锁）是Lock接口的一个实现

    public void printJob(Object document){
        queueLock.lock();       // 声明同步代码块（临界区）
        
        try {
            Long duration = (long)(Math.random()*10000);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),(duration/1000));
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock(); // 释放锁
        }
    }
}  
```  

  
  
###  使用读写锁实现同步数据访问
锁机制最大的改进之一就是ReadWriteLock接口和他的唯一实现类ReentrantReadWriteLock。  
这个类有两个锁，一个是读操作锁，一个是写操作锁。  
使用读操作锁时可以允许多个线程同时访问，使用写操作锁时只允许一个线程进行。  
在一个线程执行写操作时，其他线程不能够执行读操作。  
在调用写操作锁时，使用一个线程。  
写操作锁的用法：  
```java    
    ReadWriteLock lock = new ReentrantReadWriteLock();
    public void writeOperate(double price1, double price2) {
        lock.writeLock().lock();   //
        this.price1 = price1;
        this.price2 = price2;
        lock.writeLock().unlock(); //
    }  
```     
读操作:  
```java    
    ReadWriteLock lock = new ReentrantReadWriteLock();
    public double getPrice1() {
        lock.readLock().lock();  //
        double value = price1;
        lock.readLock().unlock();//
        return value;
    }
    public double getPrice2() {
        lock.readLock().lock();  //
        double value = price2;
        lock.readLock().unlock();//
        return value;
    }  
```    
####  修改锁的公平性
ReentrantLock和ReetrantReadWriteLock构造函数都含有一个布尔参数fair。默认fair为false，即非公平模式。
公平模式：当有很多线程在等待锁时，锁将选择一个等待时间最长的线程进入临界区。
非公平模式：当有很多线程在等待锁时，锁将随机选择一个等待区（就绪状态）的线程进入临界区。
这两种模式只适用于lock()和unlock()方法。而Lock接口的tryLock()方法没有将线程置于休眠，fair属性并不影响这个方法。  
  
  
####   在锁中使用多条件（Multri Condition）  
锁条件可以和synchronized关键字声明的临界区的方法(wait(),notify(),notifyAll())作类比。  
Condition有对应方法: await, signal, signalAll
锁条件通过Conditon接口声明。  
Condition提供了挂起线程和唤醒线程的机制。  

使用方法：  
```java  
    private Condition lines;
    private Condition space;
     
    public void insert(String line) {
        lock.lock();   //
        try {
            while (buffer.size() == maxSize) {
                space.await(); //
            }
            buffer.offer(line);
            System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread()
                    .getName(), buffer.size());
            lines.signalAll();  // lines
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //
        }
    }  
    
    public String get() {
        String line = null;
        lock.lock();      //    
        try {
            while ((buffer.size() == 0) &&(hasPendingLines())) {
                lines.await();  //
            }
            
            if (hasPendingLines()) {
                line = buffer.poll();
                System.out.printf("%s: Line Readed: %d\n",Thread.currentThread().getName(),buffer.size());
                space.signalAll();  // space
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //
        }
        return line;
    }  
 ```  
 

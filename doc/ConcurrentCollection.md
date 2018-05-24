### 并发集合 
java.util.concurrent包下的集合类  
本文简要介绍Java并发编程方面常用的类和集合，并介绍下其实现原理。  

###   AtomicInteger  
可以用原子方式更新int值。类 AtomicBoolean、AtomicInteger、AtomicLong 和 AtomicReference 的实例各自提供对相应类型单个变量的访问和更新。  
基本的原理都是使用CAS操作：boolean compareAndSet(expectedValue, updateValue);  
如果此方法（在不同的类间参数类型也不同）当前保持expectedValue，则以原子方式将变量设置为updateValue，并在成功时报告true。

循环CAS，参考AtomicInteger中的实现：
public final int getAndIncrement() {
        for (;;) {
            int current = get();
            int next = current + 1;
            if (compareAndSet(current, next))
                return current;
        }
    }
 
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }  
####  ABA问题  
因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时  
会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一， 
那么A－B－A 就会变成1A-2B－3A。
从Java1.5开始JDK的atomic包里提供了一个类AtomicStampedReference来解决ABA问题。这个类的compareAndSet方法作用是首先检查当前引用是否等于预期  
引用，并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。  
public boolean compareAndSet(
        V      expectedReference,//预期引用
        V      newReference,//更新后的引用
        int    expectedStamp, //预期标志
        int    newStamp) //更新后的标志  
        

###  ArrayBlockingQueue  




###  LinkedBlockingQueue  


###  ConcurrentLinkedQueue  


###  ConcurrentHashMap   

###  CopyOnWriteArrayList  


###  AbstractQueuedSynchronizer   


###  ThreadPoolExecutor   








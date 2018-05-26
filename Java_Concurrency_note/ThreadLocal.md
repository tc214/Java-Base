###   线程封闭-ThreadLocal  
ThreadLocal<T>在每一个线程上创建一个T的副本，副本之间彼此独立，互不影响。
这些特定于线程的T保存在各自的线程对象中，当线程终止时这些值会作为垃圾回收。  


```java
package org.tc.threadlocal;

	public class Task implements Runnable {  
	    private static ThreadLocal<Double> value = new ThreadLocal<Double>(){  
	        @Override  
	        protected Double initialValue() {  
	            return Math.random();  
	        }  
	    };  
	      
	    public void run() {  
	        System.out.println(value.get());  
	    }  
	      
	    public static void main(String[] args) {  
	        Task t = new Task();  
	        Thread td1 = new Thread(t);  
	        Thread td2 = new Thread(t);  
	        td1.start();  
	        td2.start();
	        System.out.println(value.get());  
	    }  
}  
```  

分析：线程td1和td2的value值不一样。  

运行结果：  
0.30460124180316406
0.0018258060023527145  


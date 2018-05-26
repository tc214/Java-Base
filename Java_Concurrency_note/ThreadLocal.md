###   �̷߳��-ThreadLocal  
ThreadLocal<T>��ÿһ���߳��ϴ���һ��T�ĸ���������֮��˴˶���������Ӱ�졣
��Щ�ض����̵߳�T�����ڸ��Ե��̶߳����У����߳���ֹʱ��Щֵ����Ϊ�������ա�  


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

�������߳�td1��td2��valueֵ��һ����  

���н����  
0.30460124180316406
0.0018258060023527145  


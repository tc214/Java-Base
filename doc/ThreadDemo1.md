### java线程:wait(),notify(),notifyAll()  
&ensp;&ensp;这三个方法都是java.lang.Object的方法，用于协调多个线程对共享数据的存取，wait和notify方法是Java同步机制  
中重要的组成部分，这些方法**只有在Synchronized方法或Synchronized代码块中才能使用**，
否者就会报java.lang.IllegalMonitorStateExceprion异常;  
当Synchronized方法或者Synchronized代码块中的wait()方法被调用时，当前线程将被中断运行，并且放弃该对象锁,当例外  
的线程执行了某个对象notify()方法后,会唤醒在此对象等待池中的某个线程，使之成为可运行的(就绪状态)线程。notifyAll()  
方法会唤醒所有等待这个对象的线程成为可运行的线程。  

###  经典案例：生产者-消费者  
问题描述：生产者将产品交给店员,，而消费者从店员处取走产品，店员一次只能持有固定数量的产品,如果生产者生产了过多的产品，
店员会叫生产者等一下;若果店中的有空位放产品了在通知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品
再通知生产者来取走产品。
这里可能出现的问题有以下两个：
 生产者比消费者快时，消费者会漏掉一些数据没有取到
 消费者比生产者快时，消费者会取相同的数据  
  
 代码：   
  
```java      
public class ProductTest {

	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		Producter producter = new Producter(clerk);
		Consumer consumer = new Consumer(clerk);
		Thread thread1 = new Thread(producter);
		thread1.setName("生产者");
		Thread thread2 = new Thread(consumer);
		thread2.setName("消费者");
		thread1.start();
		thread2.start();
	}
	
	public static class Clerk {
		private volatile int productNums = 0; 
		
		public synchronized void addProduct() {
			if (productNums >= 20) {
				try {  
				    wait(); // 当生产者生产的产品超过20个后，不再生产，等待消费者来买走产品   
				   
				} catch (InterruptedException e) {  
				    e.printStackTrace();  
				}  
				
			} else {
				productNums = productNums + 1;  
				System.out.println(Thread.currentThread().getName() + ":" + "添加了第"
				    + productNums + "个产品");  
				notifyAll();
			}
		}
		
		public synchronized void getProduct() {
			if (productNums <= 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(Thread.currentThread().getName() + ":"
						+ "买走了第" + productNums + "个产品");
				productNums = productNums - 1; // 消费者每次买走一个产品
				notifyAll();
			}
		}
	}
	
	public static class Producter implements Runnable {
		private Clerk clerk;
		
		public Producter(Clerk clerk) {
			this.clerk = clerk;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep((int) (Math.random() * 10) * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				clerk.addProduct();
			}
		}
	}
	
	public static class Consumer implements Runnable {
		
		private Clerk clerk;

        	public Consumer(Clerk clerk) {
           	 this.clerk = clerk;
        	}

		@Override
		public void run() {

		    while (true) {
			try {
			    // 不知道什么时候消费者回来添加产品，所以用一个随机时间来让线程休眠，模拟消费者来访的不定时
			    Thread.sleep((int) (Math.random() * 10) * 100);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			clerk.getProduct();
		    }
		}
         }
}

```    
运行结果：  
```java  
生产者:添加了第1个产品
生产者:添加了第2个产品
生产者:添加了第3个产品
消费者:买走了第3个产品
消费者:买走了第2个产品
生产者:添加了第2个产品
消费者:买走了第2个产品
生产者:添加了第2个产品
生产者:添加了第3个产品
消费者:买走了第3个产品
生产者:添加了第3个产品
消费者:买走了第3个产品
生产者:添加了第3个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
消费者:买走了第3个产品
生产者:添加了第3个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
消费者:买走了第4个产品
生产者:添加了第4个产品
生产者:添加了第5个产品

 
...
...
...

```    
只要不主动停止，程序会一直正常运行下去。  
上述代码中，notifyAll()可以全部改为notify()，两者作用是一样的。
  
    
    
###   建议       
优先使用notifyAll()。  
始终应该使用wait循环模式来调用wait()。  
没有理由在新代码中使用wait和

###               
   

 

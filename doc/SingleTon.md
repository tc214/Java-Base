
###  单例模式 
####  概述
在一个应用中，一个类只有一个实例对象，这种不能自由构造对象的使用方式，就是单例模式。  
####  使用场景   
需要避免创建多个对象消耗过多的资源，如：访问IO,数据库操作；  
某种类型的对象只应该且只有一个；  
需要频繁的进行创建和销毁的对象；  
工具类对象；  
创建对象时耗时过多或耗费资源过多，但又经常用到的对象；


#### 优点
(1) 由于单例模式在内存中只有一个实例，减少内存开支，特别是一个对象需要频繁地创建销毁时，而且创建或销毁时性能又无法优化,单例模式就非常明显了。  

(2) 由于单例模式只生成一个实例，所以，减少系统的性能开销，当一个对象产生需要比较多的资源时，如读取配置，产生其他依赖对象时，则可以通过在应用  
启动时直接产生一个单例对象，然后永久驻留内存的方式来解决。  
(3) 单例模式可以避免对资源的多重占用，例如一个写文件操作，由于只有一个实例存在内存中，避免对同一个资源文件的同时写操作。   

(4) 单例模式可以在系统设置全局的访问点，优化和共享资源访问，例如，可以设计一个单例类，负责所有数据表的映射处理。  


####   缺点
(1) 单例模式一般没有接口，扩展很困难，若要扩展，除了修改代码基本上没有第二种途径可以实现。  
(2) 单例对象如果持有Context，那么很容易引发内存泄漏，此时需要注意传递给单例对象的Context最好是Application Context。  
(3) 高并发下，会影响性能。  



####  实现单例模式需要注意：  
1、构造函数私有，不对外开放；  
2、通过一个静态方法或枚举放回单例类对象；  
3、确保单例类的对象只应该且只有一个，特别是在多线程环境下；  
4、确保单例类对象在反序列化时不会重新构造对象。  

####  单例模式的实现方式  
#####  饿汉模式  （可用，不推荐）
声明一个静态对象，并在声明时就已经初始化；  
```java  
public class SingleTon {  
    private static final SingleTon sInstance = new SingleTon();
    
    private SingleTon(){}  
    
    public static SingleTon getInstance() {  
        return sInstance;
    }  
    .... 
}
```  
静态代码块实现：  （可用，不推荐）
```java  
public class Singleton {
    
    private static Singleton instance;
    
    static {
        instance = new Singleton();
    }
    
    private Singleton() {}
    
    public Singleton getInstance() {
        return instance;
    }
}  
```  
优点：线程安全；  
缺点：声明时就初始化，消耗性能；反序列化可能会重新构造对象。  
            
            
         
#####    懒汉模式  
声明一个静态对象，并在用户第一次调用getInstance时进行初始化。    
######  懒汉一（不可用！线程不安全）  
```java  
public class SingleTon {  
private static SingleTon sInstance;  
   
   private SingleTon() {}  
   public static SingleTon getInstance() {   
       if (sInstance == null) {  
           sInstance = new SingleTon();  
       }
       return sInstance; 
   }  
   ....  
}
```   
######  懒汉二（可用，不推荐）
```java  
public class SingleTon {  
private static SingleTon sInstance;  
   
   private SingleTon() {}  
   public static synchronized SingleTon getInstance() {   
       if (sInstance == null) {  
           sInstance = new SingleTon();  
       }
       return sInstance; 
   }  
   ...  
}  
```  

优点：在使用时才被初始化，节约资源；  
缺点：每次使用都需要同步，会消耗不必要的性能；反序列化问题。  

#####   Double check Lock - 双检锁  （推荐用）  
```java  

public class SingleTon {  
    private volatile static SingleTon sInstance;  
      
    private SingleTon() {}  
    public static SingleTon getInstance() {  
       if (sInstance == null) {  
           synchronized(SingleTon.class) {  
               if (sInstance == null) {  
                   sInstance = new SingleTon();  
               }  
           }  
       }  
        
    }  
    ....  
    }
```  
  优点：线程安全；资源利用率高；第一次执行时才实例化，效率高；第一次实例化后，可以避免不必要的同步。  
  缺点：第一次加载反应稍慢，高并发场景下有一定的缺陷；反序列化问题。    
  
  

#####  解决反序列化问题  

在类里加入如下方法：  

```java   
private Object readResolve() {
       return sInstance;
    }    
```  

     
#####  静态内部类 单例模式-Initialization on Demand Holder  （推荐用）
```java  
public class SingleTon {  
    private SingleTon() {}    
    
    public static SingleTon getInstance() {  
       return SingletoHolder.sInstance;
       }  
       
    private static class SingletoHolder() {  
       private static final SingleTon sInstance = new SingleTon();  
      }

...  
}  
```    

优点：线程安全；延迟了单例初始化，节约资源；避免同步开销；  
缺点：反序列化问题。  

#####   枚举单例    （推荐用）
 ``` java  
 public enum SingletonEnum {  
    INSTANCE;  
 }  
 ```   
优点：写法简单；线程安全；在任何情况下都只有一个实例。  
缺点：只适用于JDK1.5后。

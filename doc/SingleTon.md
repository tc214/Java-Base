
###  单例模式 
####  概述
在一个应用中，一个类只有一个实例对象，这种不能自由构造对象的使用方式，就是单例模式。  
####  使用场景   
需要避免创建多个对象消耗过多的资源，如：访问IO,数据库操作；  
某种类型的对象只应该且只有一个；  

####  实现单例模式需要注意：  
1、构造函数私有，不对外开放；  
2、通过一个静态方法或枚举放回单例类对象；  
3、确保单例类的对象只应该且只有一个，特别是在多线程环境下；  
4、确保单例类对象在反序列化时不会重新构造对象。  

####  单例模式的实现方式  
#####  饿汉模式  
声明一个静态对象，并在声明时就已经初始化；  
```java  
public class SingleTon {  
    private static final SingleTon sInstance = new SingleTon();
    
    private SingleTon(){}  
    
    public static SingleTon getInstance() {  
        return sInstance;
    }  
    ...  
}
```  
优点：线程安全；  
缺点：声明时就初始化，消耗性能；反序列化可能会重新构造对象。  

#####  懒汉模式  
声明一个静态对象，并在用户第一次调用getInstance时进行初始化：  
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
优点：
#####  Double check Lock  双重判空  
```java  
public class SingleTon {  
    private static SingleTon sInstance;  
      
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
    ...  
    }
  ```  
  



###  代理   
提供了一种对目标对象的访问方式：通过代理对象来访问目标对象。  
在我们使用继承时，基类的公共方法全部暴露出来了，那有没有一种方式既利用继承的好处又  
不暴露基类的方法？有，就是代理。  
分类： 
* 静态代理  
* 动态代理

####   静态代理    
#####  定义   
静态代理是在编译时就将接口、目标类、代理类等初始化工作全部手动完成。  

#####  实现
静态代理的实现比较简单，代理类通过实现与目标对象相同的接口，并在类中维护一个代理对象。通过构造器塞入目标对象，赋值给代理对象，  
进而执行代理对象实现的接口方法，并实现“Start transaction...”，“Commit transaction...”等所需的业务功能。  

实例：  
```java  
public interface IUserDao {
	void save();
}  
  
/*
 * target object
 */
public class UserDao implements IUserDao{

	public void save() {
		System.out.println("---Saved data---");
	}
}  
  
/* proxy object */

public class UserDaoProxy implements IUserDao {
    
	private IUserDao target;
    
    public UserDaoProxy(IUserDao target) {
    	this.target = target;
    }
    
    public void save() {
    	System.out.println("Start transaction...");
    	target.save();
    	System.out.println("Commit transaction...");
    }
}  

public class TestProxy {
	
	public static void main(String[] args) {
		UserDao target = new UserDao();
    UserDao userDao = new UserDao();
		UserDaoProxy proxy = new UserDaoProxy(target);//target可以是任意实现了IUserDao的类对象
		userDao.save(); //不使用代理
		proxy.save();   //使用代理
	}
}  
```  

运行结果：  
---Saved data---  

Start transaction...  

---Saved data---  

Commit transaction...  



#####   静态代理总结   
1.优点：在不修改目标对象的功能前提下，对目标功能进行扩展;
2.缺点:
因为代理对象需要与目标对象实现一样的接口，所以会有很多代理类，类太多，难于管理，  
同时，一旦接口增加新的方法，目标对象与代理对象都要维护。
如何解决静态代理的缺点呢?  
答案是可以使用动态代理。
  


  
  
####  动态代理  

#####  定义  
动态代理是指**动态**的在内存中构建代理对象(需要指定目标对象所实现的接口类型)，即利用JDK的API生成指定接口的对象，  
也称之为JDK代理或者接口代理。  

#####  实现  
JDK中生成代理对象的API：  
代理类所在包:java.lang.reflect.Proxy
JDK实现代理只需要使用newProxyInstance方法，但是该方法需要接收三个参数，完整的写法是:  
```java   
static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)  
```
注意:该方法是在Proxy类中是静态方法，其三个参数依次为:  
* ClassLoader loader: 指定当前目标对象使用**类加载器，获取加载器的方法是固定的  

* Class<?>[] interfaces: 目标对象实现的**接口的类型**，使用泛型方式确认类型  

* InvocationHandler h: **事件处理**，执行目标对象的方法时，会触发事件处理器的方法，会把当前执行目标对象的方法作为参数传入  

实例：  
```java  
package org.tc.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
	 //维护一个目标对象
    private Object target;
    
    public ProxyFactory(Object target){
        this.target = target;
    }

   //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					  System.out.println("开始事务");
//                  //执行目标对象方法
	                  Object returnValue = method.invoke(target, args);
	                  System.out.println("提交事务");
	                  return returnValue;
				}
            }
        );
    }
}

public class TestDProxy {
	public static void main(String[] args) {
        // 目标对象
        IUserDao target = new UserDao();
        // 【原始的类型 class org.tc.proxy.dynamic.UserDao】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy.save();
    }
}  
```  
运行结果：  
class org.tc.proxy.dynamic.UserDao  

class com.sun.proxy.$Proxy0  

开始事务  

---Saved data---  

提交事务  

  
** 增加一个方法：  
```java  
public interface IUserDao {
	void save();
	void del();
}

public class UserDao implements IUserDao{

	public void save() {
		System.out.println("---Saved data---");
	}

	@Override
	public void del() {
		System.out.println("---del data---");
	}
}  
  
package org.tc.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
	 //维护一个目标对象
    private Object target;
    
    public ProxyFactory(Object target){
        this.target = target;
    }

   //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if (method.getName().equalsIgnoreCase("save")) {
						System.out.println("开始事务1");
	                      //执行目标对象方法
		                  Object returnValue = method.invoke(target, args);
		                  System.out.println("提交事务1");
		                  return returnValue;
					} else if (method.getName().equalsIgnoreCase("del")) {
						System.out.println("开始事务2");
	                      //执行目标对象方法
		                  Object returnValue = method.invoke(target, args);
		                  System.out.println("提交事务2");
		                  return returnValue;
					}
					return null;  
				}
            }
        );
    }
}
  

public class TestDProxy {
	public static void main(String[] args) {
        // 目标对象
        IUserDao target = new UserDao();
        // 【原始的类型 class cn.itcast.b_dynamic.UserDao】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy.save();
        proxy.del();
    }
}  
```  
运行结果：  
class org.tc.proxy.dynamic.UserDao  

class com.sun.proxy.$Proxy0  

开始事务1  

---Saved data---  

提交事务1  

开始事务2  

---del data---  

提交事务2


#####  动态代理的总结
* 优点：代理对象无需实现接口，免去了编写很多代理类的烦恼，同时接口增加方法也无需再维护代理对象，  
只需在事件处理器中添加对方法的判断即可。
* 缺点：代理对象不需要实现接口，但是目标对象一定要实现接口，否则无法使用JDK动态代理。  

####   Cglib代理
#####  定义   


#####  实现    


#####  总结  


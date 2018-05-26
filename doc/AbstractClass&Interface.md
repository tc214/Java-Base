###  What Why  how 

###  什么是抽象方法？  
被关键字abstract修饰，仅仅有声明没有实现的方法，格式：public abstract void function();   

###  什么是抽象类？  
&ensp;&ensp;被关键字abstract修饰的类叫做**抽象类**。  
抽象类是对类的抽象。  
抽象类同普通类一样，可以有成员变量和成员方法。  
抽象类中可以包含抽象方法，也可以不包含。  
包含抽象方法的类必须被abstract修饰，一定是抽象类。     
抽象类中的抽象方法必须由public或protected修饰，保证对子类可见，默认是public。  
子类必须复写抽象父类的抽象方法。  


###  抽象类能否实例化？    
不能使用new创建抽象类对象，但是可以通过子类继承的方式来实例化抽象类，看代码：  
```java  
package org.tc.cls;

abstract class Abstractcls {
	private String str;  
    
    protected Abstractcls(String a){  
        System.out.println("父类已经实例化");  
        this.str = a;  
        System.out.println(str);  
    }  
      
    public abstract void play();  
}


public class AbstractClassChild extends Abstractcls {
	public AbstractClassChild(String c){  
		super(c);  
		System.out.println("子类已经被实例化");  
	}  

	@Override
	public void play() {
		System.out.println("我实现了父类的方法");  
	}
      
    public static void main(String[] args){  
    	Abstractcls c = new AbstractClassChild("c");  
    }  
}  
```  
   
###  抽象类的作用  
抽象类是用来被继承的，如果定义了一个抽象类而不继承它，那么这个抽象类没有任何意义。
如果一个类继承了抽象类，那这个类必须实现抽象类中的抽象方法，否则，子类也必须定义成抽象类。  
抽象类体现了面向对象的特征：继承，拥有共同属性和特征的类继承父类，当父类增加新的方法时，子类将  
全部拥有，而不必依次增加。实现了代码的复用。


###  什么是接口？    
接口是对行为的抽象。  
定义：  
[public] interface InterfaceName {}  

###  接口的特征  
接口可以变量和方法；  
####  变量   
接口中的变量会被隐式地指定为public static final变量（并且只能是public、static、final变量，用private修饰会报编译错误），  
可以通过接口名直接访问，也可以通过ImplementClass.name访问。
  
####  方法  
只能被public修饰，且不能有实现。
    
接口弥补了Java只能单继承的缺陷。
如果一个非抽象类实现了接口，则必须实现该接口中的所有抽象方法。  
对于遵循某个接口的抽象类，可以不实现该接口中的抽象方法。  
不能创建对象，但可以创建接口变量。  

### 接口的作用  
弥补单继承的不足；  
  
###  抽象类和接口的区别   
抽象类中可以有方法的实现，Java8之前，接口中不能有方法的实现。    
抽象类中可以有构造器，接口没有。  
一个类只能继承一个抽象类，却可以实现多个接口。  
抽象类不能实例化对象，接口不能创建接口对象。  
抽象类是对类抽象，而接口是对行为的抽象。  
抽象类所体现的是一种继承关系，考虑的是子类与父类本质“是不是”同一类的关系；
而接口并不要求实现的类与接口是同一本质，它们之间只存在“有没有这个能力”的关系。  
抽象类是自下而上的设计，在子类中重复出现的工作，抽象到抽象类中；
接口是自上而下，定义行为和规范。  
  
### 如何选择？    
参考以下几点：
```java  

若使用接口，我们可以同时获得抽象类以及接口的好处。
所以假如想创建的基类没有任何方法定义或者成员变量，那么无论如何都愿意使用接口，而不要选择抽象类。
如果事先知道某种东西会成为基础类，那么第一个选择就是把它变成一个接口。
只有在必须使用方法定义或者成员变量的时候，才应考虑采用抽象类。  
实现接口可以使一个类向上转型至多个基础类。  
```  
看一个例子：  
门和警报的例子：门都有open( )和close( )两个动作，此时我们可以定义通过抽象类和接口来定义这个抽象概念：

```java  
 abstract class Door {
    public abstract void open();
    public abstract void close();
}  
或者  
interface Door {
    public abstract void open();
    public abstract void close();
}  
```  
但是现在如果我们需要门具有报警alarm( )的功能，那么该如何实现？下面提供两种思路：

1）将这三个功能都放在抽象类里面，但是这样一来所有继承于这个抽象类的子类都具备了报警功能，但是有的门并不一定具备报警功能；

2）将这三个功能都放在接口里面，需要用到报警功能的类就需要实现这个接口中的open( )和close( )，也许这个类根本就不具备open( )和  
close( )这两个功能，比如火灾报警器。

从这里可以看出， Door的open() 、close()和alarm()根本就属于两个不同范畴内的行为，open()和close()属于门本身固有的行为特性，  
而alarm()属于延伸的附加行为。因此最好的解决办法是单独将报警设计为一个接口，包含alarm()行为,Door设计为单独的一个抽象类，  
包含open和close两种行为。再设计一个报警门继承Door类和实现Alarm接口。  
  
```java  
interface Alram {
    void alarm();
}
 
abstract class Door {
    void open();
    void close();
}
 
class AlarmDoor extends Door implements Alarm {
    void oepn() {
      //....
    }
    void close() {
      //....
    }
    void alarm() {
      //....
    }
}  
  
```  


###  多态  
多态指的是编译期只知道是个人，具体是什么样的人需要在运行时能确定，同样的参数有可能会有不同的实现。  
实现多态主要有以下三种方式:

接口实现；
继承父类重写方法；
同一类中进行方法重载；  

不论哪种实现方式，调用者持有的都是基类，不同的实现在他看来都是基类，使用时也当基类用。
这就是“向上转型”，即：子类在被调用过程中由继承关系的下方转变成上面的角色。  

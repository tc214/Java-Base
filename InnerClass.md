
#### 成员内部类

##### /*要求：使用已知的变量代替“？”，在控制台输出10，20，30。*/

```java
public class OutClass {// 外部类
    int num = 30;
	
	class InnerClass {// 成员内部类
		int num = 20;
		
		public void show() {
			int num = 10;
			System.out.println(?);
			System.out.println(?);
			System.out.println(?);
		}
	}
	
	public static void main(String[] args) {
	    // 创建外部类对象
		OutClass outer = new OutClass();
		// 创建内部类对象
		OutClass.InnerClass inner = outer.new InnerClass();
		// 内部类对象调用自己的方法
		inner.show();
	}
}
```

#####
解析：
对于第一空和第二空，相信大家都很容易答对，对于第三空，能答对者恐怕不多。
show()方法中的局部变量num会隐藏InnerClass中的成员变量num，InnerClass中的成员变量num会隐藏OutClass中的成员变量num。
第一空：其实就是输出局部变量num；
第二空：实际需要输出的是内部类的成员变量num，所以需要找到内部类InnerClass对象的引用InnerClass.this，为什么是this？因为show()方法在InnerClass这个类里面,调用show方法的是InnerClass对象；
第三空：实际需要输出的是外部类OutClass的成员变量num，所以需要找到外部类对象的引用OutClass.this，这种表示方法是Java的规定。

注：非静态内部类持有外部类对象的引用 OutClass.this


答案：
num
this.num 或InnerClass.this.num
OutClass.this.num

在工程的bin目录下，我们会发现有OutClass.class和OutClass$InnerClass.class两个文件，用AS打开OutClass$InnerClass.class：
```java
class OutClass$InnerClass {
    int num;

    OutClass$InnerClass(OutClass var1) {
        this.this$0 = var1;
        this.num = 20;
    }

    public void show() {
        int num = 10;
        System.out.println(num);
        System.out.println(this.num);
        System.out.println(this.this$0.num);
    }
}
```
在这个不完整的反编译字节码中，我们可以看到，编译器会为内部类创建一个叫做 this$0 的对象，它是外部类的引用。


#####第二题：补齐代码 ，要求在控制台输出”HelloWorld“
```java
        interface Inter { 
            void show(); 
        }
        class Outer { 
            //补齐代码 
        }
        class OuterDemo {
            public static void main(String[] args) {
                  Outer.method().show();
              }
        }
```

######
解析：
很明显，method是一个静态方法，并且不需要返回一个可以调用show方法对象的引用，
show方法在接口Inter中定义，所以，Outer需要实现接口Inter才能调用show方法，然而，不能改变Outer的接口关系，
只能通过内部类来实现，让内部类实现接口Inter，然后创建内部类对象调用show方法，需要打印“HelloWorld"，则需要在内部类
的method方法中输出“HelloWorld"。
```java
class InnerClass implements Inter {
    @Override
	public void show() {
	    System.out.println("HelloWorld");
	}
}
	
public Outer method() {
    Outer.InnerClass innerClass = new Outer().new InnerClass();
    return innerClass;
}
```

也可以使用局部匿名内部类：
```java
public Outer method() {
    return new Inter(){
　　　　void show(){
　　　　System.out.println("HelloWorld");
　　　　}
　　};
}
```

#####
```
public class BwfOuterClass {

      private int x = 1;
      private int y = 2;

      private class BwfInnerClass{

           private int x = 3;

           public void print(){

                 System.out.println("x+y="+(x+y) );
           }
      }

      public static void main(String[] args) {

           new BwfOuterClass().new BwfInnerClass().print();
      }

}
```

解析：x是内部类的成员变量，在内部类中可以调用外部类的成员变量y。
答案：x+y=5
		
#####
在内部类中，直接使用变量名，会按照从方法中的局部变量、到内部类的变量、到外部类的变量的顺序访问
也就是说，如果在外部类、内部类、方法中有重名的变量/方法，编译器会把方法中直接访问变量的名称修改为方法的名称
如果想在方法中强制访问内部类的成员变量/方法，可以使用 this.i，这里的 this 表示当前的内部类对象
如果想在方法中强制访问外部类的成员变量/方法，可以使用 OutClass.this.i，这里的 OutClass.this 表示当前外部类对象
成员内部类就如同外部类的成员一样，同样可以被public、protected、private、缺省（default）这些修饰符来修饰。

但是有一个限制是：成员内部类不能创建静态变量/方法。如果我们尝试创建，编译器会直接报错：
The field i cannot be declared static in a non-static inner type, unless initialized with a constant expression
The method test cannot be declared static; static methods can only be declared in a static or top level type

为什么会这样呢？

Stackoverflow 有一个回答很好：

“if you’re going to have a static method, the whole inner class has to be static. Without doing that, you couldn’t guarantee that the inner class existed when you attempted to call the static method. ”

我们知道要使用一个类的静态成员，需要先把这个类加载到虚拟机中，而成员内部类是需要由外部类对象 new 一个实例才可以使用，这就无法做到静态成员的要求。


#####
在Java中，可以将一个类定义在另一个类里面或者一个方法里面，这样的类称为内部类。
广泛意义上的内部类一般来说包括这三种：成员内部类、局部内部类、匿名内部类

#### 成员内部类的使用场景
#####
普通内部类可以访问外部类的所有成员和方法，因此当类 A 需要使用类 B ，同时 B 需要访问 A 的成员/方法时，可以将 B 作为 A 的成员内部类。

比如安卓开发中常见的在一个 Activity 中有一个 ListView，我们需要创建一个特定业务的 adapter，在这个 adapter 中需要传入数据，你可以另建一个类，但如果只有当前类需要使用到，完全可以将它创建在 Activity 中。


####
匿名内部类
匿名内部类的特点： 
1.一个类用于继承其他类或是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是覆盖。 
3.类名没有意义，也就是不需要使用到。

匿名内部类也就是没有名字的内部类正因为没有名字，所以匿名内部类只能使用一次，它通常用来简化代码编写，
但使用匿名内部类还有个前提条件：
必须继承一个父类或实现一个接口，但最多只能继承一个父类，或实现一个接口。
关于匿名内部类还有如下两条规则：
    1）匿名内部类不能是抽象类，因为系统在创建匿名内部类的时候，会立即创建内部类的对象。因此不允许将匿名内部类定义成抽象类。
    2）匿名内部类不能定义构造器（构造方法），因为匿名内部类没有类名，所以无法定义构造器，但匿名内部类可以定义实例初始化块。

先看一个例子，体会一下匿名内部类的用法：
```java
abstract class Parent {
	public abstract  void dos();
}

public class AnonymousClass {

	public static void main(String[] args) {
		Parent parent = new Parent() {

			@Override
			public void dos() {
			   System.out.println("hello");
			}
		};
		parent.dos();
	}
}
```

运行结果：hello
可以看到，我们直接将抽象类Parent中的方法在大括号中实现了，这样便可以省略一个类的书写。
注意：抽象类是不能实例化的，不要以为new Parent()是创建Parent的实例，其实new的是Parent的实现类，这个实现类是个匿名内部类。

匿名内部类还能用于接口上：

```java
interface Inters {
	void dos();
}

public class AnonymousClassDemo {

	public static void main(String [] args) {
		Inters inter = new Inters() {

			public void get() {
				
			}

			@Override
			public void dos() {
				
			}
			
		};
		inter.dos();
//		inter.get();
	}
}
```

这里inter.dos()是可以正常调用的，但inter.dos()不能调用，为什么呢？注意Inters inter = new Inters() 创建的是Inters的对象，而非匿名内部类的对象。但继承父类的方法是可以正常调用的，本例子中，匿名内部类实现了接口Inters的dos方法，因此可以借助Inters的对象去调用。


#### 内部类调用的外部变量必须是final修饰的？
因为生命周期的原因。方法中的局部变量，方法结束后这个变量就要释放掉，final保证这个变量始终指向一个对象。首先，内部类和外部类其实是处于同一个级别，内部类不会因为定义在方法中就会随着方法的执行完毕而跟随者被销毁。问题就来了，如果外部类的方法中的变量不定义final，那么当外部类方法执行完毕的时候，这个局部变量肯定也就被GC了，然而内部类的某个方法还没有执行完，这个时候他所引用的外部变量已经找不到了。如果定义为final，java会将这个变量复制一份作为成员变量内置于内部类中，这样的话，由于final所修饰的值始终无法改变，所以这个变量所指向的内存区域就不会变。 
为了解决：局部变量的生命周期与局部内部类的对象的生命周期的不一致性问题
```java
public class OuterDemoFinal {
    public void display(final String name, int age) {//final
        class InnerClassFinal {
            void display() {
                System.out.println(name);
            }
        }
        new InnerClassFinal().display();
    }
    
    public static void main(String[] args) {
    	new OuterDemoFinal().display("Hello world", 19);
    }
}
```

Java8之前不加会报错：
Cannot refer to the non-final local variable name defined in an enclosing scope
Java8后，已经不需要加final了：如果局部变量被匿名内部类访问，那么该局部变量相当于自动使用了final修饰


#### 匿名内部类初始化
我们一般都是利用构造器来完成某个实例的初始化工作的，但是匿名内部类是没有构造器的！那怎么来初始化匿名内部类呢？使用构造代码块！
利用构造代码块能够达到为匿名内部类创建一个构造器的效果。

```java
public class AnonymousInit {

	abstract class InnerClass {
		public abstract String getName();
		public abstract int getAge();
	}
	
    public InnerClass getInnerClass(final int _age, final String _name) {
        return new InnerClass() {
            int age;
            String name;
            //构造代码块完成初始化工作
            {
                if(0 < _age && _age < 200){
                    age = _age;
                    name = _name;
                }
            }
            public String getName() {
                return name;
            }
            
            public int getAge() {
                return age;
            }
        };
    }
    
    public static void main(String[] args) {
    	AnonymousInit out = new AnonymousInit();
        
    	InnerClass inner_1 = out.getInnerClass(201, "tom");
        System.out.println(inner_1.getName());
        
        InnerClass inner_2 = out.getInnerClass(20, "tom");
        System.out.println(inner_2.getName());
    }
}
```
输出：
null
tom


解析：
匿名内部类如果有成员变量且没有初始化，则需要在静态初始块中进行初始化，否则无法使用内部类的成员变量。

#### 匿名内部类的使用场景
######
Android 开发中设置一个按钮的点击事件很简单，直接 new 一个 View.OnClickListener 然后实现方法即可：
```java
mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //...
            }
        });
```
之所以这样写，是因为我们不需要持有这个 new View.OnClickListener 的引用，只要创建了对象即可。
所以使用场景可以是：一个方法的返回值是接口，然后根据不同参数返回不同的实现，我们不需要保存引用，直接 new 一个接口实现即可。

#### 静态内部类
使用 static 关键字修饰的内部类就是静态内部类，静态内部类和外部类没有任何关系，可以看作是和外部类平级的类。

我们来反编译个静态内部类看看。
java 代码：
```java
public class StaticInnerClass$InnerClass {
    private String name;

    public StaticInnerClass$InnerClass() {
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return StaticInnerClass.access$0(new StaticInnerClass());
    }
}
```

编译后的静态内部类:
```java
public class StaticInnerClass {
    private String name;
    private int age;

    public StaticInnerClass() {
    }

    public static class InnerClass {
        private String name;

        public InnerClass() {
        }

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return (new StaticInnerClass()).age;
        }
    }
}
```

可以看到静态内部类很干净，没有持有外部类的引用，如果需要访问外部类的成员变量，还需要新建一个外部类对象。
否则只能访问外部类的静态属性和静态方法，同理，外部类只能访问内部类的静态属性和静态方法。

注意：如果内部类有成员变量，则需要初始化它，否则，在使用内部类时，无法新建内部类对象。
```java
button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
       // TODO Auto-generated method stub
    }
});
```

上面代码是 Android 中最常见的设置 button 的点击事件，其中 new OnClickListener() {…} 就是一个匿名内部类，在这里没有创建类对象的引用，而是直接创建的类对象。大部分匿名类用于接口回调。




从反编译的代码可以看出：创建的每个匿名内部类编译器都对应生成一个实现接口的子类，同时创建一个构造函数，构造函数的参数是外部类的引用，以及匿名函数中访问的参数。

现在我们知道了：匿名内部类也持有外部类的引用。

同时也理解了为什么匿名内部类不能有构造方法，只能有初始化代码块。 因为编译器会帮我们生成一个构造方法然后调用。

此外还可以看出，匿名内部类中使用到的参数是需要声明为 final 的，否则编译器会报错。

可能有朋友会提问了：参数为什么需要是 final 的？

我们知道在 Java 中实际只有一种传递方式：即引用传递。一个对象引用被传递给方法时，方法中会创建一份本地临时引用，它和参数指向同一个对象，但却是不同的，所以你在方法内部修改参数的内容，在方法外部是不会感知到的。

而匿名内部类是创建一个对象并返回，这个对象的方法被调用的时机不确定，方法中有修改参数的可能，如果在匿名内部类中修改了参数，外部类中的参数是否需要同步修改呢？

#### 静态内部类的使用场景
静态内部类只能访问外部类的静态变量和方法，但相对普通内部类的功能更为完整，因为它可以定义静态变量/方法。

当类 A 需要使用类 B，而 B 不需要直接访问外部类 A 的成员变量和方法时，可以将 B 作为 A 的静态内部类。

比较常见的一种使用场景是：在基类 A 里持有静态内部类 B 的引用，然后在 A 的子类里创建特定业务的 B 的子类，这样就结合多态和静态内部类的优势，既能拓展，又能限制范围。

我们经常使用的 LayoutParams 就是静态内部类，由于不同的布局中参数不一样，Android SDK 提供了很多种 LayoutParams:
```java
ViewGroup.LayoutParams
WindowManager.LayoutParams 继承上一层
RelativeLayout.LayoutParams
…
```

静态内部类的另一种使用场景是：实现单例模式。
```java
public class StaticInnerSingleton {
    
	static class InnerSingleTon {
		private static final StaticInnerSingleton instance = new StaticInnerSingleton();
	}
	
	public static StaticInnerSingleton getInstance() {
		return InnerSingleTon.instance;
	}
	
	public static void main(String[] args) {
		 
	} 
}
```
优点：
懒加载：类加载时不会创建实例，只有当 getInstance() 方法被调用时才去加载静态内部类以及其中持有的StaticInnerSingleton实例
线程安全：JVM 加载类时，可以确保 instance 变量只能初始化一次




#### 总结
##### 总的来说，内部类一般用于两个场景：

需要用一个类来解决一个复杂的问题，但是又不希望这个类是公共的
需要实现一个接口，但不需要持有它的引用
没有引用外部类的内部类，最好设置为 static 的，那样可以避免非静态内部类的弊端：持有外部引用、导致外部类更大。比如说一般的 Adapter, holder。

## 注解

### 什么是注解?
&ensp;&ensp;&ensp;&ensp;对于很多初次接触的开发者来说应该都有这个疑问。Annontation是Java5开始引入的新特征，中文名称叫注解。  
它提供了一种安全的类似注释的机制，用来将任何的信息或元数据（metadata）与程序元素（类、方法、成员变量等）进行关联。  
为程序的元素（类、方法、成员变量）加上更直观更明了的说明，这些说明信息是与程序的业务逻辑无关，并且供指定的工具或框架使用。  
Annontation像一种修饰符一样，应用于包、类型、构造方法、方法、成员变量、参数及本地变量的声明语句中。  
&ensp;&ensp;&ensp;&ensp;Java注解是附加在代码中的一些元信息，用于一些工具在编译、运行时进行解析和使用，起到说明、配置的功能。  
注解不会也不能影响代码的实际逻辑，仅仅起到辅助性的作用。包含在 java.lang.annotation包中。  


注解简单的说就是以 @ 开头的一个字符串，在 Android Studio 默认是黄色高亮，比如下面的 @Override:
```java
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ...
```
这里的 @Override 没有值，只是一个修饰作用，告诉编译器这个方法要覆盖父类的方法，编译器会去检查父类有没有这个方法。    
我们在使用注解时可以传入更详细的内容，使用 “key1=value1, key2=value2”的格式传入，比如：  
```java   
@Author(name = "Libai", date = "2018.5.20")
public class AnnotationTestActivity extends BaseActivity {...}  
```
@Author 注解内部有两个字符串，分别为 name, date：  
```java 
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Author {
    String name();
    String date();
}
```  
当注解的属性只有一个时，可以命名为 value，这样在使用时可以使用快捷方式 – 直接传入值，而不是声明属性名，比如下面的 @ContentView，只有一个名称为 value() 的属性：  
```java  
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {
    //属性叫 value，在使用时可以直接传参数即可，不必显式的指明键值对，是一种快捷方法
    int value();
}  
```
使用时直接传参数，不必指明属性名：  

@Author(name = "Libai", date = "2018.5.20")  
@ContentView(R.layout.activity_annotation)  
public class AnnotationTestActivity extends BaseActivity {...}    


###  注解的作用
&ensp; &ensp; 1、生成文档。这是最常见的，也是java 最早提供的注解。常用的有@param @return 等;  
&ensp; &ensp; 2、跟踪代码依赖性，实现替代配置文件功能。比如Dagger 2依赖注入，未来java开发，将大量注解配置，具有很大用处;  
&ensp; &ensp; 3、在编译时进行格式检查，提示信息。如@override 放在方法前，如果你这个方法并不是覆盖了超类方法，则编译时就能检查出。  

&ensp; &ensp; 4、编译时生成代码：一些处理器可以在编译时根据注解信息生成代码，比如 Java 代码，xml 代码等；


####  

###  注解的原理
&ensp; &ensp;注解本质是一个继承了Annotation的特殊接口，其具体实现类是Java运行时生成的动态代理类。而我们通过反射获取注解时，返回的是Java运行  
时生成的动态代理对象$Proxy1。通过代理对象调用自定义注解（接口）的方法，会最终调用AnnotationInvocationHandler的invoke方法。该方法会从  memberValues这个Map中索引出对应的值。而memberValues的来源是Java常量池。  

###  注解分类  
####  Java 内置的注解
#####  
Java 内置的注解主要有 9 个，分为位于 java.lang or java.lang.annotation 包下。

#####  5个用于通知编译器信息的注解：
@Override ：空注解，用于标记那些覆盖父类方法的方法，如果父类没有这个方法，或者复写的方法访问权限比父类的权限小，编译器就会报错；  

@Deprecated : 空注解，用于标记那些不应该被使用的代码，如果使用了过时的代码，编译器会发出警告；  

@SafeVarargs : 空注解，（varargs 可变参数）用于标记构造函数或者方法，通知编译器，这里的可变参数相关的操作保证安全；  

@FunctionInterface : Java SE 8 出现的，用于通知编译器，这个类型是 function 接口;  

@SuppressWarning：抑制错误，可以用于标记整个类、某个方法、某个属性或者某个参数，用于告诉编译器这个代码是安全的，不必警告， 
强烈建议最小范围使用这个注解，一旦你在一个比较大的范围抑制错误，可能会把真正的问题掩盖了。  


#####  4 个用于修饰注解的注解-元注解： 
修饰其他注解的注解称为“元注解”。

@Documented：让注解信息出现在 document 中  

@Retention ： 指出注解如何存储，支持以下三种参数   

RetentionPolicy.SOURCE : 注解只保留在源码中，编译时会忽略  

RetentionPolicy.CLASS : 更高一级，编译时被编译器保留，但是运行时会被 JVM 忽略  

RetentionPolicy.RUNTIME : 最高级，运行时会被保留，可以被运行时访问  

@Target ：指出注解作用于（修饰）什么对象，支持以下几种参数  

ElementType.TYPE : 作用于任何类、接口、枚举  

ElementType.FIELD : 作用于一个域或者属性  

ElementType.METHOD : 作用于一个方法  

ElementType.PARAMTER : 作用于参数  

ElementType.CONSTRUCTOR : 作用于构造函数  

ElementType.LOCAL_VARIABLE : 作用于本地变量  

ElementType. ANNOTATION_TYPE : 作用于注解  

ElementType.PACKAGE : 作用于包  

@Inherited ：当前注解是否可以继承
  
  
###  自定义一个注解  
####  自定义注解类编写的一些规则:  
#####
  1. Annotation型定义为@interface, 所有的Annotation会自动继承java.lang.Annotation这一接口,并且不能再去继承别的类或是接口.  
  
  2. 参数成员只能用public或默认(default)这两个访问权修饰.  
  
  3. 参数成员只能用基本类型byte,short,char,int,long,float,double,boolean八种基本数据类型和String、Enum、Class、annotations等数据类型,以及这一些类型的数组.  
  
  4. 要获取类方法和字段的注解信息，必须通过Java的反射技术来获取 Annotation对象,因为你除此之外没有别的获取注解对象的方法  
  
  5. 注解也可以不定义成员, 不过这样注解就没啥用了  
  
  PS:自定义注解需要使用到元注解  

我们可以使用 default … 为注解的某个属性指定默认值，这样即使不指定某个属性，编译器也不会报错。这通常可以节约很多时间，  
比如这样：  

```java  
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Author {
    String name() default "Moren";
    String date();
}  
```  

当我们使用 @Author 时没有指定 name = XXX，则会默认为 “Moren”。  
  
###  注解处理器
####  注解处理器是（Annotation Processor）是javac的一个工具，用来在编译时扫描和编译和处理注解（Annotation）。你可以自己定义注解和注解处理器去搞一些事情。一个注解处理器它以Java代码或者（编译过的字节码）作为输入，生成文件（通常是java文件）。这些生成的java文件不能修改，并且会同其手动编写的java代码一样会被javac编译。看到这里加上之前理解，应该明白大概的过程了，就是把标记了注解的类，变量等作为输入内容，经过注解处理器处理，生成想要生成的java代码。  

 #### 对于注解，如果没有注解处理器，其作用和注释没有多大区别。  
 
 #### 运行时处理器和编译时处理器  
 
 先介绍简单的一种：运行时注解处理器。
运行时注解需要使用 注解 + 反射 ，非常简单。
我们先自定义一个 ContentView 注解，表示当前布局对应的 layout 文件：  

```java  
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {
    //属性叫 value ，在使用时可以直接传参数即可，不必显式的指明键值对，是一种快捷方法
    int value() ;
}  
```
然后用它修饰一个 Activity：  
```java  
@ContentView(R.layout.activity_annotation)
public class AnnotationTestActivity extends BaseActivity {
        @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //调用父类的 onCreate
        setContentView(R.layout.activity_annotation);
    }
}  
```    

在 BaseActivity 中反射获取当前类使用的注解，拿到注解的值，就可以直接设置布局了：    

```java  
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        annotationProcess();
    }

    //读取注解，进行处理
    private void annotationProcess() {
        Class c = this.getClass();
        //遍历所有子类
        for (; c != Context.class; c = c.getSuperclass()) {
            //找到使用 ContentView 注解的类
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                try {   //有可能出错的地方都要 try-catch
                    //获取 注解中的属性值，为 Activity 设置布局
                    this.setContentView(annotation.value());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                return;
            }

        }
    }  
  ```    
  
 这样就简单实现了运行时根据注解动态设置布局的功能。

### 总结
使用注解往往可以实现用非常少的代码作出匪夷所思的事情，比如 ButterKnife。

但被人诟病的是，运行时注解需要使用大量 Java 反射而引起较为严重的性能问题。

在使用运行时注解时需要小心，在调用方法时注意对异常的捕获，避免调用失败。  


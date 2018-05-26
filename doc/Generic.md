###  泛型   
###  what定义  
泛型 意味着编写的代码可以被不同类型的对象所重用。  
泛型的提出是为了编写重用性更好的代码。

泛型的本质是**参数化**类型，也就是说所操作的数据类型被指定为一个参数。 
比如常见的集合类 LinkedList:  
```java  
public class LinkedList<E> extends AbstractSequentialList<E> implements
    List<E>, Deque<E>, Queue<E>, Cloneable, Serializable {
//...

transient Link<E> voidLink;

//...
}  
```  
可以看到，上述代码中有很多"<E>"，这里的“E”就是泛型参数，它使得在运行中，创建一个LinkedList时可以传入不同的类型，  
比如：  
LinkedList<String> ll = new LinkedList<String>();    -这样它的成员存放的类型是 String。  
LinkedList<Integer> ll = new LinkedList<Integer>();  -这样它的成员存放的类型是 Integer。    
  
###   why 为什么要引入泛型  
在引入泛型之前，要想实现一个通用的、可以处理不同类型的方法，你需要使用 Object 作为属性和方法参数。  
但是，使用 Object 来实现通用、不同类型的处理，有这么两个缺点：  

每次使用时都需要强制转换成想要的类型；
在编译时编译器并不知道类型转换是否正常，运行时才知道，不安全。    
  
有许多原因促成了泛型的出现，而最引人注意的一个原因，就是为了**创建容器类**。    
在 JDK 1.5 出现泛型以后，许多集合类都使用泛型来保存不同类型的元素，比如 Collection:  
```java  
public interface Collection<E> extends Iterable<E> {

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);
    boolean add(E e);

    boolean remove(Object o);

    boolean containsAll(Collection<?> c);

    boolean addAll(Collection<? extends E> c);

    boolean removeAll(Collection<?> c);
}     
```   
引入泛型的主要目标有以下几点：  
>类型安全 
>>泛型的主要目标是提高 Java 程序的类型安全
>>编译时期就可以检查出因 Java 类型不正确导致的 ClassCastException 异常
>>符合越早出错代价越小原则
>消除强制类型转换 
>>泛型的一个附带好处是，使用时直接得到目标类型，消除许多强制类型转换
>>所得即所需，这使得代码更加可读，并且减少了出错机会
>潜在的性能收益 
>>由于泛型的实现方式，支持泛型（几乎）不需要 JVM 或类文件更改
>>所有工作都在编译器中完成
>>编译器生成的代码跟不使用泛型（和强制类型转换）时所写的代码几乎一致，只是更能确保类型安全而已。    


  
###  泛型使用方式  
*泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。  
*类型参数的意义是告诉编译器这个集合中要存放实例的类型，从而在添加其他类型时做出提示，在编译时就为类型安全做了保证。
*这种参数类型可以用在类、接口和方法的创建中，分别称为泛型类、泛型接口、泛型方法。   

####  泛型类  
泛型类和普通类的区别就是类名后有类型参数列表 <E>，既然叫“列表”了，当然这里的类型参数可以有多个，比如 public class HashMap<K, V>，  
参数名称由开发者决定。

类名中声明参数类型后，内部成员、方法就可以使用这个参数类型，比如下面的 GenericClass<F> 就是一个泛型类，它在类名后声明了类型 F，  
它的成员、方法就可以使用 F 表示成员类型、方法参数/返回值都是 F 类型。

泛型类最常见的用途就是作为容纳不同类型数据的容器类，比如 Java 集合容器类。  
```java  
public class GenericClass<F> {
    private F mContent;

    public GenericClass(F content){
        mContent = content;
    }

    /**
     * 泛型方法
     * @return
     */
    public F getContent() {
        return mContent;
    }

    public void setContent(F content) {
        mContent = content;
    }

    /**
     * 泛型接口
     * @param <T>
     */
    public interface GenericInterface<T>{
        void doSomething(T t);
    }
}  
```  


####  泛型接口   
和泛型类一样，泛型接口在接口名后添加类型参数，比如上面的 GenericInterface<T>，接口声明类型后，接口方法就可以直接使用这个类型。

实现类在实现泛型接口时需要指明具体的参数类型，不然默认类型是 Object，这就失去了泛型接口的意义。

未指明类型的实现类，默认是 Object 类型：  
```java  
public class Generic implements GenericInterface{

    @Override
    public void doSomething(Object o) {
        //...
    }
}
 
指明了类型的实现：

public class Generic implements GenericInterface<String>{
    @Override
    public void doSomething(String s) {
        //...
    }
}  
```  
泛型接口比较实用的使用场景就是用作策略模式的公共策略，比如Comparator，它就是一个泛型接口：  
```java  
public interface Comparator<T> {

    public int compare(T lhs, T rhs);

    public boolean equals(Object object);
}  
```  
泛型接口定义基本的规则，然后作为引用传递给客户端，这样在运行时就能传入不同的策略实现类。  



####  泛型方法  
泛型方法是指使用泛型的方法，如果它所在的类是个泛型类，那就很简单了，直接使用类声明的参数。

如果一个方法所在的类不是泛型类，或者他想要处理不同于泛型类声明类型的数据，那它就需要自己声明类型，举个例子：  
```java  
/**
 * 传统的方法，会有 unchecked ... raw type 的警告
 * @param s1
 * @param s2
 * @return
 */
public Set union(Set s1, Set s2){
    Set result = new HashSet(s1);
    result.addAll(s2);
    return result;
}

/**
 * 泛型方法，介于方法修饰符和返回值之间的称作 类型参数列表 <A,V,F,E...> (可以有多个)
 *      类型参数列表 指定参数、返回值中泛型参数的类型范围，命名惯例与泛型相同
 * @param s1
 * @param s2
 * @param <E>
 * @return
 */
public <E> Set<E> union2(Set<E> s1, Set<E> s2){
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}  
```  
注意上述代码在返回值前面也有个 <E>，它和类名后面的类型参数列表意义一致，指明了这个方法中类型参数的意义、范围。  
    

####  泛型通配符    
有时候希望传入的类型有一个指定的范围，从而可以进行一些特定的操作，这时候就是通配符边界登场的时候了。

泛型中有三种通配符形式：  
   
> <?> 无限制通配符
> <? extends E> extends 关键字声明了类型的**上界**，表示参数化的类型可能是所指定的类型E，或者是此类型E的子类
> <? super E> super 关键字声明了类型的**下界**，表示参数化的类型可能是指定的类型E，或者是此类型E的父类  

###   泛型使用场景   



###  总结  



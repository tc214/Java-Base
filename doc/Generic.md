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

> return shell_exec("echo $input | $markdown_script");


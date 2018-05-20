
#### ��Ա�ڲ���

##### /*Ҫ��ʹ����֪�ı������桰�������ڿ���̨���10��20��30��*/

```java
public class OutClass {// �ⲿ��
    int num = 30;
	
	class InnerClass {// ��Ա�ڲ���
		int num = 20;
		
		public void show() {
			int num = 10;
			System.out.println(?);
			System.out.println(?);
			System.out.println(?);
		}
	}
	
	public static void main(String[] args) {
	    // �����ⲿ�����
		OutClass outer = new OutClass();
		// �����ڲ������
		OutClass.InnerClass inner = outer.new InnerClass();
		// �ڲ����������Լ��ķ���
		inner.show();
	}
}
```

#####
������
���ڵ�һ�պ͵ڶ��գ����Ŵ�Ҷ������״�ԣ����ڵ����գ��ܴ���߿��²��ࡣ
show()�����еľֲ�����num������InnerClass�еĳ�Ա����num��InnerClass�еĳ�Ա����num������OutClass�еĳ�Ա����num��
��һ�գ���ʵ��������ֲ�����num��
�ڶ��գ�ʵ����Ҫ��������ڲ���ĳ�Ա����num��������Ҫ�ҵ��ڲ���InnerClass���������InnerClass.this��Ϊʲô��this����Ϊshow()������InnerClass���������,����show��������InnerClass����
�����գ�ʵ����Ҫ��������ⲿ��OutClass�ĳ�Ա����num��������Ҫ�ҵ��ⲿ����������OutClass.this�����ֱ�ʾ������Java�Ĺ涨��

ע���Ǿ�̬�ڲ�������ⲿ���������� OutClass.this


�𰸣�
num
this.num ��InnerClass.this.num
OutClass.this.num

�ڹ��̵�binĿ¼�£����ǻᷢ����OutClass.class��OutClass$InnerClass.class�����ļ�����AS��OutClass$InnerClass.class��
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
������������ķ������ֽ����У����ǿ��Կ�������������Ϊ�ڲ��ഴ��һ������ this$0 �Ķ��������ⲿ������á�


#####�ڶ��⣺������� ��Ҫ���ڿ���̨�����HelloWorld��
```java
        interface Inter { 
            void show(); 
        }
        class Outer { 
            //������� 
        }
        class OuterDemo {
            public static void main(String[] args) {
                  Outer.method().show();
              }
        }
```

######
������
�����ԣ�method��һ����̬���������Ҳ���Ҫ����һ�����Ե���show������������ã�
show�����ڽӿ�Inter�ж��壬���ԣ�Outer��Ҫʵ�ֽӿ�Inter���ܵ���show������Ȼ�������ܸı�Outer�Ľӿڹ�ϵ��
ֻ��ͨ���ڲ�����ʵ�֣����ڲ���ʵ�ֽӿ�Inter��Ȼ�󴴽��ڲ���������show��������Ҫ��ӡ��HelloWorld"������Ҫ���ڲ���
��method�����������HelloWorld"��
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

Ҳ����ʹ�þֲ������ڲ��ࣺ
```java
public Outer method() {
    return new Inter(){
��������void show(){
��������System.out.println("HelloWorld");
��������}
����};
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

������x���ڲ���ĳ�Ա���������ڲ����п��Ե����ⲿ��ĳ�Ա����y��
�𰸣�x+y=5
		
#####
���ڲ����У�ֱ��ʹ�ñ��������ᰴ�մӷ����еľֲ����������ڲ���ı��������ⲿ��ı�����˳�����
Ҳ����˵��������ⲿ�ࡢ�ڲ��ࡢ�������������ı���/��������������ѷ�����ֱ�ӷ��ʱ����������޸�Ϊ����������
������ڷ�����ǿ�Ʒ����ڲ���ĳ�Ա����/����������ʹ�� this.i������� this ��ʾ��ǰ���ڲ������
������ڷ�����ǿ�Ʒ����ⲿ��ĳ�Ա����/����������ʹ�� OutClass.this.i������� OutClass.this ��ʾ��ǰ�ⲿ�����
��Ա�ڲ������ͬ�ⲿ��ĳ�Աһ����ͬ�����Ա�public��protected��private��ȱʡ��default����Щ���η������Ρ�

������һ�������ǣ���Ա�ڲ��಻�ܴ�����̬����/������������ǳ��Դ�������������ֱ�ӱ���
The field i cannot be declared static in a non-static inner type, unless initialized with a constant expression
The method test cannot be declared static; static methods can only be declared in a static or top level type

Ϊʲô�������أ�

Stackoverflow ��һ���ش�ܺã�

��if you��re going to have a static method, the whole inner class has to be static. Without doing that, you couldn��t guarantee that the inner class existed when you attempted to call the static method. ��

����֪��Ҫʹ��һ����ľ�̬��Ա����Ҫ�Ȱ��������ص�������У�����Ա�ڲ�������Ҫ���ⲿ����� new һ��ʵ���ſ���ʹ�ã�����޷�������̬��Ա��Ҫ��


#####
��Java�У����Խ�һ���ඨ������һ�����������һ���������棬���������Ϊ�ڲ��ࡣ
�㷺�����ϵ��ڲ���һ����˵���������֣���Ա�ڲ��ࡢ�ֲ��ڲ��ࡢ�����ڲ���

####��Ա�ڲ����ʹ�ó���
#####
��ͨ�ڲ�����Է����ⲿ������г�Ա�ͷ�������˵��� A ��Ҫʹ���� B ��ͬʱ B ��Ҫ���� A �ĳ�Ա/����ʱ�����Խ� B ��Ϊ A �ĳ�Ա�ڲ��ࡣ

���簲׿�����г�������һ�� Activity ����һ�� ListView��������Ҫ����һ���ض�ҵ��� adapter������� adapter ����Ҫ�������ݣ��������һ���࣬�����ֻ�е�ǰ����Ҫʹ�õ�����ȫ���Խ��������� Activity �С�


####
�����ڲ���
�����ڲ�����ص㣺 
1.һ�������ڼ̳����������ʵ�ֽӿڣ�������Ҫ���Ӷ���ķ�����ֻ�ǶԼ̳з�����ʵ�ֻ��Ǹ��ǡ� 
3.����û�����壬Ҳ���ǲ���Ҫʹ�õ���

�����ڲ���Ҳ����û�����ֵ��ڲ�������Ϊû�����֣����������ڲ���ֻ��ʹ��һ�Σ���ͨ�������򻯴����д��
��ʹ�������ڲ��໹�и�ǰ��������
����̳�һ�������ʵ��һ���ӿڣ������ֻ�ܼ̳�һ�����࣬��ʵ��һ���ӿڡ�
���������ڲ��໹��������������
    1�������ڲ��಻���ǳ����࣬��Ϊϵͳ�ڴ��������ڲ����ʱ�򣬻����������ڲ���Ķ�����˲����������ڲ��ඨ��ɳ����ࡣ
    2�������ڲ��಻�ܶ��幹���������췽��������Ϊ�����ڲ���û�������������޷����幹�������������ڲ�����Զ���ʵ����ʼ���顣

�ȿ�һ�����ӣ����һ�������ڲ�����÷���
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

���н����hello
���Կ���������ֱ�ӽ�������Parent�еķ����ڴ�������ʵ���ˣ����������ʡ��һ�������д��
ע�⣺�������ǲ���ʵ�����ģ���Ҫ��Ϊnew Parent()�Ǵ���Parent��ʵ������ʵnew����Parent��ʵ���࣬���ʵ�����Ǹ������ڲ��ࡣ

�����ڲ��໹�����ڽӿ��ϣ�

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

����inter.dos()�ǿ����������õģ���inter.dos()���ܵ��ã�Ϊʲô�أ�ע��Inters inter = new Inters() ��������Inters�Ķ��󣬶��������ڲ���Ķ��󡣵��̳и���ķ����ǿ����������õģ��������У������ڲ���ʵ���˽ӿ�Inters��dos��������˿��Խ���Inters�Ķ���ȥ���á�


#####
�ڲ�����õ��ⲿ����������final���εģ�
��Ϊ�������ڵ�ԭ�򡣷����еľֲ��������������������������Ҫ�ͷŵ���final��֤�������ʼ��ָ��һ���������ȣ��ڲ�����ⲿ����ʵ�Ǵ���ͬһ�������ڲ��಻����Ϊ�����ڷ����оͻ����ŷ�����ִ����϶������߱����١���������ˣ�����ⲿ��ķ����еı���������final����ô���ⲿ�෽��ִ����ϵ�ʱ������ֲ������϶�Ҳ�ͱ�GC�ˣ�Ȼ���ڲ����ĳ��������û��ִ���꣬���ʱ���������õ��ⲿ�����Ѿ��Ҳ����ˡ��������Ϊfinal��java�Ὣ�����������һ����Ϊ��Ա�����������ڲ����У������Ļ�������final�����ε�ֵʼ���޷��ı䣬�������������ָ����ڴ�����Ͳ���䡣 
Ϊ�˽�����ֲ�����������������ֲ��ڲ���Ķ�����������ڵĲ�һ��������
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

Java8֮ǰ���ӻᱨ��
Cannot refer to the non-final local variable name defined in an enclosing scope
Java8���Ѿ�����Ҫ��final�ˣ�����ֲ������������ڲ�����ʣ���ô�þֲ������൱���Զ�ʹ����final����


######�����ڲ����ʼ��
����һ�㶼�����ù����������ĳ��ʵ���ĳ�ʼ�������ģ����������ڲ�����û�й������ģ�����ô����ʼ�������ڲ����أ�ʹ�ù������飡
���ù��������ܹ��ﵽΪ�����ڲ��ഴ��һ����������Ч����

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
            //����������ɳ�ʼ������
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
�����
null
tom


������
�����ڲ�������г�Ա������û�г�ʼ��������Ҫ�ھ�̬��ʼ���н��г�ʼ���������޷�ʹ���ڲ���ĳ�Ա������

#####�����ڲ����ʹ�ó���
######
Android ����������һ����ť�ĵ���¼��ܼ򵥣�ֱ�� new һ�� View.OnClickListener Ȼ��ʵ�ַ������ɣ�
```java
mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //...
            }
        });
```
֮��������д������Ϊ���ǲ���Ҫ������� new View.OnClickListener �����ã�ֻҪ�����˶��󼴿ɡ�
����ʹ�ó��������ǣ�һ�������ķ���ֵ�ǽӿڣ�Ȼ����ݲ�ͬ�������ز�ͬ��ʵ�֣����ǲ���Ҫ�������ã�ֱ�� new һ���ӿ�ʵ�ּ��ɡ�

#####
��̬�ڲ���
ʹ�� static �ؼ������ε��ڲ�����Ǿ�̬�ڲ��࣬��̬�ڲ�����ⲿ��û���κι�ϵ�����Կ����Ǻ��ⲿ��ƽ�����ࡣ

���������������̬�ڲ��࿴����
java ���룺
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

�����ľ�̬�ڲ���:
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

���Կ�����̬�ڲ���ܸɾ���û�г����ⲿ������ã������Ҫ�����ⲿ��ĳ�Ա����������Ҫ�½�һ���ⲿ�����
����ֻ�ܷ����ⲿ��ľ�̬���Ժ;�̬������ͬ���ⲿ��ֻ�ܷ����ڲ���ľ�̬���Ժ;�̬������

ע�⣺����ڲ����г�Ա����������Ҫ��ʼ������������ʹ���ڲ���ʱ���޷��½��ڲ������
```java
button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
       // TODO Auto-generated method stub
    }
});
```

��������� Android ����������� button �ĵ���¼������� new OnClickListener() {��} ����һ�������ڲ��࣬������û�д������������ã�����ֱ�Ӵ���������󡣴󲿷����������ڽӿڻص���




�ӷ�����Ĵ�����Կ�����������ÿ�������ڲ������������Ӧ����һ��ʵ�ֽӿڵ����࣬ͬʱ����һ�����캯�������캯���Ĳ������ⲿ������ã��Լ����������з��ʵĲ�����

��������֪���ˣ������ڲ���Ҳ�����ⲿ������á�

ͬʱҲ�����Ϊʲô�����ڲ��಻���й��췽����ֻ���г�ʼ������顣 ��Ϊ�����������������һ�����췽��Ȼ����á�

���⻹���Կ����������ڲ�����ʹ�õ��Ĳ�������Ҫ����Ϊ final �ģ�����������ᱨ��

���������ѻ������ˣ�����Ϊʲô��Ҫ�� final �ģ�

����֪���� Java ��ʵ��ֻ��һ�ִ��ݷ�ʽ�������ô��ݡ�һ���������ñ����ݸ�����ʱ�������лᴴ��һ�ݱ�����ʱ���ã����Ͳ���ָ��ͬһ�����󣬵�ȴ�ǲ�ͬ�ģ��������ڷ����ڲ��޸Ĳ��������ݣ��ڷ����ⲿ�ǲ����֪���ġ�

�������ڲ����Ǵ���һ�����󲢷��أ��������ķ��������õ�ʱ����ȷ�������������޸Ĳ����Ŀ��ܣ�����������ڲ������޸��˲������ⲿ���еĲ����Ƿ���Ҫͬ���޸��أ�

#####��̬�ڲ����ʹ�ó���
��̬�ڲ���ֻ�ܷ����ⲿ��ľ�̬�����ͷ������������ͨ�ڲ���Ĺ��ܸ�Ϊ��������Ϊ�����Զ��徲̬����/������

���� A ��Ҫʹ���� B���� B ����Ҫֱ�ӷ����ⲿ�� A �ĳ�Ա�����ͷ���ʱ�����Խ� B ��Ϊ A �ľ�̬�ڲ��ࡣ

�Ƚϳ�����һ��ʹ�ó����ǣ��ڻ��� A ����о�̬�ڲ��� B �����ã�Ȼ���� A �������ﴴ���ض�ҵ��� B �����࣬�����ͽ�϶�̬�;�̬�ڲ�������ƣ�������չ���������Ʒ�Χ��

���Ǿ���ʹ�õ� LayoutParams ���Ǿ�̬�ڲ��࣬���ڲ�ͬ�Ĳ����в�����һ����Android SDK �ṩ�˺ܶ��� LayoutParams:
```java
ViewGroup.LayoutParams
WindowManager.LayoutParams �̳���һ��
RelativeLayout.LayoutParams
��
```

��̬�ڲ������һ��ʹ�ó����ǣ�ʵ�ֵ���ģʽ��
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
�ŵ㣺
�����أ������ʱ���ᴴ��ʵ����ֻ�е� getInstance() ����������ʱ��ȥ���ؾ�̬�ڲ����Լ����г��е�StaticInnerSingletonʵ��
�̰߳�ȫ��JVM ������ʱ������ȷ�� instance ����ֻ�ܳ�ʼ��һ��




####�ܽ�
#####�ܵ���˵���ڲ���һ����������������

��Ҫ��һ���������һ�����ӵ����⣬�����ֲ�ϣ��������ǹ�����
��Ҫʵ��һ���ӿڣ�������Ҫ������������
û�������ⲿ����ڲ��࣬�������Ϊ static �ģ��������Ա���Ǿ�̬�ڲ���ı׶ˣ������ⲿ���á������ⲿ����󡣱���˵һ��� Adapter, holder��

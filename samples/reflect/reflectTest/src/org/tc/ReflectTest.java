package org.tc;

import java.lang.reflect.Constructor;

public class ReflectTest {

	
	
	public static void main(String[] args) throws Exception {
		Class clazz = Test.class;
		Class clazz2 = User.class;
		Class clazz3 = Bean.class;
		
		Object obj = createWithoutParam(clazz);
		System.out.println(obj);

		Object obj2 = createWithOneParam(clazz2);
		System.out.println(obj2);
		
		Object obj3 = createWithOneParam(clazz3);
		System.out.println(obj3);
	}
	
	static Object createWithoutParam(Class clazz) throws Exception {
		Object obj = clazz.newInstance();
		return obj;
	}
	

	static Object createWithOneParam(Class clazz) throws Exception {
		Constructor con = clazz.getConstructor(String.class);
		Object obj = con.newInstance("obj name");
		return obj;
	}

}

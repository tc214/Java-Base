package org.tc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {

	
	
	public static void main(String[] args) throws Exception {
		Class clazz = Test.class;
		Class clazz2 = User.class;
		Class clazz3 = Bean.class;
		
//		Object obj = createWithoutParam(clazz2);
//		System.out.println(obj);

//		catMethods(obj); 
		catAnnontaions(clazz2);
		catFilds(clazz2);
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
	
	/*
	 * get methods of the Class
	 */
	static void catMethods(Object obj) {
		Method[] ms = obj.getClass().getDeclaredMethods();
		ms = obj.getClass().getMethods();
		
		for (Method m : ms) {
			System.out.println(m.getName());
		}
	}
	
	/*
	 * get annotaions of the Class
	 */
	static void catAnnontaions(Class clazz) {
		Annotation[] as = clazz.getAnnotations();
		for (Annotation an : as) {
			System.out.println(an.toString());
		}
	}

	/*
	 * get annotaions of the Class
	 */
	static void catFilds(Class clazz) {
		Field[] fs = clazz.getFields();
		for (Field f: fs) {
			System.out.println(f.getName());
		}
	}
	
}

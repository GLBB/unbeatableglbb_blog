---
title: Java反射
date: 2017-12-22 21:20:23
tags:
---
java反射主要用于框架当中

一个类有多个组成部分，如成员变量，方法，构造方法等，
反射就是加载类，并揭破出类的各个部分。
如配置文件中写
```
itcast.Person run();
```
框架根据这句话会去加载Person这个类，并调用run()方法

得到字节码的几种方法
```
package reflect;


public class demo {
	public static void main(String[] args) throws ClassNotFoundException {
		//得到一个类的字节码的几种方法
		//1.
		Class class_1 = Class.forName("reflect.Person");
		//2.
		Class class_2 = new Person().getClass();
		//3.
		Class class_3 = Person.class;
	}
}

```

解析一个类的示例
```
package reflect;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class demo2 {
	@Test
	public void test() throws Exception{
		Class class_1 = Class.forName("reflect.Person");
		Constructor constructor = class_1.getConstructor(null);
		Person person_1 = (Person) constructor.newInstance(null);
		System.out.println(person_1.name);
	}
}

package reflect;

import java.util.List;

public class Person {
	String name = "aaa";
	int age = 0;
	List list;
	public Person(){
		System.out.println(name);
	}
	
	public Person(String name){
		this.name = name;
		System.out.println(this.name);
	}
	
	public Person(String name,int age){
		this.name = name;
		this.age = age;
		System.out.println(this.name+" "+this.age);
	}
	
	private Person(List list){
		this.list = list;
		System.out.println(list);
	}
	
	public void print(){
		System.out.println(name);
	}
	
	public void print(String name,int age){
		System.out.println(name+":"+age);
	}
	
	public Class[] print(String name,int r[]){
		return new Class[]{String.class};
	}
	
	private void print(int age){
		System.out.println(age);
	}
	
	public static void print(boolean k){
		System.out.println(k);
	}
}

```
其中null代表构造无参构造函数
### 其中构造Person的字符串参数类型的构造函数
```
public Person(String name){
		this.name = name;
		System.out.println(this.name);
}
```
代码如下
```
@Test
	public void test_2() throws Exception{
		Class class_1 = Class.forName("reflect.Person");
		Constructor constructor =     class_1.getConstructor(String.class);
		Person p = (Person) constructor.newInstance("GLBB");
		System.out.println(p.name);
	}
```

#### 注意：getConstructor(...args)只能得到public类型的构造参数
解释：
用于公共的有

getConstructor(...args)用于解析公共构造函数
```
Constructor<T>	getConstructor(Class<?>... parameterTypes)
Returns a Constructor object that reflects the specified public constructor of the class represented by this Class object.
```
getMethod(...args)用于解析公共方法
```
Method	getMethod(String name, Class<?>... parameterTypes)
Returns a Method object that reflects the specified public member method of the class or interface represented by this Class object.
```
getField()用于得到成员变量
```
Field	getField(String name)
Returns a Field object that reflects the specified public member field of the class or interface represented by this Class object.
```

其中用于私有的有
getDeclaredConstructor(Class<?>... parameterTypes)
```
Constructor<T>	getDeclaredConstructor(Class<?>... parameterTypes)
Returns a Constructor object that reflects the specified constructor of the class or interface represented by this Class object.
```
getDeclaredMethod(String name, Class<?>... parameterTypes)
```
Method	getDeclaredMethod(String name, Class<?>... parameterTypes)
Returns a Method object that reflects the specified declared method of the class or interface represented by this Class object.
```
getDeclaredField(String name)
```
Field	getDeclaredField(String name)
Returns a Field object that reflects the specified declared field of the class or interface represented by this Class object.
```
获得私有的Person方法
```
@Test
	public void test_4() throws Exception{
		Class class_1 = Class.forName("reflect.Person");
		Constructor constructor = class_1.getDeclaredConstructor(List.class);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		constructor.setAccessible(true);//强制访问
		Person p = (Person) constructor.newInstance(list);
		System.out.println(p.list);
	}
```
必须设置setAccessible为true

可以直接用class.newInstance()获得无参构造函数
```
@Test
	public void test_5() throws Exception{
		Class class_1 = Class.forName("reflect.Person");
		Person p = (Person) class_1.newInstance();
	}
```

### 通过反射调用方法 
#### 无参方法
```
public void print(){
		System.out.println(name);
}
```
反射解析方法
```
//public void print()
	@Test
	public void test1() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Method method = clazz.getMethod("print", null);
		method.invoke(new Person(), null);
	}
```

#### 带参数的方法
```
public void print(String name,int age){
		System.out.println(name+":"+age);
}
```
反射解析方法
```
//public void print(String name,int age)
	@Test
	public void test2() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Method method = clazz.getMethod("print", String.class,int.class);
		method.invoke(new Person(), "GLBB",20);	
	}
```

#### 静态的方法
```
public static void print(boolean k){
		System.out.println(k);
}
```
反射解析方法
```
//public static void print(boolean k)
	@Test
	public void test5()throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Method method = clazz.getMethod("print", boolean.class);
		method.invoke(null, true);
	}
```

### 解析main方法的坑
```
public static void main(String[] args){
		System.out.println("main!!");
}
```
解析方法如下是错的
```
@Test
	public void test6() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Method method = clazz.getMethod("main", String[].class);
		method.invoke(null,new String[]{"aa","bb"});
	}
```
原因：SUM公司JAVA升级问题
JDK1.4时，没有可变参数，传递函数间的参数是通过数组来解决的
JDK1.4    method.invoke(Object obj,Object objs[]);
JDK1.5    method.invoke(Object obj,Object ...args);
因此new String[]{"aa","bb"}传递过去
会被解析成String aa, String bb
即会寻找main(String name,String ss)方法，没有此方法，报错
或者不符合我们的预期
因此，如果解析main此类参数为对象的方法时应注意写成如下
```
@Test
	public void test6() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Method method = clazz.getMethod("main", String[].class);
		//方法一
		//method.invoke(null, (Object)new String[]{"1","2"});
		//方法二
		method.invoke(null, new Object[]{new String[]{"1","2"}});
	}
```
	
### 解析字段方法
```
//public String name = "aaa";
	@Test
	public void test1() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Field f = clazz.getField("name");
		String s = (String) f.get(new Person());
		System.out.println(s);
	}
```
```
//private int age = 0;
	@Test
	public void test2() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Field f = clazz.getDeclaredField("age");
		f.setAccessible(true);
		int number = (int) f.get(new Person());
		System.out.println(number);
	}
```

```
//private static String passwd = "123";
	@Test
	public void test3() throws Exception{
		Class clazz = Class.forName("reflect.Person");
		Field f = clazz.getDeclaredField("passwd");
		f.setAccessible(true);
		String str = (String) f.get(null);
		System.out.println(str);
	}
```
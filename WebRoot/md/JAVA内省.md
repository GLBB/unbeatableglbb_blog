---
title: JAVA内省
date: 2017-12-22 21:22:48
tags:
---
### 学习内省原因
开发框架时，经常需要对JAVA属性进行操作，但是用反射技术来操作过于麻烦，所以SUM公司专门开发了一套API，用于对JAVA属性进行操作

#### 得到所有的属性
其中Person类相当于一个Bean,
```
package introspector;

public class Person {
	public String name;
	public int age;
	public String passwd;
	
	public String getAB(){
		return null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
```
其中具有的属性为name,age,passwd,AB,class
以下为获得所有属性的代码
```
@Test
	public void test1() throws Exception{
		BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor pd : pds){
			System.out.println(pd.getName());
		}
	}
```
若是不想要获得父类型所继承的属性可以调用方法
```
BeanInfo beanInfo = Introspector.getBeanInfo(Person.class,Object.class);
```

### 操作bean的属性
```
//操作bean的age属性
	@Test
	public void test2() throws Exception{
		Person p = new Person();
		PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
		
		Method m = pd.getWriteMethod();
		m.invoke(p, 45);
		
		m = pd.getReadMethod();
		int age = (int) m.invoke(p, null);
		System.out.println(age);
	}
```

如果要得到属性类型
```
Class c = pd.getPropertyType();
System.out.println(c);
```
---
title: BeanUtils操作
date: 2017-12-22 21:19:20
tags:
---
因为用Sum公司API操作Bean属性还是比较麻烦，所以Apache开发出BeanUtils来让操作属性更加简便,BeanUtils需要一个日志支持，可以导入logging.jar
下面是Bean Person类
```
package beanutils;

public class Person {
	public String name;
	public int age;
	public String passwd;
	private Date birthday;
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
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

更改属性的方法
```
@Test
	public void test1() throws IllegalAccessException, InvocationTargetException{
		Person p = new Person();
		BeanUtils.setProperty(p, "name", "glbb");
		System.out.println(p.getName());
	}
```

### BeanUtils会进行基本类型自动转换
只限八种基本类型
```
@Test
	public void test2() throws IllegalAccessException, InvocationTargetException{
		String name = "GLBB";
		String passwd = "123456";
		String age = "20";
		//String birthday = "1997-09-25";
		
		Person p = new Person();
		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "passwd", passwd);
		BeanUtils.setProperty(p, "age", age);
		//BeanUtils.setProperty(p, "birthday", birthday);
		
		System.out.println(p.name+" "+p.passwd+" "+p.age+" ");
	}
```
其中把String age型转换为int age类型然后赋值给person，
但是无法自动进行String转换成date类型
需要类型转换器

### 类型转换器
自己写的类型转换器
```
@Test
	public void test3() throws IllegalAccessException, InvocationTargetException{
		String name = "GLBB";
		String passwd = "123456";
		String age = "20";
		String birthday = "1997-09-25";
		
		ConvertUtils.register(new Converter(){
			@SuppressWarnings("unchecked")
			@Override
			public <T> T convert(Class<T> arg0, Object arg1) {
				if(arg1==null){
					return null;
				}else if(!(arg1 instanceof String)){
					throw new ConversionException("不是String类型");
				}
				String str = (String)arg1;
				if(str.trim().equals("")){
					throw new ConversionException("无效字符串");
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return (T) sdf.parse(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					throw new ConversionException("字符串格式不对");
				}
			}
		}, Date.class);
		
		Person p = new Person();
		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "passwd", passwd);
		BeanUtils.setProperty(p, "age", age);
		BeanUtils.setProperty(p, "birthday", birthday);
		
		System.out.println(p.name+" "+p.passwd+" "+p.age+" "+p.getBirthday());
	}
```
#### 可以调用Apache已经写好的类型转换器
```
@Test
	public void test4() throws IllegalAccessException, InvocationTargetException{
		String name = "GLBB";
		String passwd = "123456";
		String age = "20";
		String birthday = "1997-09-25";
		
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		
		Person p = new Person();
		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "passwd", passwd);
		BeanUtils.setProperty(p, "age", age);
		BeanUtils.setProperty(p, "birthday", birthday);
		
		System.out.println(p.name+" "+p.passwd+" "+p.age+" "+p.getBirthday());
	}
```
#### 使用Map进行填充
```
@Test
	public void test5() throws IllegalAccessException, InvocationTargetException{
		Map<String,String> map = new HashMap<>();
		map.put("name", "GLBB");
		map.put("passwd", "123456");
		map.put("age", "20");
		map.put("birthday", "1997-09-25");
		
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		
		Person p = new Person();
		
		BeanUtils.populate(p, map);
		
		System.out.println(p.name+" "+p.passwd+" "+p.age+" "+p.getBirthday());
	}
```
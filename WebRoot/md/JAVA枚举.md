---
title: JAVA枚举
date: 2017-12-22 21:21:24
tags:
---
看如下示例代码
```
package enumeration;

import org.junit.Test;

public class demo_1 {
	
	@Test
	public void test(){
		print(Grade.A);
		print(Grade.B);
		print(Grade.C);
		print(Grade.D);
		print(Grade.E);
	}
	
	public void print(Grade g){
		System.out.println(g.getValue());
	}
}

enum Grade{
	A("100-90"),B("89-80"),C("79-70"),D("69-60"),E("59-0");
	
	private String value;
	private Grade(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
```
#### 注意
1. 枚举构造函数必须为私有的
2. switch可以接受枚举类型



### 带有抽象方法的枚举
```
package enumeration;

import org.junit.Test;

public class demo_1 {
	
	@Test
	public void test(){
		print(Grade.A);
		print(Grade.B);
		print(Grade.C);
		print(Grade.D);
		print(Grade.E);
		System.out.println(Grade.A.locateValue());
	}
	
	public void print(Grade g){
		System.out.println(g.getValue());
	}
}

enum Grade{
	A("100-90"){
		public String locateValue(){
			return "优";
		}
	},B("89-80"){
		public String locateValue(){
			return "良";
		}
	},C("79-70"){
		public String locateValue(){
			return "中";
		}
	},D("69-60"){
		public String locateValue(){
			return "差";
		}
	},E("59-0"){
		public String locateValue(){
			return "不及格";
		}
	};
	
	private String value;
	private Grade(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public abstract String locateValue();
}
```

### 单态设计模式
内存中只有一个这样的类
可以使用枚举来实现
```
enum A{
	a;
}
```
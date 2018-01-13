---
title: JAVA可变参数
date: 2017-12-21 00:36:16
tags:
---
### java可变参数
```
package variable_parameter;

import org.junit.Test;

public class demo1 {
	@Test
	public void testsum(){
		sum(1,2,3,4,5,6);
		//或者写成
		int[] arr = {1,2,3,4,5,6};
		sum(arr);
	}
	
	public void sum(int ...values){
		//可变参数可以看成数组
		int result = 0;
		for(int i:values){
			result += i;
		}
		System.out.println(result);
	}
}

```
但注意不可写成
```
public void sum(int ...values,int a){}
```
可写成
```
public void sum(int a,int ...values){}
```

<br/>
### 可变参数可能具有的坑

java.util.Arrays中有aslist方法
以下是asList解释
```
static <T> List<T>	asList(T... a)
Returns a fixed-size list backed by the specified array.
```

![Markdown](http://i1.bvimg.com/624208/bcd8e58d2cfdae48.png)

T代表泛型对象 
注意int[]数组是被当作一个对象
Interger[]是被当成一个数组，数组里面每一个都是Integer对象


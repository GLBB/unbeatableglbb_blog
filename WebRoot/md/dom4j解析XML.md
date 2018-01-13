---
title: dom4j解析XML、
date: 2017-12-27 20:51:15
tags:
---
dom4j下载地址
>>https://dom4j.github.io/
dom4j快速入门
>>https://github.com/dom4j/dom4j/wiki/Quick-Start-Guide

### dom4j写document到xml中乱码问题
1. 由于sum.io.FileWriter中用的是GB2312码表，而XML中用的是UTF-8所以会乱码，且FileWriter构造函数中没有设置码表的，所以切换到用OutputStreamWriter()可指定编码
2. 将xml文档改成gb2312 用FileWriter还是会产生乱码，原因是将xml读取到内存中document会用UTF-8方式，而FileWriter用gb2312写，所以产生乱码
#### 若想要将xml改成gb2312可采用格式化输出器
```
<?xml version="1.0" encoding="UTF-8"?>

<书架> 
  <书> 
    <书名 author="GLBB">JAVA就业培训教程</书名>  
    <作者>张孝祥</作者>  
    <售价>29.00元</售价> 
  </书>  
  <书> 
    <书名 name="GLBB">JavaScript网页开发</书名>  
    <作者>张孝祥</作者>  
    <售价>28.00元</售价> 
  </书> 
</书架>

```

```
package dom4jexercise;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.junit.Test;

public class demo1 {

	// 得到<书名 name="GLBB">JavaScript网页开发</书名>中的文本,在得到其中name属性的值
	@Test
	public void read1() throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/book.xml"));
		Element root = (Element) document.getRootElement();
		Element book = root.elements("书").get(1);
		String bookName = book.element("书名").getText();
		String bookAttName = book.element("书名").attributeValue("name");
		System.out.println(bookName);
		System.out.println(bookAttName);
	}

	// 在book.xml第一本书中添加一个售价<售价>159.00元</售价>
	@Test
	public void add() throws Exception {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File("src/book.xml"));

		Element root = document.getRootElement();
		Element book = root.element("书");
		book.addElement("售价").setText("159.00元");
		
		//方法一全部采用UTF-8
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/book.xml"),"UTF-8"));
		
		//方法二采用gb2312
		//OutputFormat format = OutputFormat.createPrettyPrint();
		//format.setEncoding("gb2312");
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/book.xml"),"gb2312"),format);
		
		//以上都是采用字符流，最好采用字节流
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/book.xml"),format);
		
		writer.write(document);
		writer.close();
		
	}
	
	//在指定位置添加<售价>159.00元</售价>  ,更改保存所有孩子list集合的顺序
	@Test
	public void add2() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/book.xml"));
		
		Element root = document.getRootElement();
		Element book = root.element("书");
		List<Element> childrens = book.elements();
		
		Element price = DocumentHelper.createElement("售价");
		price.setText("189元");
		
		childrens.add(2,price);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/book.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	//删除第一本书的第一个售价节点
	@Test
	public void delete() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/book.xml"));
		
		Element price = document.getRootElement().element("书").element("售价");
		price.getParent().remove(price);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/book.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	
	//更新第二本书的<作者>张孝祥</作者> 为<作者>GLBB</作者> 
	@Test
	public void update() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/book.xml"));
		Element author = document.getRootElement().elements("书").get(1).element("作者");
		author.setText("GLBB");
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/book.xml"),format);
		writer.write(document);
		writer.close();
	}
	
}

```

### XPATH快速定位到要的数据
#### 示例一
需要用到的jar包,其中jaxen和saxpath是支持jar
![Markdown](http://i1.bvimg.com/624208/bd4701c2bdcbb0d1.png)
```
package dom4jexercise;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class demo2 {
	
	/**
	 * 
	 * 应用Xpath提取数据
	 * 提取第一本书的作者值
	 * */
	public static void main(String[] args) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/book.xml"));
		
		String value = document.selectSingleNode("//作者").getText();
		System.out.println(value);
	}
}

```

### 利用Xpath实现对比操作
用于操作的XML文件
```
<?xml version="1.0" encoding="UTF-8"?>

<users>
	<user id="1" username="GLBB" passwd="123456"></user>
	<user id="2" username="nix" passwd="234567"></user>
</users>

```
java解析过程
```
package dom4jexercise;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class demo3 {

	/**
	 * 利用XML作为数据库 从XML读取出user 对比
	 */
	public static void main(String[] args) throws Exception {
		String username = "GLBB";
		String passwd = "123456";

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/users.xml"));

		Node node = document.selectSingleNode("//user[@username='" + username + "' and @passwd='" + passwd + "']");
		if (node == null) {
			System.out.println("---------------------用户名或密码错误----------------------");
		} else {
			System.out.println("------------------------登陆成功--------------------");
		}
	}
}

```






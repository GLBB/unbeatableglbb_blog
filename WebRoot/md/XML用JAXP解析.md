---
title: XML用JAXP解析
date: 2017-12-24 14:23:12
tags:
---
XML解析介绍
xml解析开发包
### Jaxp解析
1. Jaxp(sum)是JAVASE一部分，由javax.xml,org.w3c.dom,org.xml.sax包及其子包构成，在javax.xml.parsers包中定义了几个工厂类，可以得到XMLdom解析

下面是操作的XML文件
```
<?xml version="1.0" encoding="UTF-8"?>
<书架>
	<书>
		<书名 name="GLBB">JAVA就业培训教程</书名>
		<作者>张孝祥</作者>
		<售价>109.00元</售价>
		<售价>29.00元</售价>
	</书>
	<书>
		<书名 name="GLBB">JavaScript网页开发</书名>
		<作者>张孝祥</作者>
		<售价>28.00元</售价>
	</书>
</书架>
```
#### 对XML文件进行读操作
```
package xmlParse;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class demo1 {
	
	//对xml进行crud
	
	//对xml进行读取某个节点操作
	@Test
	public void read1() throws Exception{
		//创建工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//得到解析器
		DocumentBuilder builder = factory.newDocumentBuilder();
		//解析xml文档，得到代表的document
		Document document = builder.parse("src/book.xml");
		
		NodeList list = document.getElementsByTagName("书名");
		Node node = list.item(1);
		String content = node.getTextContent();
		System.out.println(content);
	}
	
	//遍历xml中所有的节点
	@Test
	public void read2() throws Exception{
		//创建工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//得到解析器
		DocumentBuilder builder = factory.newDocumentBuilder();
		//解析xml文档，得到代表的document
		Document document = builder.parse("src/book.xml");
		
		Node root = document.getElementsByTagName("书架").item(0);
		list(root);
	}
	
	public void list(Node node){
		if(node instanceof Element){
			System.out.println(node.getNodeName());
		}
		NodeList nodelist = node.getChildNodes();
		for(int i=0;i<nodelist.getLength();i++){
			Node child = nodelist.item(i);
			list(child);
		}
	}
	
	
	//得到xml某个标签的属性
	@Test
	public void read3() throws Exception{
		//创建工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//得到解析器
		DocumentBuilder builder = factory.newDocumentBuilder();
		//解析xml文档，得到代表的document
		Document document = builder.parse("src/book.xml");
		
		Element element = (Element) document.getElementsByTagName("书名").item(0);
		String str = element.getAttribute("name");
		System.out.println(str);
	}
}

```
#### 对XML文件进行写操作
javax.xml.transform包中有Transform类用于把代表的XML的Document对象转换成某种格式进行输出，如可把XML文档转化为Html文档，也可以把document对象重新写入XML文件中
Transform类通过transform方法完成该操作，该方法接受一个源和一个目的地，通过：
javax.xml.transform.dom.DOMSourse类来关联要转换的的Document对象，
javax.xml.transform.dom.DOMResult类来表示数据传送的目的地
Transform对象可通过TransformerFactory来获得

#### 添加元素
```
//添加元素
	@Test
	public void write1() throws Exception{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document sourseDocument = dbBuilder.parse("src/book.xml");
		
		Element price = sourseDocument.createElement("售价");
		price.setTextContent("53.00元");
		
		Element addPrice = (Element) sourseDocument.getElementsByTagName("书").item(0);
		addPrice.appendChild(price);
		
		TransformerFactory tfFactory = TransformerFactory.newInstance();
		Transformer tf = tfFactory.newTransformer();
		tf.transform(new DOMSource(sourseDocument), new StreamResult(new FileOutputStream("src/book.xml")));
	}
```

#### 在指定位置添加元素
```
//指定位置添加元素
	@Test
	public void write2() throws Exception{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		
		//得到DOM
		Document sourseDocument = dbBuilder.parse("src/book.xml");
		
		//要插入的节点
		Element price = sourseDocument.createElement("售价");
		price.setTextContent("53.00元");
		
		//在哪个节点插入该节点
		Element book = (Element) sourseDocument.getElementsByTagName("书").item(0);
		
		//在DOM中加入该节点
		Element relNode = (Element) book.getElementsByTagName("售价").item(0);
		book.insertBefore(price, relNode);
		
		TransformerFactory tfFactory = TransformerFactory.newInstance();
		Transformer tf = tfFactory.newTransformer();
		tf.transform(new DOMSource(sourseDocument), new StreamResult(new FileOutputStream("src/book.xml")));
	}
```

#### 给元素添加属性
```
//给元素添加属性
	@Test
	public void write3() throws Exception{
		DocumentBuilderFactory dbfBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbbuilder = dbfBuilderFactory.newDocumentBuilder();
		Document document = dbbuilder.parse("src/book.xml");
		
		Element element = (Element) document.getElementsByTagName("书名").item(0);
		element.setAttribute("author", "GLBB");
		
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
```

### 删除节点

```
@Test
	public void delete() throws Exception{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse("src/book.xml");
		
		//第一种方法
		//得到要删除的节点
	//	Element element = (Element) document.getElementsByTagName("售价").item(0);
		//得到要删除的父节点
	//	Element element2 = (Element) document.getElementsByTagName("书").item(0);
		//从父节点中移除该节点
	//	element2.removeChild(element);
		
		//第二种方法
		Element element = (Element) document.getElementsByTagName("售价").item(0);
		element.getParentNode().removeChild(element);
		
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
	}
```


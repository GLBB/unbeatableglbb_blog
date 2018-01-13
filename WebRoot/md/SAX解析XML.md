---
title: SAX解析XML
date: 2017-12-25 23:20:50
tags:
---
SAX采用事件处理的方式解析XML文件，利用SAX解析XML文档涉及两个部分:解析器和事件处理器
1. 解析器可以使用JAXP的API，创建出SAX的解析器，可以指定解析器去解析某个XML文档。
2. 解析器采用SAX方式解析某个XML文档，它只要解析到XML文档的一个组成部分，就会去调用事件处理器的一个方法，解析器在调用事件处理器方法时，会把当前解析到的XML文档内容作为方法的参数传递给事件处理器。
3. 事件处理器由程序员编写，程序员通过事件处理器方法的参数，可以得到SAX解析器解析的数据，从而决定对数据如何处理。


### 用SAX方式获取xml文本内容

用于操作的XML文件
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
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
解析XML文档内容，并打印到控制台
```
package day03;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class demo1 {
	// sax解析文档
	public static void main(String[] args) throws Exception {
		// 1.创建解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2.得到解析器
		SAXParser parser = factory.newSAXParser();
		// 3.得到读取器
		XMLReader reader = parser.getXMLReader();
		// 4.设置内容处理器
		reader.setContentHandler(new ListHanlder());
		// 5.读取XML文本内容
		reader.parse("src/book.xml");
	}
}

class ListHanlder implements ContentHandler {
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		System.out.print("<" + qName + " ");
		for (int i = 0; i < atts.getLength(); i++) {
			String att = atts.getQName(i);
			String value = atts.getValue(i);
			System.out.print(" "+att +"=\""+ value +"\"");
		}
		System.out.println(">");

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("</"+qName+">");
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.println(new String(ch,start,length));
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub

	}
	

	@Override
	public void setDocumentLocator(Locator locator) {

	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub

	}
}

```

### 获取某一标签的内容
```
package day03;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class demo2 {
	public static void main(String[] args) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		reader.setContentHandler(new GetContent());
		reader.parse("src/book.xml");
	}
}

class GetContent extends DefaultHandler{
	public String currentTag;
	//获取第几个标签的内容
	private int needNumber = 1;
	//当前的标签的计数
	private int nowNumber = 0;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentTag = qName;
		if("售价".equals(currentTag)){
			nowNumber++;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		currentTag = null;	
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//1.   "售价".equals(currentTag)
		//2.   currentTag.equals("售价")
		//currentTag可能等于null，所以第二种方式错的
		if("售价".equals(currentTag)&&nowNumber==needNumber){
			System.out.println(new String(ch,start,length));
		}
	}
}
```
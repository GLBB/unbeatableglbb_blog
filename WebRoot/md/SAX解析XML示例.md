---
title: SAX解析XML示例
date: 2017-12-25 23:21:52
tags:
---
### 读取xml文件，返回一个书得list
用于操作得xml文件
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
```
package day03;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class demo3 {
	public static void main(String[] args) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		BookListHandler handler = new BookListHandler();
		reader.setContentHandler(handler);
		reader.parse("src/book.xml");
		ArrayList<Book> bookList = handler.getBookList();
		System.out.println(bookList);
		System.out.println();
	}
}

class BookListHandler extends DefaultHandler {
	private ArrayList<Book> bookList = new ArrayList<>();
	private String currentTag;
	private Book book;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentTag = qName;
		if ("书".equals(currentTag)) {
			book = new Book();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if ("书名".equals(currentTag)) {
			String str = new String(ch, start, length);
			book.setBookName(str);
		} else if ("作者".equals(currentTag)) {
			String str = new String(ch, start, length);
			book.setAuthor(str);
		} else if ("售价".equals(currentTag)) {
			String str = new String(ch, start, length);
			book.setPrice(str);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("书")) {
			bookList.add(book);
			book = null;
		}
		currentTag = null;
	}

	public ArrayList<Book> getBookList() {
		return bookList;
	}


}
```
其中BooklistHandle中有许多坑，容易写错，可以在booklistHandle中startElement(),characters(),endElement()中得方法中加入断点调试
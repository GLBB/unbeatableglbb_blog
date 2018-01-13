---
title: jsp语法
date: 2018-01-07 19:51:52
tags:
---
### 脚本表达式
作用是向浏览器输出数据
```
<%=time%> 
```
相当于，在翻译后的servlet中可以看到
```
out.print(time);
```
### 脚本片段
```
    <% 
    	String x = "是";
    %>
    <p>问是否此山最高</p>
    <% 
    	out.print(x+"<br/>");
    %>
    <br/>
```
可以嵌如多行java代码
单个脚本片段中java语句可以是不完成的，但多个脚本片段组合起来必须是完整的
```
    <% 
    	for(int i=0;i<9;i++){
    %>
    		我最高<br/>
    <% 
    	}
    %>
```
由此可以看出jsp页面确实不适合处理请求

### jsp声明
jsp页面默认声明的代码默认会翻译到jspService方法中，
但jsp声明中的语句会放在jspService方法之外
```
    <%! 
    	public void run(){
    		
    	}
    %>
```
java语法认为方法里面不能在声明一个方法的
也就是说jspService方法中不能在加入run方法，jsp声明会将代码块里面的东西放到jspService方法之外

### jsp注释
jsp注释不会发送给浏览器，html注释中注释的内容会发给浏览器，但浏览器识别不显示
格式
```
<%--
    	saaaaa
--%>
```

### jsp指令
jsp指令是为jsp引擎设计的，并不产生任何可见输出，只是告诉引擎如何处理JSP页面的其余部分
如page指令
```
<%@ 指令 属性名="值" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
```
如果一个指令有多个属性，这多个属性可以写在一个指令中，也可以分开写
```
<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.Date" %>
//也可以写作
<%@ page contentType="text/html;charset=gb2312" import="java.util.Date"  %>
```

### page指令
page指令用于定义jsp页面的各种属性,无论page指令出现在jsp页面哪个位置，作用的都是整个jsp页面，为了页面的可读性，常定义在jsp页面的起始位置
jsp page 的import属性，默认导入了如下包
```
java.lang.*
javax.servlet.*
javax.servlet.jsp
javax.servlet.http
```
一个import属性可以导入多个包,不同的包用逗号分割
```
import="java.util.Date,java.sql.*，java.io.*"
```
也可以分为多行写

还有属性
```
errorPage="/errors/error.jsp"
```
定义jsp出错后跳转到哪个页面，其中路径必须是相对路径目录
如果以"/"开头，表示相对于当前WEB程序根目录，否则表示当前页面
可以在web.xml文件中使用&lt;error-page&gt; 元素为整个web应用程序设置处理错误页面，其中&lt;exception-type&gt; 子元素指定异常类的完全限定名，&lt;location&gt; 元素指定以“/”开头的错误处理页面
例子
在web.xml文件中配置全局的
```
  <error-page>
  	<exception-type>java.lang.ArithmeticException</exception-type>
  	<location>/errors/error.jsp</location>
  </error-page>
```
或者在jsp文件中，配置Jsp文件的
```
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" errorPage="/errors/error.jsp"%>
```
其中jsp page 中errorPage属性的优先级比在web.xml中文件配置的更高

page中还有一个属性，isErrorPage,默认值是false,作用是对异常页面显式说明，声明这个属性，jsp翻译成的servlet中就会内置一个exception对象

pageEncoding和contentType是解决乱码相关的
pageEncoding设置tomcat服务器以声明码表来把jsp翻译成servlet，contentType是指定浏览器以什么码表打开
一般来说只需设置pageEncoding属性就可以了

### include指令
include指令用于引入其他jsp页面，如果使用include指令引入了其他jsp页面，那么jsp引擎将把两个jsp页面翻译为一个servlet,所以include指令引入通常称为静态引入
语法
```
<%@ include file="relativeURL" %>
```
其中file属性用于指定被引入文件路径，路径以"/"开头，代表当前web应用

例子
页头
```
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
这是页头<br/>
```
页脚
```
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
这是页脚<br/>
```
内容
```
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>include指令</title>
  </head>
  
  <body>
  		<%@ include file="/public/head.jsp" %>
    	这是内容<br/>
    	<%@ include file="/public/foot.jsp" %>
  </body>
</html>
```
注意被包含进来的直接写内容，不需要有&lt;html&gt; 这些标签，否则格式不良好

### include包含
include指令包含成为静态包含，它包含的所有jsp会翻译成一个servlet，编译时包含
其中
```
    <%
    	request.getRequestDispatcher("/public/head.jsp").include(request, response);
    %>
    
    <% 
    	response.getWriter().write("这是内容<br/>");
    %>
    
    <% 
    	request.getRequestDispatcher("/public/foot.jsp").include(request, response);
    %>
```
称为动态包含，动态包含会把包含进来的各自翻译成servlet，jsp运行的时候把这些包含进来了，称为动态包含
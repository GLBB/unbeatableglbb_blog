---
title: 手写一个servlet程序
date: 2017-12-29 14:31:19
tags:
---
在tomcat的webapps目录中新建一个WEB应用
编写servlet
```
package cn.itcast;

import java.io.*;
import javax.servlet.*;

public class FirstServlet extends GenericServlet{
	public void service(ServletRequest req,ServletResponse res)throws ServletException,IOException{
		OutputStream os = res.getOutputStream();
		os.write("Hello servlet".getBytes());
	}
}
```

将servlet 的jar包加入编译路径
```
set classpath=%classpath%;E:\tomcat\apache-tomcat-7.0.82\lib\servlet-api.jar
```

编译FirstServlet
```
javac -d . FirstServlet.java
```
配置访问路径、
建立web.xml文件
在WEB-INF目录下新建web.xml文件

web.xml里面写入
```
<?xml version="1.0" encoding="ISO-8859-1"?>

<web-app xmlns="http://java.sun.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                      http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
  version="3.0">
	<servlet>
        <servlet-name>FirstServlet</servlet-name>
        <servlet-class>cn.itcast.FirstServlet</servlet-class>
    </servlet>
	
	<servlet-mapping>
        <servlet-name>FirstServlet</servlet-name>
        <url-pattern>/FirstServlet</url-pattern>
    </servlet-mapping>

</web-app>
```
打开tomcat服务器
打开浏览器输入
```
http://localhost:8080/day04/FirstServlet
```
即可查看输出
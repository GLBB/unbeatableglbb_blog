---
title: jsp之pageContext对象和常用标签和映射
date: 2018-01-08 11:42:20
tags:
---
pageContext对象代表jsp页面的运行环境
1. 封装了其他八大对象的引用
2. 自身是一个域对象，可以用来保存数据
3. 该对象还封装了web开发中的一些常用操作，例如引入和跳转到其他资源，检索其他域对象中的属性

pageContext主要是用在自定义标签

pageConxtext可以获得其他域对象的值
```
	<%
		request.setAttribute("data", "AAA");
		String data = (String) pageContext.getAttribute("data", PageContext.REQUEST_SCOPE);
		out.write(data);
	%>
```

使用findAttibute方法可以获得在四个域对象中的某一个值
该方法先在pageContext，然后在request，再在session，最后在application域中找，找到了返回值，没找到返回空

pageContext还可以跳转到其他页面的forward方法，还有include包含方法

### jsp常用标签
#### 跳转
跳转方法
```
    <jsp:forward page="/index.jsp"></jsp:forward>
```
用法原因
配置首页，使用mvc开发时，请求都是交给servlet，必须要把一个servlet设为首页，但在web.xml中
```
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
  </welcome-file-list>
```
配置servlet为首页是无效的，所以可以设置一个jsp为首页，在跳转到servlet中处理请求

#### 包含
```
<jsp:include page="/1.jsp"></jsp:include>   <!-- pageContext.include()  动态包含 -->
```
#### 参数
```
    <jsp:forward page="/servlet/ServletDemo1">
    	<jsp:param value="GLBB" name="username"/>
    </jsp:forward>
```
跳转的时候带参数过去，可以在servlet中使用request的getParameter方法的得到值
还可以带脚本表达式中的值
```
    <% 
    	int i = 1;
    %>
    <jsp:forward page="/servlet/ServletDemo1">
    	<jsp:param value="<%=i %>" name="username"/>
    </jsp:forward>
```
## 映射
把jsp映射到另一个路径上去
在web.xml文件中，可以
```
  <servlet>
  	<servlet-name>other</servlet-name>
  	<jsp-file>/10.jsp</jsp-file>
  </servlet>

	<servlet-mapping>
		<servlet-name>other</servlet-name>
		<url-pattern>/15.html</url-pattern>
	</servlet-mapping>
```



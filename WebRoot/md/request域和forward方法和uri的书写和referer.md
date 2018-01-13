---
title: request域和forward方法和uri的书写和referer
date: 2018-01-04 21:12:00
tags:
---
mvc模式
m : model(javabean)
v : view(jsp)
c : control(servlet)
域代表一个容器

### request实现消息的转发 
servlet中
```
		String data = "aaaaaa";
		request.setAttribute("data", data);
		
		request.getRequestDispatcher("/message.jsp").forward(request, response);
```
jsp中
```
  	<%
  		String data = (String)request.getAttribute("data");
  		out.write(data);
  	%>
  	${data }  //el表达式
```


### 输出流关闭后，不能实现request的跳转
1. 如果在调用forward之前，servlet程序中写入部分已经真正传送到客户端，forward方法将抛出illegleStateException，注意：在forward之后加return;
2. 在调用forward方法前，向servlet的缓冲区中（response）写入了内容，写入到缓冲区的还没有真正输出到客户端，forward方法可以正确执行，原来写入到缓存区的内容将被清空，但是，已写入到HttpServletResponse对象的响应字段信息保持有效。

### uri的书写
写uri首先考虑是浏览器调用还是服务器调用，若是服务器调用，则第一个/代表webroot这个目录，若是浏览器调用则写web应用下，下例：、
浏览器调用
```
response.sendRedirect("/day06/index.jsp");
```
服务器调用
```
RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
```
windows访问硬盘上的资源： \

### 其中referer头代表从哪里来的，从哪个页面来的

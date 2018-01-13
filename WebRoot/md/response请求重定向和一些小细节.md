---
title: response请求重定向和一些小细节
date: 2018-01-03 23:30:14
tags:
---
示例一
```
response.setStatus(302);
response.setHeader("location", "/day06/index.jsp");

//以上两行代码可用下面一句代码替代
//response.sendRedirect("/day06/index.jsp");
```
重定向特点
1. 浏览器向服务器发送两次请求，意味着有两对request/response
2. 重定向浏览器地址栏会发生变化
3. 用户登陆和显示购物车时常用重定向

#### 其中关于response的一些细节
1. response.getWriter()和response.getOutputStream()方法互斥，在一个response中只能调用其中之一，不能两个都调用,否者会抛异常
2. 通过response.getWriter()和response.getOutputStream()得到的输出流不需要关闭，在运行结束service()方法后，服务器在摧毁response对象时，服务器会自动关闭response的输出流

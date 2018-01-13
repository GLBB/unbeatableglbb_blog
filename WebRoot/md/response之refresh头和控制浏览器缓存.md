---
title: response之refresh头和控制浏览器缓存
date: 2018-01-03 21:22:41
tags:
---

用refresh头控制浏览器每隔三秒刷新
```
response.setHeader("refresh", "3");			
String data = new Random().nextInt()+"";
response.getWriter().write(data);
```

用refresh头控制浏览器跳转到指定页面
```
response.setContentType("text/html;charset=utf-8");
response.setHeader("refresh", "3;url='/day06/index.jsp'");
response.getWriter().write("3秒后跳转到首页，如没成功点击链接<a href='/day06/index.jsp'>首页</a>");
```

现转发到message.jsp把信息以良好的样式输出出来，隔三秒在跳转到首页
```
		String message = "<meta http-equiv='refresh' content='3;url=/day06/index.jsp'>3秒后跳转到首页，如未跳转请点击<a href='/day06/index.jsp'></a>";
		this.getServletContext().setAttribute("message", message);
		this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
```


### 控制浏览器缓存
```
response.setDateHeader("expires", System.currentTimeMillis()+1000*3600);
String data = "aaaaaaaaaaaaaaaaaaaaaaa";
response.getWriter().write(data);
```
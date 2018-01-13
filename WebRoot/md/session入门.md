---
title: session入门
date: 2018-01-06 13:45:21
tags:
---
#### servlet1中
session被创建出来，且设置属性awesome
```
HttpSession session = request.getSession();
session.setAttribute("awesome", "GLBB");
```
#### servlet2中
得到session的属性
```
HttpSession session = request.getSession();
String awesome = (String) session.getAttribute("awesome");

		response.setContentType("text/html;charset=utf-8");
PrintWriter pw = response.getWriter();
pw.write("世界上最英俊的人是谁：" + awesome);
```

session是服务器为每个浏览器创建的
由服务器进行管理，session周期由request.getSession创建，默认在30分钟内没用就被服务器销毁，即使会话没有结束，只要30分钟没有使用session服务器就自动销毁session

可以在web.xml文件中根节点下加入
```
  <session-config>
  	<session-timeout>10</session-timeout>
  </session-config>
```
其中10代表10分钟没用session就销毁

也可以在程序中加入
```
session.invalidate();
```
会马上摧毁session

```
request.getSession(false);
requset.getSeession();
```
重载方法，第一句代码只获取session,不创建session
第二句代码获取session，若没获取到，则创建session

### 服务器怎么知道哪个session对应哪个浏览器
在执行到request.getSession()方法时，创建出一个session,每个session有一个id号，通过cookie把id号写给浏览器，下次浏览器访问服务器，把cookie取出来给服务器，服务器就知道哪个session对应哪个浏览器，所以说:session是基于cookie的，其中cookie写给浏览器，cookie是没有设置有效期的，也就是说，浏览器关了，cookie就没有了，所以说需要手动把这个cookie设置一个有效期
在html页面设置两个链接
```
    <a href="/day07/servlet/sessiondemo1">设置这世界最英俊的人</a>
   	<br/>
   	<a href="/day07/servlet/sessiondemo2">查看这世界最英俊的人</a>
```
servlet1设置session数据
```
		HttpSession session = request.getSession();
		session.setAttribute("awesome", "GLBB");
		
		String id = session.getId();
		Cookie cookie = new Cookie("JSESSIONID",id);
		cookie.setMaxAge(30*60);
		cookie.setPath("/day07");
		response.addCookie(cookie);
```
servlet2得到session数据


### 浏览器阻止接收cookie
（浏览器对主机名localhost不阻止），把localhost改成127.0.0.1阻止，
解决方案：URL重写
```
		request.getSession();
		
		String url1 = response.encodeURL("/day07/servlet/sessiondemo1");
		String url2 = response.encodeURL("/day07/servlet/sessiondemo2");
		
		PrintWriter pw = response.getWriter();
		pw.write("<a href="+url1+">设置这世界最帅的人</a><br/>");
		pw.write("<a href="+url2+">查看这世界最帅的人</a><br/>");
```
则在解析后的url后面会带上sessionid号，若浏览器开启了cookie，那么，url后面不会带sessionid号。但是若用户阻止了cookie，启用url方式，用户将浏览器关闭后，就无法保存用户的操作

#### uri路径不写死
如
```
String = "/day07/servlet/sessiondemo2"
```
可以改成
```
String = request.getContextPath()+"/servlet/sessiondemo2"
```
其中request.getContextPath()就相当于/day07
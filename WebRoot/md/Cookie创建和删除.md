---
title: Cookie创建和删除
date: 2018-01-05 16:53:38
tags:
---
### 常用方法
setMaxAge()用来设置cookie的有效期，若不调用这个方法，则cookie有效期是浏览器进程
setPath()设置cookie的有效目录，若设置/day06意味着等会去访问服务器，在/day06下所有的资源都会带着cookie,若不调用这个方法，则这个cookie是由哪个servlet发出去的，则访问它所在的有效目录才带cookie，如该cookie是由/day06/servlet/servletDemo1,则有效目录是/day06/servlet/
setDomain()方法为第三方主机设置一个cookie，但常被浏览器拒收

例子，利用cookie显示上次登陆的时间
```
package cn.cqut.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cookiedemo1 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw = response.getWriter();
		pw.print("你上次登陆的时间是：");

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("lastAcessTime")) {
					long cookieValue = Long.parseLong(cookies[i].getValue());
					Date date = new Date(cookieValue);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
					pw.print(dateFormat.format(date)+"  ");
				}
			}
		}
		
		Cookie cookie = new Cookie("lastAcessTime",System.currentTimeMillis()+"");
		cookie.setMaxAge(30*24*3600);
		cookie.setPath("/day07");
		
		response.addCookie(cookie);
	}

}
```
### 删除名为lastAcessTime的cookie
在cookiedemo1中添加一个链接到cookiedemo2后
```
package cn.cqut.cookie;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cookiedemo2 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//删除cookie
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = new Cookie("lastAcessTime",System.currentTimeMillis()+"");
		//给出以秒为单位的有效时间，0代表删除
		cookie.setMaxAge(0);
		
		//路径名必须要和删除的cookie设置的路径一样才能删除
		cookie.setPath("/day07");
		
		response.addCookie(cookie);
		
		response.sendRedirect("/day07/servlet/cookiedemo1");
	}
}
```
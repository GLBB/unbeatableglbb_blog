---
title: request常用方法
date: 2018-01-04 00:19:48
tags:
---
URL: http://www.sina.com/news/1.html
URI: /news/1.html 
URI用来标示某一个资源
URL用来标示互联网上某一个资源
```
		System.out.println(request.getRequestURI());
		
		System.out.println(request.getRequestURL());
		
		//获得查询参数
		System.out.println(request.getQueryString());
		
		//得到客户端远程访问ip地址
		System.out.println(request.getRemoteAddr());
		
		//得到远程访问客户端主机名（在dns服务器上注册），若未注册，即返回远程ip地址
		System.out.println(request.getRemoteHost());
		
		//得到远程访问的客户机端口号，浏览器占用的端口号
		System.out.println(request.getRemotePort());
		
		//方法返回WEB服务器主机名
		System.out.println(request.getLocalName());
		
		//获得客户机请求方式
		System.out.println(request.getMethod());
```
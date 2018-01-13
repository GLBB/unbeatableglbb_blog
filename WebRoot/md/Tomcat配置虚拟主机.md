---
title: Tomcat配置虚拟主机
date: 2017-12-27 20:52:46
tags:
---
如需在WEB服务器中配置一个网站，需使用Host元素进行配置，配置的主机想要被外接访问，必须在DNS服务器或WINDOWS服务器中配置
```
<Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">
```
默认是localhost
配置一个www.unbeatableglbb.com主机
```
<Host name="www.unbeatableglbb.com" appBase="F:\tomcatpractice\unbeatableglbb">
		<Context path="/mail" docBase="F:\tomcatpractice\unbeatableglbb\mail"/>
	  </Host>
```
appBase是项目实际所在路径
同时配置了一个comtext WEB应用

但此时打开浏览器并不能访问到http://www.unbeatableglbb.com/mail/1.html

下面是访问网站流程

WEB访问baidu流程
![Markdown](http://i4.bvimg.com/624208/1e8bf720ee4d5855.png)

想要知道一个主机的Ip，可以通过ping命令查看IP地址
![Markdown](http://i4.bvimg.com/624208/8f1517a492e2e8a5.png)

真正web访问过程
![Markdown](http://i4.bvimg.com/624208/694cf95ff1e78e87.png)


在hosts文件所在位置
```
C:\Windows\System32\drivers\etc
```
在hosts中加入
```
127.0.0.1 www.unbeatableglbb.com
```

则可通过浏览器http://www.unbeatableglbb.com:8080/mail/1.html访问到


### 直接通过主机访问到mail/1.html
在server.xml中
```
<Host name="www.unbeatableglbb.com" appBase="F:\tomcatpractice\unbeatableglbb">
		<Context path="" docBase="F:\tomcatpractice\unbeatableglbb\mail"/>
	  </Host>
```
将path设置为空，设置为默认web应用
在把默认的web应用设置一个默认的首页
方法 在F:\tomcatpractice\unbeatableglbb\mail中新建一个WEB-INF目录，然后在WEB-INF目录中新建web.xml文件
---
title: WEB应用组成结构
date: 2017-12-21 00:36:45
tags:
---
如图，图引用自http://blog.csdn.net/friendan/article/details/8117291
![Markdown](http://i2.bvimg.com/624208/cd287fba3a2bf9f4.png)

WEB-INF下应有个web.xml文件对web应用进行配置
如何配置web.xml文件
例：配置某个页面为首页
 1. 打开tomcat下webaaps目录复制粘贴其内容到web.xml文件
 复制如下内容
 
```
<?xml version="1.0" encoding="UTF-8"?>

  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
  version="4.0"
  metadata-complete="true">

<welcome-file-list>
        <welcome-file>index.html</welcome-file>
</welcome-file-list>
</web-app>
```
其中<welcome-file-list>为设置首页

### 将某个页面配置成网站首页
1. 插入上述文档头
2. 插入标签<welcome-file-list>
	```
	<welcome-file-list>
        <welcome-file>index.html</welcome-file>
	</welcome-file-list>
	```
	其中index.html为指定的作为WEB应用的首页
3. 在tomcat的conf/Catalina/localhost目录下新建ROOT.xml
4. 输入如下代码
```
<Context docBase="F:\blog"/>
```
重要！！！ 
此种需WEB项目不能放在webapps中

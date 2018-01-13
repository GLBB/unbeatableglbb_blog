---
title: tomcat之war包和reloadable属性
date: 2017-12-28 11:19:13
tags:
---
### 如何打成一个war包
windows下cmd进入WEB应用所在目录
如unbeatableglbb下的news WEB应用
就进入unbeatableglbb
输入如下命令
```
jar cvf news.war news
```

### 为什么需要打成war包
服务器认识war包，会进行自动解压，放在tomcat下的webapss目录下

### 配置tomcat中Context元素中的reloadable属性
配置tomcat中Context元素中的reloadable属性，让tomcat自动加载更新后的web应用
```
<Host name="www.unbeatableglbb.com" appBase="F:\tomcatpractice\unbeatableglbb">
		<Context path="" docBase="F:\tomcatpractice\unbeatableglbb\mail" reloadable="true"/>
</Host>
```
并不建议在大型项目开发中配置这个属性，原因是程序如果很大，若一改动tomcat就会自动加载，频繁改动，那就会发生很多错误

#### 在tomcat目录下的conf/context.xml中配置的Context元素被所有WEB应用共享,若在这里配置reloadable属性，意味着服务器里面的web应用自动加载
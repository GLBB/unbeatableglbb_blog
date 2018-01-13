---
title: tomcat入门学习二
date: 2017-12-19 20:12:50
tags:
---
## WEB应用配置

### WEB应用配置
#### 虚拟目录映射
（1）需要重启WEB服务器
打开conf下server.xml
在<host>中插入<Context>标签
```
<Context path="/blogproject" docBase="f:\blog"/>
```
path代表虚拟目录，即用浏览器所访问的目录
即http://www.glbb.com/blogproject
docBase代表项目实际所在的目录
改动server.xml之后必须重启web服务器
(2)不需重启WEB服务器上线WEB应用
tomcat官方文档
```
In individual files (with a ".xml" extension) in the $CATALINA_BASE/conf/[enginename]/[hostname]/ directory. The context path and version will be derived from the base name of the file (the file name less the .xml extension). This file will always take precedence over any context.xml file packaged in the web application's META-INF directory.
```
在tomcat的conf/Catalina/localhost目录下新建一个文件
如a.xml  
其中文件名称（如a）除去.xml扩展名被用作path路径的值,即虚拟目录
在a.xml中键入·
```
<Context docBase="f:\javaee"/>
```
多级虚拟目录
例子：文件名称foo#bar代表虚拟目录foo/bar
(3)把某个WEB应用配置为缺省应用
如直接输入主机IP进入某个WEB应用
在tomcat的conf/Catalina/localhost目录下创建文件名为ROOT.xml
需要重启服务器来覆盖原来的默认WEB应用
(重要！！！)此种方法需WEB应用不在webapps目录内
原来http://localhost/blogproject/1.html
现在http://localhost/1.html
(4)在conf/server.xml中把WEB应用配置为缺省的WEB应用
`<Context path="" docBase="f:\blog"/> `
即path什么都不写
(5)让tomcat自动映射
在webapps目录下，tomcat会自动映射

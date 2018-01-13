---
title: tomcat入门学习一
date: 2017-12-18 23:04:47
tags:
---
### 下载tomcat之后
开启tomcat必须配置javahome环境变量

### tomcat web服务从默认的8080端口改为80端口
1. 打开tomcat 里面conf目录里的server.xml文件
2.  找到如下代码(连接器)
	```
	<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
   ```
   改为
   ```
	<Connector port="80" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
   ```
3. 启动tomcat服务
4. 若启动不成功查看是否有程序已经占用80端口
5. 我80端口被httpd占用
	通过netstat -apn|grep "80"发现我端口被httpd服务占用
6. 通过systemctl stop httpd.service关闭httpd服务
7. 重启tomcat成功


### web入门辨识
- url      例子: http://www.sina.com/
- 主机名      例子: www.sina.com
- 域名     例子：sina.com

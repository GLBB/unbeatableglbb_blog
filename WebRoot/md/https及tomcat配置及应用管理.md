---
title: https及tomcat配置及应用管理
date: 2017-12-28 16:14:08
tags:
---
### 对称加密
通过相同的密码序列进行加密解密

#### 非对称加密
产生公钥私钥
公钥加密的数据私钥解，私钥加密的数据公钥解密

（代号）小东将公钥发给CA担保，生成数字证书，发给代号（小明）

小明也生成一对公钥私钥，用小东数字证书加密，再对数据摘要进行加密，生成数字签名，也称数据指纹

数字签名通过md5算法生成

### tomcat配置加密服务器
在cmd中使用java工具keytool
在cmd中输入
```
keytool -genkey -alias tomcat -keyalg RSA
```
设置密码
在姓氏和名字填写服务器主机名称
如 www.unbeatableglbb.com
其他可以回车默认
在cmd的工作目录下可以看见.keystore文件
复制粘贴到conf文件夹下，
然后打开server.xml插入一个连接器
```
<Connector port="8443" protocol="org.apache.coyote.http11.Http11Protocol"
               maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
               clientAuth="false" sslProtocol="TLS" keystoreFile="conf/.keystore" keystorePass="****"/>
```
keystoreFile是.keystore所在文件夹
keystorePass是建立.keystore文件是输入的密码
重启服务器

### tomcat应用管理
参考
```
https://jingyan.baidu.com/album/fea4511a1c624ef7bb9125ec.html?picindex=6
```

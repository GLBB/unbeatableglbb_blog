<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆</title>
    <link rel="stylesheet" href="css/login.css">
    <script src="js/login.js"></script>
  </head>
  <body>
		<section>
			<div id="mode"><font id="loginMode" onclick="logi()">登陆</font><font id="registerMode" onclick="reg()">注册</font></div>
			<div id="login">
				<form method="post" action="/Blog/servlet/Login">
					<font>用户:</font>
					<input type="text" name="user"/><br />
					<font>密码:</font>
					<input type="password" name="password"/><br />
					<input type="submit" value="登陆"/>
				</form>
			</div>
			
			<div id="register">
				<form method="post" action="/Blog/servlet/Register">
					<font>用户:</font>
					<input type="text" name="user"/><br />
					<font>密码:</font>
					<input type="password" name="password"/><br />
					<input type="submit" value="注册"/>		
				</form>
			</div>
		</section>
	    
  </body>
</html>

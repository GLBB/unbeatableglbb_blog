<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>��½</title>
    <link rel="stylesheet" href="css/login.css">
    <script src="js/login.js"></script>
  </head>
  <body>
		<section>
			<div id="mode"><font id="loginMode" onclick="logi()">��½</font><font id="registerMode" onclick="reg()">ע��</font></div>
			<div id="login">
				<form method="post" action="/Blog/servlet/Login">
					<font>�û�:</font>
					<input type="text" name="user"/><br />
					<font>����:</font>
					<input type="password" name="password"/><br />
					<input type="submit" value="��½"/>
				</form>
			</div>
			
			<div id="register">
				<form method="post" action="/Blog/servlet/Register">
					<font>�û�:</font>
					<input type="text" name="user"/><br />
					<font>����:</font>
					<input type="password" name="password"/><br />
					<input type="submit" value="ע��"/>		
				</form>
			</div>
		</section>
	    
  </body>
</html>

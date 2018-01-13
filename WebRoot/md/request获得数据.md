---
title: request获得数据
date: 2018-01-04 18:42:00
tags:
---
```
//获得某个头的一个值
		String value = request.getHeader("Accept");
		System.out.println(value);
		
		//获得某个头的所有值
		Enumeration<String> enumration = request.getHeaders("Accept");
		while(enumration.hasMoreElements()){
			String values = enumration.nextElement();
			System.out.println(values);
		}

// 获取所有头的值
		Enumeration<String> enumration2 = request.getHeaderNames();
		while (enumration2.hasMoreElements()) {
			String name = enumration2.nextElement();
			String value2 = request.getHeader(name);
			System.out.println(name + " : " + value2);
		}
```

通过a标签给servlet提供数据
```
<a href="/day06/servlet/requstDemo2?username=GLBB">点我发送username给服务器</a>
```
servlet获得数据a标签中的数据
```
		String value = request.getParameter("username");
		System.out.println(value);
```

### servlet获得表单用post方法提交的数据
在servlet中覆盖父类doPost方法
```
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + " : " + value);
		}
	}
```
下面模拟html中用post方法提交表单
```
    <form action="/day06/servlet/requstDemo2" method="post" >
			用户名 ：<input type="text" name="username"/>
			密码： <input type="text" name="passwd" />
			<input type="submit" value="提交"/>
	</form>
```

### 使用beanutils填充表单数据到user类

加入beanutils和logging的jar包

html页面
```
<form action="/day06/servlet/requstDemo2" method="post" >
			用户名1 ：<input type="text" name="username"/><br/>
			用户名2： <input type="text" name="username"/><br/>
			密码： <input type="text" name="passwd" /><br/>
			<input type="submit" value="提交"/><br/>
	</form>
```
user类,对这两个属性的get和set方法
```
String[] username;
String passwd;
```
servlet中
```
Map<String,String[]> map = request.getParameterMap();
		
		User user = new User();
		
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user.getUsername()[0]+" "+user.getUsername()[1]+" "+user.getPasswd());
```

### 获得各种表单提交的数据
```
<form method="post" action="/day06/servlet/requestDemo3">
			用户名 ：<input type="text" name="username"/>
			密码： <input type="password" name="passwd" />
			性别：
				<input type="radio" name="gender" value="male"/>男
				<input type="radio" name="gender" value="female" />女
				<br />
			所在地：
				<select name="city">
					<option value="beijing">北京</option>
					<option value="shanghai">上海</option>
					<option value="chongqing">重庆</option>
				</select>
				<br />
			爱好：
				<input type="checkbox" name="hobby" value="basketball"/>篮球
				<input type="checkbox" name="hobby" value="football"/>足球
				<input type="checkbox" name="hobby" value="pingqang"/>乒乓球
				<input type="checkbox" name="hobby" value="dance"/>跳舞
				<br />
			备注：<textarea cols="60" rows="6" name="descreption"></textarea><br />
			<input type="hidden" name="id" value="GLBB"/>
			
			<input type="submit" value="提交"/>
		</form>
```
servlet中
```
System.out.println(req.getParameter("username"));
		System.out.println(req.getParameter("passwd"));
		System.out.println(req.getParameter("gender"));
		System.out.println(req.getParameter("city"));
		String[] hobbies = req.getParameterValues("hobby");
		for (int i = 0; hobbies != null && i < hobbies.length; i++) {
			System.out.print(hobbies[i]+" ");
		}
		System.out.println(req.getParameter("descreption"));
		System.out.println(req.getParameter("id"));
```
其中提交中文会产生乱码问题，原因是设置网页以utf-8打开，网页提交就以utf-8提交，但是response中是以iso-8859-1码表来存储，所以若想提交中文，可以设置response的码表，设置
```
req.setCharacterEncoding("utf-8");
```
但上面这句代码只对post方式提交的数据有效，用get方式提交的无效

若get方式提交出现乱码解决方法(测试时我没出现)
```
username = new String(username.getBytes("iso8859-1"),"utf-8");
```

还可以更改tomcat服务器配置
在conf目录下打开server.xml文件
在Connector标签中加一个属性
URIEncoding="UTF-8"
或属性
useBodyEncodingForURI="true"
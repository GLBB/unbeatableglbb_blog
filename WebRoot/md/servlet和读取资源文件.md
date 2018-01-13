---
title: servlet和读取资源文件
date: 2018-01-01 19:23:11
tags:
---
1. 配置文件web.xml文件更改，自动加载
2. 一个servlet可以映射到多个url地址，可以配置多个&lt;servlet-mapping&gt;
3. <servlet-mapping>中可以配置通用符， &lt;servlet-mapping&gt;/ *&lt;servlet-mapping&gt;通配符匹配，或 &lt;servlet-mapping&gt;\*.sh&lt;servlet-mapping&gt;
4. 启动服务器时就启动servlet可以配置一个标签&lt;load-on-starup&gt;1&lt;/load-on-starup&gt;
5. servlet线程安全问题,可以使用同步方法获得把类实现接口SingleThreadModel标记接口，这样当有多个访问时，若一个servlet还没处理完上一个客户机的请求，就会新创建一个servlet
6. java中子类在覆盖父类的方法时，不能抛出比父类更多的异常

### servletConfig对象
servletConfig对象可以读取web.xml servlet &lg;init-param&gt;里面配置的参数
```
<init-param>
    	<param-name>name</param-name>
    	<param-value>GLBB</param-value>
</init-param>
```
```
String value = conf.getInitParameter("name");
response.getOutputStream().write(value.getBytes());
```
### ServletContext对象
servletContext域
1. 这是一个容器
2. servletContext域说明了这个容器的范围，也就是应用程序范围
一个servlet可以通过servletContext的setAttribute方法设置一个属性，另一个servlet可以同过getAttribute方法得到这个值

#### 为整个应用程序配置参数
可以通过在web.xml中添加
```
<context-param>
	<param-name>name</param-name>
	<param-value>glbb<param-value/>
<context-param>
```
web服务器在加载这个web应用时，会将以上参数封装到servletContext对象中
可以通过调用getInitParameter方法来获得参数的值

### servlet进行请求的转发
servlet中
```
String str = "1111111111111111";
this.getServletContext().setAttribute("str", str);
		
RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
rd.forward(request, response);
```

jsp中
```
    <%
    	if(application.getAttribute("str")!=null){
    		String str = (String)application.getAttribute("str");
    		out.write(str);
    	}
    %>
```

其中用this.getServletContext().setAttribute("str", str);来保存数据，有问题对于当访问密集时，容易造成数据紊乱

#### 利用servletContext读取资源文件
其中资源文件在src目录下
servlet中
```
InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
		Properties property = new Properties();
		property.load(is);
		
		String url = property.getProperty("url");
		String user = property.getProperty("user");
		String password = property.getProperty("password");
		
		response.getWriter().write(url+" "+user+" "+password);
```
properties配置文件中
```
url=jdbc:mysql://localhost:3306/test
user=glbb
password=123456
```

其中这是一个web项目，读取文件时如用以前的传统方式
```
FileInputSteam fis = new FileInputStream("classes/db.properties");
```
其中url为相对路径，相对的是启动java虚拟机的目录
即bin目录下的starup.bat文件所在目录相对目录

也可以通过servletContext的getRealPath方法
```
String path = this.getServletContext().getRealPath("/WEB-INF/classes/db.properties");
FileInputSteam fis = new FileInputStream(path);
```

#### 普通java程序读取资源数据
可以通过类加载器来读取资源文件，其中文件路径名以src目录为基准，如db.properties是在src目录下，即
```
InputStream is = Dao.class.getClassLoader().getResourceAsStream("db.properties");
```

其中Dao，data acess object中有许多如update,delete,add这些方法都需加载资源文件,所以资源文件加载可以放到一个静态代码块

通过类加载器读取文件，文件不可以太大，若文件太大，类加载器把文件全部加载到内存中，容易导致jvm内存溢出
还有：类加载器和静态代码块只会执行一次，若对资源文件进行更改，想要访问最新的资源可
```
String path = Dao.class.getClassLoader().getResource("db.properties").getPath();
		try {
			FileInputStream fis = new FileInputStream(path);
			Properties prop = new Properties();
			prop.load(fis);
			System.out.println(prop.getProperty("url"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
```
用类装载方式获得文件位置
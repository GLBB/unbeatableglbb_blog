---
title: servlet之response输出和文件下载
date: 2018-01-03 18:36:15
tags:
---
```
		String str = "中国";
		
		OutputStream os = response.getOutputStream();
		os.write(str.getBytes());
```
可以成功显示原因：
向response中写数据和浏览器读数据采用的码表都是gb2312，或其他相同的码表
设置都用utf-8码表
```
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		String str = "中国";
		
		OutputStream os = response.getOutputStream();
		os.write(str.getBytes("UTF-8"));
```

用&lt;meta&gt;方式，不同的浏览器支持不一样
```
		String str = "中国";
		
		OutputStream os = response.getOutputStream();
		os.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'>".getBytes());
		os.write("<h1>的说法是就是</h1>".getBytes());
		os.write(str.getBytes("UTF-8"));
```
不一定对所有的浏览器都有效



### 采用字符流进行输出
```
String str = "中国";
//写入response采用utf-8码表
response.setCharacterEncoding("utf-8");
//控制浏览器采用utf-8进行显示
response.setHeader("content-type", "text/html;charset=utf-8");
PrintWriter pw = response.getWriter();
pw.write(str);
```

其中
```
response.setContentType("text/html;charset=utf-8");
等价于
//写入response采用utf-8码表
response.setCharacterEncoding("utf-8");
//控制浏览器采用utf-8进行显示
response.setHeader("content-type", "text/html;charset=utf-8");
```

### 文件下载
下载download目录下一个girl.jpg文件
```
package cn.cqut.response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class demo02 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = this.getServletContext().getRealPath("/download/girl.jpg");
//		System.out.println(path);
		String filename = path.substring(path.lastIndexOf("\\")+1);
//		System.out.println(path.lastIndexOf("\\")); 45
//		System.out.println(filename);
		

		response.setHeader("content-disposition", "attachment;filename=" + filename);

		InputStream is = null;
		OutputStream os = null;
		is = new FileInputStream(path);
		int len = 0;
		byte[] buffer = new byte[1024];
		os = response.getOutputStream();
		while ((len = is.read(buffer)) > 0) {
			os.write(buffer, 0, len);
		}
		is.close();
		os.close();
	}

}

```
其中的文件名若为中文，必须将文件名用URLEncoder转换
```
	String path = this.getServletContext().getRealPath("/download/美女.jpg");
	String filename = path.substring(path.lastIndexOf("\\")+1);
		//文件名为中文
	response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
```
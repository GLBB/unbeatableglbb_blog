---
title: response图片输出
date: 2018-01-03 19:17:32
tags:
---
```
package cn.cqut.response;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//输出随机图片
public class demo3 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 120;
	public static final int HEIGHT = 35;
	private static final int LINESNUMBER = 5;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		
		//设置背景颜色
		setBackgroudColor(graphics);
		
		//设置边框
		setBorder(graphics);
		
		//设置干扰线
		drawRomdomLine(graphics);
		
		//写随机字符，graphics2D有旋转方法
		drawRomdomLetter((Graphics2D) graphics);
		
		//把图形输出给浏览器,不缓存
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
		
	}
	
	private void setBackgroudColor(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	private void setBorder(Graphics graphics) {
		graphics.setColor(Color.BLUE);
		graphics.drawRect(1, 1, WIDTH-3, HEIGHT-3);
	}
	
	private void drawRomdomLine(Graphics graphics) {
		graphics.setColor(Color.GREEN);
		for(int i=0;i<LINESNUMBER;i++){
			int x1 = new Random().nextInt(WIDTH);
			int x2 = new Random().nextInt(WIDTH);
			
			int y1 = new Random().nextInt(HEIGHT);
			int y2 = new Random().nextInt(HEIGHT);
			
			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	private void drawRomdomLetter(Graphics2D graphics) {
	//常用汉语字符
		String base = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31";
		
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("宋体",Font.BOLD,20));
		
		int x = 5;
		
		for(int i=0;i<4;i++){
			int degree = new Random().nextInt()%30;
			double theta = degree*3.14/180;
			graphics.rotate(theta, x, 25);
			String ch = base.charAt(new Random().nextInt(base.length()))+"";
			graphics.drawString(ch, x, 25);
			graphics.rotate(-theta, x, 25);
			
			x+=30;
		}	
	}
}
```
附加注册页面
```
<!DOCTYPE html>
<html>
  <head>
    <title>index.html</title>
    <meta charset="utf-8" />
    
    <style type="text/css">
		#authenticat:HOVER {
			cursor: pointer;
		}		
	</style>
  </head>
  
  <body>
    <form>
    	用户名：<input type="text" name="name"><br/>
    	密码：<input type="password" name="passwd"><br/>
    	认证码：<input type="text" name="authentication"><br/>
    	<img src="/day06/servlet/demo3" onclick="this.src=this.src+'?'+(new Date().getMilliseconds())" id="authenticat"/>
    	<input type="submit" value="提交">
    </form>
  </body>
</html>

```
---
title: jsp运行原理
date: 2018-01-07 12:07:11
tags:
---
浏览器访问一个jsp页面时，服务器会将jsp页面翻译成servlet,
这个被服务器翻译出来的servlet页面可在tomcat目录下的work中
```
work\Catalina\localhost\day08\org\apache\jsp
```
其中被翻译的servlet定义如下
```
public final class _1_jsp extends org.apache.jasper.runtime.HttpJspBase
```
其中HttpJspBase是servlet的子类
其中源码定义如下
```
public abstract class HttpJspBase extends HttpServlet implements HttpJspPage
```

### jsp如何把页面内容打给浏览器的
在翻译后的servlet中的_jspService方法中可以看到
```
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <title>My JSP '1.jsp' starting page</title>\r\n");
      out.write("  <script>\"undefined\"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:\"60251\",secure:\"60257\"},c={nonSecure:\"http://\",secure:\"https://\"},r={nonSecure:\"127.0.0.1\",secure:\"gapdebug.local.genuitec.com\"},n=\"https:\"===window.location.protocol?\"secure\":\"nonSecure\";script=e.createElement(\"script\"),script.type=\"text/javascript\",script.async=!0,script.src=c[n]+r[n]+\":\"+t[n]+\"/codelive-assets/bundle.js\",e.getElementsByTagName(\"head\")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>\r\n");
      out.write("  \r\n");
      out.write("  <body data-genuitec-lp-enabled=\"false\" data-genuitec-file-id=\"wc2-0\" data-genuitec-path=\"/day08/WebRoot/1.jsp\">\r\n");
      out.write("    \t当前时间是：\r\n");
      out.write("    \t");

    		Date date = new Date();
    		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		out.print(sd.format(date));
    		
    	
      out.write("\r\n");
      out.write("  </body>\r\n");
      out.write("</html>\r\n");
```
与浏览器相关的全部通过Out这样的输出流打给浏览器，其中的Java代码全部照搬到翻译后的servlet中
其中jsp页面预先准备了多少对象
可以在jspService方法中看到
```
  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;
```
也就是提供的对象
```
PageContext pageContext;
HttpSession session = null;
ServletContext application;
ServletConfig config;
JspWriter out = null;
Object page = this;
JspWriter _jspx_out = null;
PageContext _jspx_page_context = null;
```

### jsp的最佳实践
把servlet作为WEB应用的控制器组件来使用，jsp作为数据的显示模板
servlet只负责响应请求产生数据，jsp负责显示数据
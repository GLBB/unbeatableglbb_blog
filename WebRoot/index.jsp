<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>glbbBlog</title>
    <meta charset="utf-8">
    <script type="text/javascript" src="js/marked.js"></script>
  	<script type="text/javascript" src="js/JSP_JS.js"></script>
    <link rel="stylesheet" href="css/JSPstyle.css"/>
    <link rel="stylesheet" href="css/article.css"/>
    <link rel="stylesheet" href="css/markedlight.css">
  </head>
  <body>
    <img id="head" src="img/head.jpg">
    <p id="name">GLBB</p>
    <p id="glbbsay">do better everyday</p>
    <div id="blog">
      <%
        File file = new File(application.getRealPath("md"));
        String[] strings = file.list();
      %>
      <script>
        var strs = new Array();
        <%for (int i=0;i<strings.length;i++){%>
          strs[<%=i%>] = "<%=strings[i]%>";
        <%}%>
        for(var i=0;i<strs.length;i++){
//            alert(strs[i]);
            strs[i] = strs[i].split(".")[0];
//            alert(strs[i]);
            document.write("<p class='title' onclick='getBlog(event)'>"+strs[i]+"</p>")
        }
      </script>
    </div>
  </body>
</html>

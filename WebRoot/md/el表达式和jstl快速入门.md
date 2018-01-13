---
title: el表达式和jstl快速入门
date: 2018-01-09 19:47:41
tags:
---
## EL表达式
el表达式用于获取数据，在jsp页面可使用${标识符}的形式，通知Jsp引擎调用pageContext.findAttribute()方法，以标示符为关键字从各个域对象中获取对象，如何域对象不存在标识符对应的对象，则返回空字符串""(注意，不是null)

获取data属性的值
```
 	<% 
 		String data = "aaaaaaaaa";
 		request.setAttribute("data", data);
 	%>
 	
 	${data}  <!-- pageContext.findAttribute()  page request session application -->
```

获取一个对象的属性
```
 	<% 
 		Person p = new Person();
 		p.setName("GLBB");
 		request.setAttribute("person", p);
 	%>
 	${person.name}
```

javabean中有对象，获取javabean对象中的值
```
 	<% 
 		Person p1 = new Person();
 		Address addr = new Address();
 		addr.setCity("上海");
 		
 		p1.setAddress(addr);
 		request.setAttribute("p1", p1);
 	%>
 	
 	${p1.address.city}
```

获取集合中的元素的值
```
 	<% 
 		List<Person> list = new ArrayList<>();
 		list.add(new Person("aaa"));
 		list.add(new Person("bbb"));
 		list.add(new Person("ccc"));
 		request.setAttribute("list", list);
 	%>
 	${list[1].name}
```
如果想要把list中所有元素迭代出来，需要配合jstl

获取map集合中的数据
```
 	<% 
 		Map<String,Person> map = new HashMap<>();
 		map.put("dd", new Person("dd"));
 		map.put("ee", new Person("ee"));
 		map.put("ff", new Person("ff"));
 		request.setAttribute("map", map);
 	%>
 	${map.ee.name}
```
但map存储Key为数字时
```
 	<% 
 		Map<String,Person> map = new HashMap<>();
 		map.put("dd", new Person("dd"));
 		map.put("ee", new Person("ee"));
 		map.put("ff", new Person("ff"));
 		map.put("11", new Person("11"));
 		request.setAttribute("map", map);
 	%>
 	${map['11'].name}
```
用el表达式在取数据时，通常用'.'点号，但点号取不出来时用[]

拿到当前web应用路径
```
${pageContext.request.contextPath}  <!-- /day09 -->
<a href="${pageContext.request.contextPath}/index.jsp">首页</a>
```

### jstl
jstl是sun公司开发的一套标签库，使用jstl可以在页面中实现一些简单的逻辑，从而替换页面中的脚本代码
在页面中使用jstl需要2个步骤
1. 导入jstl.jar和standerd.jar则两个jar文件
2. 在页面中使用&lt;@tablib url="" prifix=""&gt; 元素导入标签库
jstl标签库中常用标签：
&lt;c:foreach var="" items=""&gt;
&lt;c:if test=""&gt;

#### 使用jstl
添加一个指令
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```
遍历list中所有的元素
```
 	<c:forEach var="person" items="${list}">
 		${person.name}
 	</c:forEach>
```

遍历map中所有元素
```
 	 <% 
 		Map<String,Person> map = new HashMap<>();
 		map.put("dd", new Person("dd"));
 		map.put("ee", new Person("ee"));
 		map.put("ff", new Person("ff"));
 		map.put("11", new Person("11"));
 		request.setAttribute("map", map);
 	%>
 	<c:forEach var="entry" items="${map}">
 		${entry.key} : ${entry.value.name}
 	</c:forEach>
```
当${map}时，程序拿到map集合后，会自动调用entrySet方法，得到一个关于entry的集合set

用测试语句测试用户是否登陆
```
<c:if test="${user}==null"></c:if>
```
从四个域中获取user对象，并判断user是否为空
---
title: javabean
date: 2018-01-08 22:15:35
tags:
---
javabean是一个遵循特定写法的java类，通常具有如下特点
1. 这个java类必须具有一个无参构造函数
2. 属性必须私有化
3. 私有化的属性必须通过public类型的方法暴露给其他程序，并且方法和命名也必须遵守一定命名规范

javabean在javaEE开发中，用于封装数据，对于遵守以上写法的javabean组件，其他程序可以通过反射技术实例化javabean对象，并通过反射调用那些遵守命名的方法，从而获知javabean属性，进而调用属性保存数据

jsp提供了三个关于Javabean组件的动作元素，即Jsp标签
1. &lt;jsp:useBean&gt; 标签，用于在Jsp页面查找或实例化一个javaBean组件
2. &lt;jsp:setProperty&gt; 标签，用于在Jsp页面设置一个JavaBean组件的属性
3. &lt;jsp:getProperty&gt; 标签,用于在Jsp页面中获取一个javaBean组件的属性

### 例子
实例化一个javabean，并获取其属性
```
    <jsp:useBean id="person" class="cn.cqut.javabean.Person" scope="page"></jsp:useBean>
    
    <%=person.getName() %>
```
其中useBean标签可以嵌如标签体，标签体只会在创建javaBean后执行
例子
```
    <jsp:useBean id="person" class="cn.cqut.javabean.Person" scope="session">
    	bbb
    </jsp:useBean>
    
    <%=person.getName() %>
```


设置javabean的属性
```
    	<!-- 手工为bean属性赋值 -->
    <jsp:useBean id="person" class="cn.cqut.javabean.Person" />
    
    <jsp:setProperty name="person" property="name" value="GLBB"/>
    
    <%=person.getName() %>

    <!-- 用请求参数给bean赋值 -->
    <jsp:setProperty name="person" property="name" param="name"/>
    <%=person.getName() %>

    <jsp:setProperty name="person" property="age" param="age"/>
    <%=person.getAge() %>

    <!-- 用所有的请求参数为bean赋值 -->
    <jsp:setProperty property="*" name="person"/>
       <%=person.getName() %>
      <%=person.getAge() %>
```
其中age为int类型，传入的是一个String类型，发生了自动转化，其中转化支持八中基本类型转化，
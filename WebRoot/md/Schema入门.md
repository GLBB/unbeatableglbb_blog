---
title: Schema入门
date: 2017-12-29 14:29:57
tags:
---
### xsd文件
```
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
		   targetNamespace="http://glbb.com.cn"
		   xmlns="http://glbb.com.cn"
		   elementFormDefault="qualified">

<xs:element name="shiporder">
 <xs:complexType>
  <xs:sequence>
   <xs:element name="orderperson" type="xs:string"/>
   <xs:element name="shipto">
    <xs:complexType>
     <xs:sequence>
      <xs:element name="name" type="xs:string"/>
      <xs:element name="address" type="xs:string"/>
      <xs:element name="city" type="xs:string"/>
      <xs:element name="country" type="xs:string"/>
     </xs:sequence>
    </xs:complexType>
   </xs:element>
   <xs:element name="item" maxOccurs="unbounded">
    <xs:complexType>
     <xs:sequence>
      <xs:element name="title" type="xs:string"/>
      <xs:element name="note" type="xs:string" minOccurs="0"/>
      <xs:element name="quantity" type="xs:positiveInteger"/>
      <xs:element name="price" type="xs:decimal"/>
     </xs:sequence>
    </xs:complexType>
   </xs:element>
  </xs:sequence>
  <xs:attribute name="orderid" type="xs:string" use="required"/>
 </xs:complexType>
</xs:element>

</xs:schema>

```

### xml文件
```
<?xml version="1.0" encoding="UTF-8"?>

<shiporder 
xmlns="http://glbb.com.cn"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://glbb.com.cn shiporder.xsd"	
orderid="3">
	<orderperson>GLBB</orderperson>
	<shipto>
		<name>pencil</name>
		<address>ChongqingChangShou</address>
		<city>ChongQing</city>
		<country>Chin</country>
	</shipto>
	<item>
		<title>s</title>
		<quantity>20</quantity>
		<price>10</price>
	</item>
</shiporder>

```
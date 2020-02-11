<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String pizzaName = (String)request.getAttribute("pizzaName");
	String toppingNames = (String)request.getAttribute("toppingNames");
	String sideNames = (String)request.getAttribute("sideNames");
	int total = (int)request.getAttribute("total");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	span:nth-of-type(1){color: red;}
	span:nth-of-type(2){color: green;}
	span:nth-of-type(3){color: blue;}
	span:nth-of-type(4){text-decoration-line: underline;}
	span:nth-of-type(5){color: pink; font-size: 10em;}
</style>
</head>
<body>
	피자는<span><%=pizzaName %></span>토핑은<span><%=toppingNames %></span>사이드는<span><%=sideNames %></span>주문하셨습니다.
	
	<br><br>
	
	총합 <span><%=total %>원</span>
	
	<br><br>
	
	<span>즐겁게 드쇼</span>

</body>
</html>
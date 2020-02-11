<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>꼬리스컴바인 KKORISCOMBINE</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/index.css"/>
</head>
<body>
	<%@ include file="views/layout.jsp" %>
	
	<div class="container">
    	<section class="main">
      		<article class="quickMenu">
	         	<ul>
	            	<li><a href="#"><i class="fas fa-map-marker-alt"></i><span>검색</span></a></li>
	                <li><a href="#"><i class="far fa-check-circle"></i><span>예약</span></a></li>
	                <li><a href="#"><i class="far fa-comments"></i><span>커뮤니티</span></a></li>
	                <li><a href="#"><i class="far fa-calendar-alt"></i><span>일정</span></a></li>   
	         	</ul>
         	</article>
      	</section>
	</div>
</body>
</html>
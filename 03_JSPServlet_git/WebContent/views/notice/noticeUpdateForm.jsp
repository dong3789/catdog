<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.Date, notice.model.vo.Notice"%>
    
<%
	Notice n = ((Notice)request.getAttribute("notice"));
	request.setCharacterEncoding("UTF-8");
	String no = request.getParameter("no"); //쿼리스트링을 보고 가져온 것
	String title = request.getParameter("title");
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");
	String date = (String)request.getParameter("date");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.outer{
		width: 600px; height: 500px; background-color: rgba(255, 255, 255, 0.4); border: 5px solid white;
		margin-left: auto; margin-right: auto; margin-top: 50px;
	}
	.tableArea {width:450px; height:350px; margin-left:auto; margin-right:auto;}
	#updateNoBtn, #cancelBtn{background: #B2CCFF; color: white; border-radius: 15px; width: 80px; heigth: 25px; text-align: center; display: inline-block;}
	#updateNoBtn:hover, #cancelBtn:hover{cursor: pointer;}
	#cancelBtn{background: #D1B2FF;}
	content{width: 400px; height:300px;}
</style>
</head>
<body>
		
	<%@ include file="../common/01_menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항 수정</h2>
		<div class="tableArea">
			<form action="<%= request.getContextPath() %>/update.no">
				<table>
					<tr>
						<th>제목</th>
						
						<td colspan="3">
						<input type="hidden" name ="no" value="<%= no %>">
						<input type="text" name ="title" value="<%= title %>">
						</td>				
					</tr>
					<tr>
						<th>작성자</th>
						<td><%= writer %><input type="hidden" name="writer" value="<%= writer %>"></td>
						<th>작성일</th>
						<td><input type="date" name= "date" value="<%= date %>"></td>
					</tr>
					<tr>
						<th>내용</th>
						
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="content" cols="60" rows="15" style="resize:none;"><%= content%></textarea>
						</td>
					</tr>
				</table>
				
				<br>
				
				<div align="center">
					<button type="submit" id="updateNoBtn">저장</button>
					<div onclick="location.href='javascript:history.go(-1);'" id="cancelBtn">취소</div>
				</div>
			</form>	
		</div>
	</div>
	<script>
	
	</script>
</body>
</html>
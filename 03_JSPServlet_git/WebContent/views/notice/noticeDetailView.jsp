<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.Date, notice.model.vo.Notice" %>
<%
	Notice n = ((Notice)request.getAttribute("notice"));
	String no = request.getParameter("no"); //쿼리스트링을 보고 가져온 것
	String title = n.getnTitle();
	String writer = n.getnWriter();
	Date date = n.getnDate();
	String content = n.getnContent();	
	int count = n.getnCount();
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
	#updateNoBtn, #cancelBtn, #deleteNoBtn{background: #B2CCFF; color: white; border-radius: 15px; width: 80px; heigth: 25px; text-align: center; display: inline-block;}
	#updateNoBtn:hover, #cancelBtn:hover, #deleteNoBtn:hover{cursor: pointer;}
	#cancelBtn{background: #D1B2FF;}
	#deleteNoBtn{background: #D5D5D5;}
</style>
</head>
<body>
	<%@ include file="../common/01_menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항</h2>
		<div class="tableArea">
			<form action="views/notice/noticeUpdateForm.jsp" id="detailForm" name="detailForm">
				<table>
					<tr>
						<th>제목</th>
						<td colspan="3">
							<input type="hidden" name="no" value="<%= no %>">										
							<input type="hidden" size="50" name="title" value="<%= title %>"><%= title %>
						</td>				
					</tr>
					<tr>
						<th>작성자</th>
						<td><input type="hidden" name="writer" value="<%= writer %>"><%= writer %></td>
						<th>작성일</th>
						<td><input type="hidden" name="date" value="<%= date %>"><%= date %></td>
					</tr>
					<tr>
						<th>내용</th>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="hidden" cols="60" rows="15" style="resize:none;" readonly><%= content %></textarea>
						</td>
					</tr>
				</table>
			
				<br>
				
				<div align="center">
				<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
					<button type="submit" id="updateNoBtn">수정</button>
					<button type="button" onclick="deleteNo();" id="deleteNoBtn">삭제</button>
				<% } %>
					<div onclick="location.href='<%= request.getContextPath() %>/list.no'" id="cancelBtn">메뉴로</div>
				</div>
			</form>
			<script>
				function deleteNo(){
					location.href='<%= request.getContextPath() %>/delete.no?no=' + <%= no %>;
				}				
			</script>
		</div>
	</div>
	
</body>
</html>
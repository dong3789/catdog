<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, notice.model.vo.Notice"%>
    
<% 
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.outer{
		width: 800px; height: 500px; background: rgba(255, 255, 255, 0.4); border: 5px solid white;
		margin-left: auto; margin-right: auto; margin-top: 50px;
	}
	#listArea{text-align: center;}
	.tableArea{width:650px; height:350px; margin:auto;}
	th{border-bottom: 1px solid grey;}
	#writeNoBtn{background: #B2CCFF; color: white; border-radius: 10px; width: 80px; height: 25px;}
</style>
</head>
<body>
	<%@ include file="../common/01_menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">공지사항</h2>
		<div class="tableArea">
			<table id="listArea">
				<tr>
					<th>글번호</th>
					<th width="300px">글제목</th>
					<th width="100px">작성자</th>
					<th>조회수</th>
					<th width="100px">작성일</th>
				</tr>
				
				<% if(list.isEmpty()){ %> <!-- 스크립틀릿이 가져오는 list가 없으면 출력, 있으면 각 열을 출력. -->
				<tr>
					<td colspan="5">존재하는 공지사항이 없습니다.</td>
				</tr>
				<% } else {
						for(Notice n : list){ %> 
				<tr>
					<td><%= n.getnNo() %></td>
					<td><%= n.getnTitle() %></td>
					<td><%= n.getnWriter() %></td>
					<td><%= n.getnCount() %></td>
					<td><%= n.getnDate() %></td>
				</tr>
				<% 		}
				
					}%>
				
			</table>
		</div>
		
		<div class="searchArea" align="center">
		<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
			<button onclick ="location.href ='views/notice/noticeInsertForm.jsp'" id="writeNoBtn">글쓰기</button>
		<% }%>
		</div>
	</div>
	
	<script>
		$(function(){
			$('#listArea td').mouseenter(function(){
				$(this).parent().css({'background':'darkgray', 'cursor':'pointer'});
			}).mouseout(function(){
				$(this).parent().css("background","none");
			}).click(function(){
				var num = $(this).parent().children().eq(0).text(); //그 행의 첫번째 여기선 글번호를 의미.
				location.href = '<%= request.getContextPath() %>/detail.no?no=' + num;
				//클릭을 하면 글번호 주소로 이동한다. 
			});
			
		}); 
	</script>
</body>
</html>
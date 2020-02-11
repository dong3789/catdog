<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%@ page import ="java.util.ArrayList, board.model.vo.*"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	int listCount = pi.getListCount();
	int currentPage = pi.getCurrentPage();
	int maxPage = pi.getMaxPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	
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
	.tableArea{width:650px;	height:350px; margin-left:auto;	margin-right:auto;}
	th{border-bottom: 1px solid grey;}
	.pagingArea button{border-radius: 15px; background: #D5D5D5;}
	.searchArea{margin-right: 50px;}
	.searchArea button{background: #D1B2FF; border-radius: 5px; color: white; width: 80px; heigth: 25px; text-align: center;}
	button:hover{cursor: pointer;}
	#numBtn{background: #B2CCFF;}
	#choosen{background: #FFD8D8;}
	#listArea{margin: auto;}
</style>
</head>
<body>
	<%@ include file="../common/01_menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">게시판</h2>
		<div class="tableArea">
			<table id="listArea">
				<tr>
					<th width="100px">글번호</th>
					<th width="100px">카테고리</th>
					<th width="300px">글제목</th>
					<th width="100px">작성자</th>
					<th width="100px">조회수</th>
					<th width="150px">작성일</th>
				</tr>
				<% if(list.isEmpty()){%>
				
				<tr>
					<td colspan="6">조회된 리스트가 없습니다.</td>
				</tr>
				<% } else { 
						for(Board b : list) {
						
					
				
				%>
				<tr>
					<td><%= b.getbId() %><input type="hidden" value='<%= b.getbId() %>'> </td>
					<td><%= b.getCategory() %><input type="hidden" value='<%= b.getCategory() %>'></td>
					<td><%= b.getbTitle() %><input type="hidden" value='<%= b.getbTitle() %>'></td>
					<td><%= b.getbWriter() %><input type="hidden" value='<%= b.getbWriter() %>'></td>
					<td><%= b.getbCount() %><input type="hidden" value='<%= b.getbCount() %>'></td>
					<td><%= b.getModifyDate() %><input type="hidden" value='<%= b.getModifyDate() %>'></td>
				</tr>
				
				<% 		}
					
					}%>
			</table>
		</div>
		
		<!-- 페이징 처리  -->
		<div class='pagingArea' align='center'>
			<!-- list가 있을 때만 나타나는 영역이다.  -->
			<% if(!list.isEmpty()){ %>		
			<!-- 맨 처음으로 -->
			<button onclick ="location.href='<%=request.getContextPath() %>/list.bo?currentPage=1'">&lt;&lt;</button> 
			
			
			<!-- 이전 페이지로 -->
			<button onclick="location.href='<%=request.getContextPath() %>/list.bo?currentPage=<%= currentPage -1 %>'" id="beforeBtn"> &lt;</button>
			<script>
				if(<%= currentPage %> <= 1){
					var before = $('#beforeBtn');
					before.attr('disabled', 'true'); // 첫번째 페이지면 클릭이 안되게 한다. 
				}
			
			</script>
			
			<!-- 10개의 페이지 목록 -->
			<% for(int p = startPage; p <= endPage; p++){ %>
				<% if(p == currentPage) { %>
					<button id="choosen" disabled><%= p %></button>
				<% } else {%>
					<button id="numBtn" onclick="location.href='<%= request.getContextPath() %>/list.bo?currentPage=<%= p %>'"><%= p %></button>
				<% } %>
			<% } %>
			<!-- 다음 페이지 -->
			<button onclick="location.href='<%= request.getContextPath() %>/list.bo?currentPage=<%= currentPage +1 %>'" id="afterBtn">&gt;</button>
			<script>
				if(<%= currentPage %> >= <%= maxPage %>){
					var after = $("#afterBtn");
					after.attr('disabled', 'true');
				}
			</script>			
			
			<!-- 맨 끝으로 -->
			<button onclick="location.href='<%= request.getContextPath() %>/list.bo?currentPage=<%= maxPage %>'">&gt;&gt;</button>			
			
			
			
			
			
			<% } %>
			<div class='searchArea' align='right'>
				<% if(loginUser != null){ %>
				<button onclick = 'location.href="views/board/boardInsertForm.jsp"'>작성하기</button>
				<% } %>
			
			</div>
		</div>
		
	</div>
	
	<script>
		$(function(){
			$('#listArea td').mouseenter(function(){
				$(this).parent().css({'background':'darkgray', 'cursor':'pointer'});
			}).mouseout(function(){
				$(this).parent().css('background', 'none');
			}).click(function(){
				var bid = $(this).parent().children().children('input').val();
				
				//로그인 한 사람만 상세보기 이용할 수 있게하기
				<% if(loginUser != null){ %>
					location.href='<%= request.getContextPath() %>/detail.bo?bid=' + bid;
					
				<% } else {  %>
					alert('회원만 이용할 수 있는 서비스입니다.');
				
				<% }  %>
			});
		});
		
	</script>
	
	
</body>
</html>
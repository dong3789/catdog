<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.*, java.util.*"%>
    
<%
	Board b = (Board)request.getAttribute("board");
	ArrayList<Attachment> fileList = (ArrayList<Attachment>)request.getAttribute("fileList");
	Attachment titleImg = fileList.get(0);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer {
		width:1000px; height:735px; background: rgba(255, 255, 255, 0.4); border: 5px solid white;
		margin-left:auto; margin-right:auto; margin-top:50px;
	}
	.detail{text-align:center;}
	.detail th, .detail td{width: 1000px; padding: 10px; background: rgba(255, 255, 255, 0.4);}
	.detail th{background: white;}
	#titleImgArea{width:500px; height:300px; margin-left:auto; margin-right:auto;}
	#contentArea{height:30px;}
	.detailImgArea{width:250px; height:210px; margin-left:auto; margin-right:auto;}
	#titleImg{width:500px; height:300px;}
	.detailImg{width:250px; height:180px;}
	.downBtn{width: 80px; height: 25px; color: white; border-radius: 5px; background: #D1B2FF;}
	#thumbTable{margin: auto;}
   #updateBtn, #menuBtn, #deleteBtn{background: #B2CCFF; color: white; border-radius: 15px; width: 80px; heigth: 25px; text-align:center; display: inline-block;}
   #menuBtn{background: #D1B2FF;}
   #deleteBtn{background: #D5D5D5;}
   #updateBtn:hover, #menuBtn:hover, #deleteBtn:hover{cursor: pointer;}

</style>
</head>
<body>
	<%@ include file="../common/01_menubar.jsp" %>
	<div class="outer">
	<form action ="<%= request.getContextPath() %>/views/thumbnail/thumbnailUpdateForm.jsp">
			<table class="detail" id="thumbTable">
				<tr>
					<th width="50px">제목<input type="hidden" name ="bid" value="<%= request.getParameter("bid") %>"></th>
					<td colspan="5"><%= b.getbTitle() %></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><%= b.getbWriter() %></td>
					<th>조회수</th>
					<td><%= b.getbCount() %></td>
					<th>작성일</th>
					<td><%= b.getModifyDate() %></td>
				</tr>
				<tr>
					<th>대표<br>사진</th>
					<td colspan="4">
						<div id="titldImgArea" align="center">
							<a href="<%= request.getContextPath()%>/thumbnail_uploadFiles/<%=titleImg.getChangeName() %>" download="<%= titleImg.getChangeName() %>">
							<img id="titleImg" src="<%= request.getContextPath() %>/thumbnail_uploadFiles/<%= titleImg.getChangeName() %>">
							</a>	
						</div>
					</td>
					<td>
						<a href="<%= request.getContextPath()%>/thumbnail_uploadFiles/<%=titleImg.getChangeName() %>" download="<%= titleImg.getChangeName() %>">
							<button type="button" class= "downBtn">다운로드</button>
						</a>
					</td>
				</tr>
				<tr>
					<th>사진<br>메모</th>
					<td colspan="6">
						<p id="contentArea">
						<%
							String memo = b.getbContent();
						if(memo == null) { 	%>
							(메모 없음)
						<% 
							} else { %>
							<%= memo %>
						<%
							}
						%>
						</p>
					</td>
				</tr>
			</table>
			
			<table class="detail">
				<tr>
				<% for(int i = 1; i < fileList.size(); i++) { %>
					<td>
						<div class="DetailimageArea">
							<img id="detailImg" class="detailImg" src="<%= request.getContextPath() %>/thumbnail_uploadFiles/<%= fileList.get(i).getChangeName() %>">			
							<button onclick="location.href='<%= request.getContextPath() %>/download.th?fid=<%= fileList.get(i).getfId() %>'" class="downBtn" download="<%= fileList.get(i).getChangeName() %>">다운로드</button>
						</div>
						
						
						
				<% } %>
				</tr>
			</table>
			
			<br>
			
			<div align="center">
				<% if(loginUser != null && loginUser.getNickName().equals(b.getbWriter())) { %>
				<input type = "submit" id="updateBtn" value="수정">
				<input type = "button" onclick="deleteThumb();" id="deleteBtn" value="삭제">
				<% } %>
				<div onclick="location.href='<%= request.getContextPath() %>/list.th'" id="menuBtn">메뉴로</div>
			</div>
			
		</form>
	</div>
	
	<script>
		function deleteThumb() {
			var check = confirm("정말 삭제하시겠습니까?");
			console.log(check);
			if(check) {
				$('form').attr('action', '<%= request.getContextPath() %>/delete.th');
				$('form').submit();
			}
		}
	
	</script>
	
	
</body>
</html>
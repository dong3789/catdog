<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nick확인</title>

<script>
	function nickValue(){
		if("<%= request.getAttribute("nickCheck") %>" == "null"){
			document.getElementById("inputNickName").value = opener.document.joinForm.nickName.value;
			
		} else {
			document.getElementById("inputNickName").value = '<%= (String)request.getAttribute("nickCheck") %>';
			
		}
	} 
	
	function nickNamed(){
		opener.document.joinForm.nickName.value = document.getElementById("inputNickName").value;
		self.close();
	} 

</script>
</head>
<body onload="nickValue();">
	<b>닉네임 중복 체크</b>	
	<br>
	<form action="<%= request.getContextPath() %>/nickCheck.me" id="nickCheckForm" method="post">
		<input type="text" id="inputNickName" name = "inputNickName">
		<input type="submit" value="중복확인">
	</form>
	
<%	if(request.getAttribute("result") != null) {
	
	int result = (Integer)request.getAttribute("result"); //java.lang.NullPointerException 에러가 난다.
	// idCheckServlet을 쏘고 있다. getAttribute에는 시작하자마자 값이 없음. 중복확인을 눌러줬어야 idCheck.me를 실행하고, idCheckServlet이 
	//id를 받아오고, service ->DAO -> DB -> COUNT(*) -> result : 여기서 받은 result를 화면단에 보내줌. "중복확인을 눌렀을 때 이것들이 실행된다."
	if(result > 0) {

%>
이미 사용 중인 닉네임입니다.
<%
	} else {
%>	
사용 가능한 닉네임입니다.
<%
	}
	
}
%>
	<br>
	<br>
	<br>
	
	<input type="button" id="cancel" value="취소" onclick="window.close();">
	<input type="button" id="nickNamed" value= "확인" onclick="nickNamed();">
	
</body>
</html>
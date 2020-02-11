<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복체크</title>
<script>
	function idValue(){
		if("<%= request.getAttribute("checkedId") %>" == "null") { // 서블릿을 안갔으면 == 처음 창을 켰으면, 부모창에 있는걸 갖다 쓰겠다. 
			
		document.getElementById("inputId").value = opener.document.joinForm.joinUserId.value;// 여기에 값을 넣으면 id창이 떴을때 값이 들어간다. 		
		// 부모창에 있는 값이 있기 때문에 새창에서 아이디 중복체크를 한후에 새로운 아이디를 작성하고 중복확인을 누르면 기존 아이디로 다시 변하게 된다. 

		} else { // 서블릿을 한번이라도 갖다 왔으면, 갖다온 값을 가져오겠다는 의미. 
			document.getElementById('inputId').value= '<%= (String)request.getAttribute("checkedId")%>';
		
		}
			
		
		
	}
	
	
	function usedId(){
		opener.document.joinForm.joinUserId.value = document.getElementById('inputId').value;
		self.close();
		
	}
</script>

</head>


<body onload="idValue();">
	<b>아이디 중복 체크</b>
	<br>
	<form action="<%= request.getContextPath() %>/idCheck.me" id="idCheckForm" method="post">
	<!-- idCheck.me를 처리할 서블릿을 만들자  -->
		<input type="text" id="inputId" name="inputId">
		<input type="submit" value="중복확인">
	</form>
	
	<br>
	
	<%	if(request.getAttribute("result") != null) {// 이걸 써야 에러가 안난다. 
		
	
			int result = (Integer)request.getAttribute("result"); //java.lang.NullPointerException 에러가 난다.
			// idCheckServlet을 쏘고 있다. getAttribute에는 시작하자마자 값이 없음. 중복확인을 눌러줬어야 idCheck.me를 실행하고, idCheckServlet이 
			//id를 받아오고, service ->DAO -> DB -> COUNT(*) -> result : 여기서 받은 result를 화면단에 보내줌. "중복확인을 눌렀을 때 이것들이 실행된다."
			if(result > 0) {
		
	%>
		이미 사용 중인 아이디입니다.
	<%
			} else {
	%>	
		사용 가능한 아이디입니다.
	<%
			}
			
		}
	%>
	
	
	<br>
	<br>
	
	<input type="button" id="cancel" value="취소" onclick="window.close();">
	<input type="button" id="usedId" value="확인" onclick="usedId();">

</body>
</html>
package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout.me") 
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 영역에 로그인을 만들었다. 10분 유지. 유지되는 것을 없애면 된다. 
		HttpSession session = request.getSession();
		session.invalidate(); //끝. 세션 무효화
		
		/*
		 * 세션 무효화 시키기
		 * 1. HttpSession의 invalidate()
		 * 2. HttpSession의 setMaxInactiveInterval(0) 로그인 유지 시간을 0으로 만듦.
		 * 3. web.xml 배포서술자 = DD에서 수정
		 * 		<session-config>
		 * 			<session-timeout>0</session-timeout>
		 * 		</session-config> 
		 * */
		
		
		response.sendRedirect("index.jsp"); //로그아웃 후 메인으로 보냄.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

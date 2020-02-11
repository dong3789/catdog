package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.me") //여기있는 annotation부터 읽고 매칭시켜서 들어온다. 
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		
		String userPwd = request.getParameter("userPwd");
		
		Member member = new Member(userId, userPwd);
		
		Member loginUser = new MemberService().loginMember(member);
		
		response.setContentType("text/html; charset=UTF-8");  //한글도 들어갈 수 있으므로 사용함. 
		
		if(loginUser != null) { //로그인 성공 시
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(600); // 10분.(60*10) , 기본값 30분
			session.setAttribute("loginUser", loginUser); //멤버를 담아둠
			
			response.sendRedirect("index.jsp"); //정보를 갖고 올텐데 왜 sendRedirect를 쓰는가? sendRedirect은 단순페이지 이동. 새로운 request를 만든다. 하지만, 
			// 왜냐면 로그인 정보를 session에 담아뒀기 때문에. session은 request보다 큰 범위이기 때문에 sendRedirect의 영향을 받지 않는다. 
			// session은 모든 jsp 영역 안에서 사용 가능. 
			// application은 전체 브라우저에서 사용 가능. 
			 
		} else { //로그인 실패 시
			request.setAttribute("msg", "로그인 실패");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
			//실패하면 담아서 보내야 하므로 requestDispatcher에 담는다.
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

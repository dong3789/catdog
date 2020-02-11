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
 * Servlet implementation class UpdatePwdServlet
 */
@WebServlet("/updatePwd.me")
public class UpdatePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePwdServlet() {

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		HttpSession session = request.getSession();
		
		String loginUserId = ((Member)session.getAttribute("loginUser")).getUserId();
		String userPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
		String newPwd = request.getParameter("newPwd");
				
		int result = new MemberService().updatePwd(loginUserId, newPwd);
			
		String page = null;
		if(result > 0 ) {
			page="/myPage.me";
			request.setAttribute("msg", "비밀번호 수정에 성공하였습니다.");
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "비밀번호 수정에 실패하였습니다.");
		}
		
		request.setAttribute("userPwd", userPwd);

		RequestDispatcher view2 = request.getRequestDispatcher("views/member/pwdUpdateForm.jsp");
		view2.forward(request, response);
		
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

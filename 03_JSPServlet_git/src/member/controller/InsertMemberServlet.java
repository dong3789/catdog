package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class InsertMemberServlet
 */
@WebServlet("/insert.me")
public class InsertMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 가장 먼저 해야하는 것.
		// post방식에서 한글 넘어오면 깨질 수 있으므로 무조건 인코딩
		
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("joinUserId");
		String userPwd = request.getParameter("joinUserPwd");
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] intrr = request.getParameterValues("interest"); 
		//취미는 배열로 받기
	
		String interest = "";
		if(intrr != null) {
			for(int i = 0; i < intrr.length; i++) {
				if(i == intrr.length -1) {
					interest += intrr[i];
				} else {
					interest += intrr[i] + ",";
				}
			} 
		}
		//멤버 객체에 담아서 그 멤버객체를 보내본다. 
		Member member = new Member(userId, userPwd, userName, nickName, phone, email, address, interest);
		
		int result = new MemberService().insertMember(member);
		// 결과값은 int로 올 것이다. DAO에서 제대로 값 넘어온다면 1, 0 이 온다. 
		
		String page = "";
		if(result > 0) {
			page = "index.jsp";
			request.setAttribute("msg", "회원가입에 성공하였습니다.");
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
		} 
		
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

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
 * Servlet implementation class UpdateMemberServlet
 */
@WebServlet("/update.me")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("joinUserId");
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] intrr = request.getParameterValues("interest"); 
		//취미는 배열로 받기
	
		String interest = "";
		if(intrr != null) {
			interest = String.join(",", intrr);
		}
		
		Member member = new Member(userId, null, userName, nickName, phone, email, address, interest);	
		int result = new MemberService().updateMember(member);
		
		String page = null;
		if(result > 0) {
			page = "/myPage.me"; //seesion을 그대로 갖고 오게되면, 내가 기존 로그인 정보를 갖고 있는 상태인데, 회원정보 수정을 했을 땐 아직 session에 그 정보가 바뀌진 않은 상태.
			// myPage으로 넘어가서 한번 더 db를 다녀오도록 하는 방법으로 바꿈. 
			request.setAttribute("msg", "회원수정에 성공하였습니다.");
			
			
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "회원 수정에 실패하였습니다.");
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

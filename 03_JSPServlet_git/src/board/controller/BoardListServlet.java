package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/list.bo")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService service = new BoardService(); // 두 개의 서비스를 호출할 것이기 때문에 참조변수로 생성
		
		int listCount =  service.getListCount(); //게시판 리스트 개수
		
		int currentPage; //현재 페이지 표시
		int limit; 		// 한 페이지에 표시될 페이징 수
		int maxPage;	// 전체 페이지 중 가장 마지막 페이지
		int startPage;	// 페이징 된 페이지 중 시작 페이지
		int endPage;	// 페이징 된 페이지 중 마지막 페이지
		
		currentPage = 1;
		
		if(request.getParameter("currentPage") != null) { // view에서 요청이 들어왔다면
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			//페이지 전환 시 전달 받은 페이지로 currentPage 적용
			
		}
		
		limit = 10; 
		
		maxPage = (int)((double)listCount/limit + 0.9);
						//나눌 때 소숫점 위해 double 형변환
												//0.9를 더해서 페이징 수 추출
					//int로 소숫점 없애버리면 전체 페이징 수 구함
		startPage = ((int)((double)currentPage/limit + 0.9) - 1) * 10 + 1;
		endPage = startPage + limit - 1;
		
		if(maxPage < endPage) {
			endPage = maxPage; //maxPage가 endPage보다 작을 수 있다. 
		}
		
		//변수 다섯개를 움직이기엔 너무 많다. PageInfo클래스를 생성하자.
		
		PageInfo pi = new PageInfo(currentPage, listCount, limit, maxPage, startPage, endPage);
		
		ArrayList<Board> list = service.selectList(currentPage); //0개 또는 여러개
		//Board가 필요하다. 
		
		
		String page = null;
		if(list != null) {
			page = "views/board/boardList.View.jsp"; 
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "게시판 조회에 실패했습니다.");
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

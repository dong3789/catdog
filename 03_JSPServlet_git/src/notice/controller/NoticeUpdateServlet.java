package notice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateServlet
 */
@WebServlet("/update.no")
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getAttribute("UTF-8");
		int no = Integer.parseInt(request.getParameter("no"));
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		String date = (String)request.getParameter("date");
		
		
		Date sqlDate = null;
		
		if(date != "") {
			String dateArr[] = date.split("-");
			
	         int year = Integer.parseInt(dateArr[0]);
	         int month = Integer.parseInt(dateArr[1])-1; 
	         int day = Integer.parseInt(dateArr[2]);
	         
	         sqlDate = new Date(new GregorianCalendar(year, month, day).getTimeInMillis());
	      } else {
	         sqlDate = new Date(new GregorianCalendar().getTimeInMillis()); 
	      }
		
		Notice notice = new Notice(title, writer, content, sqlDate);
		
		int result = new NoticeService().updateNotice(no, notice);
		
		String page = null;
		if(result > 0) {
			page = "/detail.no?no=" + no;
		} else {
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "공지사항 수정에 실패했습니다.");
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

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

import member.model.vo.Member;
import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class InsertNoticeServlet
 */
@WebServlet("/insert.no")
public class InsertNoticeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      
      String title = request.getParameter("title");
      String userId = ((Member)request.getSession().getAttribute("loginUser")).getUserId();
      String date = request.getParameter("date"); // sql의 date형식으로 바꿔줘야함.
      String content = request.getParameter("content");
      
      Date sqlDate = null;
      
		/*
		 * System.out.println(title); System.out.println(userId); // admin
		 * System.out.println(date); System.out.println(content); // 나머지 3개는 아무것도 들어가있지
		 * 않다.
		 */      
      if(date != "") {
         String[] dateArr = date.split("-");
         
         int year = Integer.parseInt(dateArr[0]);
         int month = Integer.parseInt(dateArr[1])-1; // 그레고리안 캘린더에서 month는 zero베이스이므로 그레고리안이 제대로 인식하도록 -1 해줌
         int day = Integer.parseInt(dateArr[2]);
         
         sqlDate = new Date(new GregorianCalendar(year, month, day).getTimeInMillis());
      } else {
         sqlDate = new Date(new GregorianCalendar().getTimeInMillis()); // 기본적으로 오늘 날짜를 넣는다는 뜻.
      }
      
      Notice n = new Notice(title, content, userId, sqlDate);
      int result = new NoticeService().insertNotice(n);
      
      if(result > 0) {
    	  response.sendRedirect("list.no");
      } else {
    	  request.setAttribute("msg",  "공지사항 등록에 실패했습니다.");
    	  RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
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
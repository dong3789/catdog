package board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MyFileRenamePolicy;
import member.model.vo.Member;

/**
 * Servlet implementation class ThumbnailInsertServlet
 */
@WebServlet("/insert.th")
public class ThumbnailInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//post 방식 넘기면 한글 깨지므로 넣음.
		
		/*
		 * String title = request.getParameter("title");
		 */		
		// 자료 파일을 넣어줌. 
		// 폼 전송을 multipart/form-date 로 전송하는 경우 request.getParameter로 값을 받을 수 없음
		//폼이 multipart/form-data일 때 cos.jar가 파일도 받고 다른 값들도 받아주는 역할을 함
		// cos = com.orelilly.servlet의 약자
		
		if(ServletFileUpload.isMultipartContent(request)) { //enctype이 multi-part/form-data로 전송되었는지 확인
			int maxSize = 1024 * 1024 * 10; // 10Mbyte: 전송파일 용량 제한
			String root = request.getSession().getServletContext().getRealPath("/");  //realPath는 절대경로. 어디에 저장 할 것인지 넣음. 
			// 웹서버 container에 대한 경로를 추출. 
			
			String savePath = root + "thumbnail_uploadFiles/"; //위치 만듦.
			
			System.out.println(savePath);
			//C:\Users\YOON\Desktop\KH_WorkSpace\5_JSP_Servlet_workSpace\03_JSPServlet\WebContent\thumbnail_uploadFiles/
			//자신을 thumbnail_uploadFiles/ 넣기 위함.
			// Server modules without publishing에 체크하는 이유: 체크 안하면 정보가 메타데이터에 저장되기 때문에 접근하기가 어려워진다. 
			
			
			/*
			 * DefaultFileRenamePolicy는 cos.jar 안에 있는 클래스
			 * 같은 파일 명이 존재하는지 확인 후 존재한다면 파일 명 뒤에 숫자를 붙여 구분
			 *  ex. aaa.zip     aaa1.zip     aaa2.zip
			 * 
			 * 
			 * */
			
			// getParameter로 못가져옴. cos.jar를 통해 가져온다. 
			MultipartRequest multipartRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			//MultipartRequest multipartRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			// request객체 사용. 인코딩 타입이 다르면 못받으니 utf-8사용, 경로 지정, 용량 지정, 무슨 인코딩인지 , 내가 같은 이름이 들어갈 때 어떤 정책을 따를지.
			// 현재 이름 뒤에 숫자가 붙도록 함. 우리는 날짜와 시분초를 할 것이므로 new 자리에 새로운 정책을 넣으면 됨. 
			// common에 파일 생성
			
			
			// 사진 여러장 받을 수 있으므로 ArrayList사용. 
			ArrayList<String> saveFiles = new ArrayList<String>(); //바뀐 파일에 대한 이름 저장할 ArrayList
			ArrayList<String> originFiles = new ArrayList<String>(); // 원본 파일 이름을 저장할 ArrayList
			
			
			//반환값이 Enumeration임. 
			Enumeration<String> files = multipartRequest.getFileNames(); //파일이름 반환 메소드 
			// 폼에서 전송된 파일들의 이름 반환
		    // iterlator 할때 했음. 구버전임. Enumeration
			while(files.hasMoreElements()) {
				String name = files.nextElement();
				
				if(multipartRequest.getFilesystemName(name) != null) {
					// MyFileRenamePolicy는 rename메소드를 사용해서 rename된 파일명을 의미하는 것.
					// getFileSystemName(name) :  MyFileRenamePolicy의 rename메소드에서 작성한 대로 rename된 파일명
					// != null : null이 아니라면, rename 된것.
					saveFiles.add(multipartRequest.getFilesystemName(name)); // 프로젝트 저장될 때 바뀐이름이 필요함. 
					originFiles.add(multipartRequest.getOriginalFileName(name));  //origin은 원래 이름 그대로 들어감. 
					//원래 이름은 내가 다시 다운 받을 때, 쓰기 위함. 
					
 				}
			}
			
			String title = multipartRequest.getParameter("title");
			String content = multipartRequest.getParameter("content");
			String bWriter = ((Member)request.getSession().getAttribute("loginUser")).getUserId();
			
			/*
			 * System.out.println(title); System.out.println(content);
			 * System.out.println(bWriter); System.out.println(saveFiles);
			 * System.out.println(originFiles);
			 */
			
			Board b = new Board();
			b.setbTitle(title);
			b.setbContent(content);
			b.setbWriter(bWriter);
			
			ArrayList<Attachment> fileList = new ArrayList<Attachment>();
			for(int i = originFiles.size() -1; i >= 0; i--) {
				Attachment at = new Attachment();
						
						
				at.setFilePath(savePath);
				at.setOriginName(originFiles.get(i));
				at.setChangeName(saveFiles.get(i));
				
				if(i == originFiles.size() -1) { //대표이미지인지 내용 이미지인지 구분용
					at.setFileLevel(0);
				} else {
					at.setFileLevel(1);
				}
				
				fileList.add(at);
			}
			int result = new BoardService().insertThumbnamil(b, fileList);
			
			if(result > 0) {
				response.sendRedirect("list.th");
			} else {
				for(int i = 0; i < saveFiles.size(); i++) { //업로드 실패하면 파일 삭제하는 용도. 왜냐면, db가지 않아도 파일이 저장되기 때문에
					// 계속 파일 쌓이는 것을 방지하기 위함. 
					File failedFile = new File(savePath + saveFiles.get(i));
					failedFile.delete(); 
				}
				
				request.setAttribute("msg", "게시판 등록에 실패하였습니다.");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
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


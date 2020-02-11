package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDAO;
import board.model.vo.Attachment;
import board.model.vo.Board;

public class BoardService {

	public int getListCount() {
		Connection conn = getConnection();
		
		int result = new BoardDAO().getListCount(conn);
		close(conn);
		return result;
	}

	public ArrayList<Board> selectList(int currentPage) {
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDAO().selectList(conn, currentPage);
		close(conn);
		return list;
	}

	public Board selectBoard(int bId) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();
		
		int result = dao.updateCount(conn, bId);
		
		Board board = null;
		if(result > 0) {
			board = dao.selectBoard(conn, bId);
			if(board != null) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			rollback(conn);
		}
		
		return board;
	}

	public int insertBoard(Board board, int category) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();
		
		int result = dao.insertBoard(conn, board, category);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}

	public int updateBoard(Board board, int category) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();
		
		int result = dao.updateBoard(conn, board, category);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}

	public int deleteBoard(int bId) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();
		
		int result = dao.deleteBoard(conn, bId);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		System.out.println(result);
		return result;

	}

	public ArrayList selectTList(int i) {
		//board라고만 단정지으면 Attachment용으로 하나 더 만들어야하므로 제네릭을 지운다.
		
		Connection conn = getConnection();
		
		ArrayList list = null;
		
		BoardDAO dao = new BoardDAO();
		if(i == 1) { //BOARD갖고 오는 것
			list = dao.selectBList(conn);
			
		} else { // Attachment파일을 갖고 오는 용도
			list = dao.selectFList(conn);
		} 
				
		
		return list;
	}

	public int insertThumbnamil(Board b, ArrayList<Attachment> fileList) {
		Connection conn = getConnection();
		
		BoardDAO dao = new BoardDAO();
		
		int result1 = dao.insertThBoard(conn, b);
		int result2 = dao.insertAttachment(conn, fileList);
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result1;
	}

	public ArrayList<Attachment> selectThumbnail(int bId) {
		Connection conn  = getConnection();
		ArrayList<Attachment> list = new BoardDAO().selectThumbnail(conn, bId);
		
		return list;
	}

	public int deleteAttachment(int bId) {
		Connection conn = getConnection();
		BoardDAO dao = new BoardDAO();
		
		int result = dao.deleteAttachment(conn, bId);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		
		System.out.println(result);

		return result;
	}

}

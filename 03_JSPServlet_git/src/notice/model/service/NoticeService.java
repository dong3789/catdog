package notice.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import notice.model.dao.NoticeDAO;
import notice.model.vo.Notice;

public class NoticeService {
	public ArrayList<Notice> selectList() {
		Connection conn = getConnection();
		NoticeDAO nDAO = new NoticeDAO();
		ArrayList<Notice> list = nDAO.selectList(conn);
		
		close(conn);
		return list;
	}

	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = new NoticeDAO().insertNotice(conn, n);
		
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		} 
		
		close(conn);
		return result;
	}

	public Notice selectNotice(int nNo) {
		Connection conn = getConnection();
		NoticeDAO nDAO = new NoticeDAO();
		int result = nDAO.updateCount(conn, nNo);
		// datail 만들때 같이 카운트 되도록 만듦.
		
		Notice n = null;
		if(result > 0) {
			n = nDAO.selectNotice(conn, nNo);
			
			if(n != null) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			rollback(conn);
		} 
		close(conn);
		
		return n;
	}

	public int updateNotice(int no, Notice notice) {
		Connection conn = getConnection();
		NoticeDAO nDAO = new NoticeDAO();
		int result = nDAO.updateNotice(conn, no, notice);
		//if result
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int deleteNotice(int no) {
		Connection conn = getConnection();
		NoticeDAO nDAO = new NoticeDAO();
		int result = nDAO.deleteNotice(conn, no);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	
	
}

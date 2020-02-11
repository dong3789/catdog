package member.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;

import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberService {

		// 1. 로그인용 서비스
	public Member loginMember(Member member) {
		Connection conn = getConnection();
		
		MemberDAO mDAO = new MemberDAO();
		Member loginUser = mDAO.loginMember(member, conn);
		close(conn);
		
		return loginUser;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		int result = mDAO.insertMember(conn, member);
		// result값에 따라서 db에 commit 또는 rollback을 해야 한다.
		
		if(result>0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int idCheck(String userId) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		int result = mDAO.idCheck(conn, userId);
		close(conn); 
			
		return result;
	}

	public Member selectMember(String loginUserId) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		Member member = mDAO.selectMember(conn, loginUserId);
		
		close(conn);

		return member;
	}

	public int nickCheck(String nickName) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		int result = mDAO.nickCheck(conn, nickName);
				
		
		return result;
	}

	public int updateMember(Member member) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		int result = mDAO.updateMember(conn, member);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		int result = mDAO.deleteMember(conn, userId);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int updatePwd(String loginUserId, String newPwd) {
		Connection conn = getConnection();
		MemberDAO mDAO = new MemberDAO();
		int result = mDAO.updatePwd(conn, loginUserId, newPwd);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
}

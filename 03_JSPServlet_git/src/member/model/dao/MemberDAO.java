package member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import member.model.vo.Member;

public class MemberDAO {
	private Properties prop = new Properties();
	
	public MemberDAO() {
		String fileName  = MemberDAO.class.getResource("/sql/member/member-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public Member loginMember(Member member, Connection conn) {
		// pstmt는 위치홀더용
		PreparedStatement pstmt = null; 
		ResultSet rset = null;
		Member loginUser = null;
		
		String query = prop.getProperty("loginUser");
		//STATUS는 탈퇴한 회원인지 확인 유무
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			
			rset = pstmt.executeQuery();
			
			//로그인은 한명만 값을 가져오므로 if문 사용
			if(rset.next()) {
				loginUser = new Member(rset.getString("USER_ID"), rset.getString("USER_PWD"), 
									   rset.getString("USER_NAME"), rset.getString("NICKNAME"),
									   rset.getString("PHONE"),rset.getString("EMAIL"), 
									   rset.getString("ADDRESS"), rset.getString("INTEREST"), 
									   rset.getDate("ENROLL_DATE"), rset.getDate("MODIFY_DATE"), 
									   rset.getString("STATUS"));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return loginUser;
	}

	public int insertMember(Connection conn, Member member) {
		// query에 값을 넣어야하니깐, PrepareStatement를 세팅해야 한다.
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertMember");
		// 키 값이랑 매칭되있는 값을 갖고 온다. 
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getNickName());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getInterest());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int idCheck(Connection conn, String userId) {
		PreparedStatement pstmt = null; 
		ResultSet rs = null; //쿼리문에서 SELECT은 무조건 ResultSet을 사용한다.
		int result = 0;
		String query = prop.getProperty("idCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1); //있으면 첫번째 값을 갖고 와라. 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public Member selectMember(Connection conn, String loginUserId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		String query = prop.getProperty("selectMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginUserId);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				member = new Member(rs.getString("USER_ID"), rs.getString("USER_PWD"), rs.getString("USER_NAME"), rs.getString("NICKNAME"),
						   rs.getString("PHONE"),rs.getString("EMAIL"), 
						   rs.getString("ADDRESS"), rs.getString("INTEREST"));
				// 컬럼명 대신 컬럼 번호를 사용할 수도 있다. 그러나 번호는 순서가 바뀔 수도 있다. 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			
		}
		return member;
	}

	public int nickCheck(Connection conn, String nickName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String query = prop.getProperty("nickCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nickName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
		
	}

	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  member.getUserName());
			pstmt.setString(2,  member.getNickName());
			pstmt.setString(3,  member.getPhone());
			pstmt.setString(4,  member.getEmail());
			pstmt.setString(5,  member.getAddress());
			pstmt.setString(6,  member.getInterest());
			pstmt.setString(7,  member.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  userId);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updatePwd(Connection conn, String loginUserId, String newPwd) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updatePwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  newPwd);
			pstmt.setString(2, loginUserId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	} 

}

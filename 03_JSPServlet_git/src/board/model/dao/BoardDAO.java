package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import board.model.vo.Attachment;
import board.model.vo.Board;

public class BoardDAO {

	private Properties prop = new Properties();
	
	public BoardDAO() {
		String fileName = BoardDAO.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	public int getListCount(Connection conn) {
		// 어떤 객체를 사용할 것인가
		Statement stmt = null;;
		// 왜냐면 위치홀더가 필요없으므로
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			//count는 행하나만 나오니
			if(rset.next()) {
				result = rset.getInt(1); // 첫 번째 값을 갖고 와라.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		
		
		return result;
	}



	public ArrayList<Board> selectList(Connection conn, int currentPage) {
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		ArrayList<Board> list = null;
		
		int posts =  10; // 한 페이지에 보여질 게시글 개수
		
		int startRow = (currentPage -1) * posts + 1; //ex) 41
		int endRow = startRow + posts -1 ; //ex) 49
		
		String query = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			list = new ArrayList<Board>();
			while(rset.next()) {
				Board b = new Board(rset.getInt("bid"),
									rset.getInt("btype"),
									rset.getString("cname"),
									rset.getString("btitle"),
									rset.getString("bcontent"),
									rset.getString("nickName"),
									rset.getInt("bcount"),
									rset.getDate("create_date"),
									rset.getDate("modify_date"),
									rset.getString("status"));
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}



	public int updateCount(Connection conn, int bId) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  bId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}



	public Board selectBoard(Connection conn, int bId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;
		
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new Board(rs.getInt("bid"), 
								  rs.getInt("bType"), 
								  rs.getString("cname"), 
								  rs.getString("bTitle"), 
								  rs.getString("bContent"), 
								  rs.getString("nickname"), 
								  rs.getInt("bCount"), 
								  rs.getDate("create_Date"), 
								  rs.getDate("modify_Date"), 
								  rs.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return board;
	}

	public int insertBoard(Connection conn, Board board, int category) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, category);
			pstmt.setString(2, board.getbTitle());
			pstmt.setString(3, board.getbContent());
			pstmt.setString(4, board.getbWriter());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
				
		return result;
	}

	public int updateBoard(Connection conn, Board board, int category) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getbTitle());
			pstmt.setString(2, board.getbContent());
			pstmt.setInt(3, category);
			pstmt.setInt(4, board.getbId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
				
		return result;
	}

	public int deleteBoard(Connection conn, int bId) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
				
		return result;
	}



	   public ArrayList selectBList(Connection conn) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<Board> list = null;
		      
		      String query = prop.getProperty("selectBList");
		      try {
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(query);
		         
		         list = new ArrayList<Board>();
		         
		         while(rs.next()) {
		            list.add(new Board(rs.getInt("bid"),
		                           rs.getInt("btype"),
		                           rs.getString("cname"),
		                           rs.getString("btitle"),
		                           rs.getString("bcontent"),
		                           rs.getString("nickName"),
		                           rs.getInt("bcount"),
		                           rs.getDate("create_date"),
		                           rs.getDate("modify_date"),
		                           rs.getString("status")));
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      
		      return list;
		   }

		   public ArrayList selectFList(Connection conn) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      
		      ArrayList<Attachment>list = null;
		      
		      String query = prop.getProperty("selectFList");
		      
		      try {
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(query);
		         
		         list = new ArrayList<Attachment>();
		         
		         while(rs.next()) {
		            list.add(new Attachment(rs.getInt("bid"),
		                              rs.getString("change_name")));
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      return list;
		   }

		   
		public int insertThBoard(Connection conn, Board b) {
			PreparedStatement pstmt = null;
			int result = 0;
			
			String query = prop.getProperty("insertThBoard");
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,  b.getbTitle());
				pstmt.setString(2,  b.getbContent());
				pstmt.setString(3,  b.getbWriter());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
			
			return result;
		}



		public int insertAttachment(Connection conn, ArrayList<Attachment> fileList) {
			PreparedStatement pstmt = null;
			int result = 0;
			
			String query = prop.getProperty("insertAttachment");
			
			try {
				for(int i = 0; i < fileList.size(); i++) {
					Attachment a = fileList.get(i);
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1,  a.getOriginName());
					pstmt.setString(2,  a.getChangeName());
					pstmt.setString(3,  a.getFilePath());
					pstmt.setInt(4, a.getFileLevel());
					
					result += pstmt.executeUpdate();
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
			return result;
		}



		public ArrayList<Attachment> selectThumbnail(Connection conn, int bId) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Attachment> list = null;
			System.out.println("bId : " + bId);
			String query = prop.getProperty("selectThumbnail");
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bId);
				rs = pstmt.executeQuery();
				list = new ArrayList<Attachment>();
				
				while(rs.next()) {
					Attachment at = new Attachment();
					at.setfId(rs.getInt("fid"));;
					at.setOriginName(rs.getString("origin_name"));
					at.setChangeName(rs.getString("change_name"));
					at.setFilePath(rs.getString("file_path"));
					at.setUploadDate(rs.getDate("upload_date"));
					
					list.add(at);
				
				
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			
			return list;
		}



		public int deleteAttachment(Connection conn, int bId) {
			PreparedStatement pstmt = null;
			int result = 0;
			
			String query = prop.getProperty("deleteAttachment");
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bId);
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			}
			
			
			return result;
		}
}

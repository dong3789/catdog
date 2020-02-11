package common;

import static common.JDBCTemplate.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	private JDBCTemplate() {} //싱글톤패턴이므로 private
	
	public static Connection getConnection() {
		Connection conn = null; //sql 
		Properties prop = null; //utill 갖고 옴.
		
		String fileName = JDBCTemplate.class.getResource("/sql/driver.properties").getPath(); // 예전엔 이런 작업을 하지 않음. view단과 자바파일을 나누지 않았음.(콘솔에 출력하므로) 이제 구분함. 지금 여기에 경로에서 갖고 오겠다. 라는 path사용.
		
		try {
			prop = new Properties();
			prop.load(new FileReader(fileName));
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
			
			
			conn.setAutoCommit(false); //트랜젝션이 자동으로 commit 방지.
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
		public static void close(Connection conn) {
			try {
				if(conn!= null && !conn.isClosed()) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void commit(Connection conn) {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.commit();
				} 
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void rollback(Connection conn) {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.rollback();
				} 
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(ResultSet rset) {
			try {
				if(rset != null && !rset.isClosed()) {
					rset.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(Statement stmt) {
			try {
				if(stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
}

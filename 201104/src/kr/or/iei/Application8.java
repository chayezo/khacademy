package kr.or.iei;	//transaction

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application8 {
	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String DB_USER = "system";
	private static final String DB_PASS = "12345";

	public static void main(String[] args) throws ClassNotFoundException {
		// 1. 클래스 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// 2. 데이터베이스에 접속
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			conn.setAutoCommit(false); 
			//true라고 설정하면 물리적 저장소에 바로바로 반영되어서 오류 발생. 
			//-->이렇게 설정하면 둘 다 0원 되버림.
			//autocommit은 꺼두자.

			// insert delete update
			String sql1 = "UPDATE bank_account SET balance = 0 WHERE username = 'A'";
			String sql2 = "UPDATE bank_account SET balance = 1000 WHERE username = 'B'";

			//A의 계좌를 0으로
			final PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			final int affectedRows1 = pstmt1.executeUpdate();
			System.out.println(affectedRows1);
			
			/*if (System.currentTimeMillis() > 0) {
				throw new SQLException("롤백 테스트");
			}	//오류가 발생하는 코드. 그냥 에러 발생하는 코드. 이해하려고 하지마~~~~
			 */
			
			
			//B의 계좌를 1000으로
			final PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			final int affectedRows2 = pstmt2.executeUpdate();
			System.out.println(affectedRows2);

			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException throwables) {
			if (conn != null) {
				try {
					System.out.println("제발 롤백");
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			throwables.printStackTrace();
		} finally {
			close(conn);
		}
	}

	private static void close(AutoCloseable closeable) {
		try {
			closeable.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

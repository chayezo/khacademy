package kr.or.iei;	//INSERT

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application3 {

	public static void main(String[] args) throws ClassNotFoundException {
		// 1. jvm에 클래스 로드 (Oracle JDBC Driver)
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// 2. 드라이버 매니저로부터 커넥션 얻어옴
		try (final Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE"
				,"system", "12345"
				);

		) {
			final String name = "abc";
			final String email = "a@b.com";
			final String sql = "INSERT INTO jdbc_example VALUES(seq_jdbc_example_pk.nextval, ? , DEFAULT, ?)";
			final PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);	//괄호 안 1번 --> parameterIndex
			pstmt.setString(2, email);
			final int affectedRows = pstmt.executeUpdate();
			System.out.println(affectedRows);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package net.codejava.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
	public static Connection getConnection() {
		Connection c = null;
		try {
			// Đăng ký MySQL Driver với DriverManager
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String url = "jdbc:mysql://localhost:3306/test";
			String userName = "root";
			String passWord = "123456789";
			// Connection để thiết lập kết nối tới cơ sở dữ liệu thông qua một URL, tên người dùng và mật khẩu.
			// Tạo kết nối
			c = DriverManager.getConnection(url, userName, passWord);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return c;
	}

	public static void closeConnection(Connection c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
package net.codejava.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserInterface<User> {
	public static UserDAO getInstance() {
		return new UserDAO();
	}
	@Override
	public List<User> getAllUser() { // dùng phương thức getAll để trả về list danh sách user
		Connection conn = DBConnectionUtil.getConnection(); // để kết nối cơ sở sử liệu thông qua lớp dbconectuon
		List<User> userList = new ArrayList<>(); // tạo mới list user
		try {
			Statement stmt = conn.createStatement(); // sử dụng đối tượng để tạo đối tượng kết nối statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM db_user");
			//sử dụng statement để thực thi truy vấn sql lấy ra tất cả bản ghi từ user
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"),
						rs.getString("address"));
				userList.add(user);
			}
			//sử dụng vòng lặp while để lấy từng bản ghi trong result và tạo user mới cho bản ghi 
			//đó và thêm đối tượng user vào list
		} catch (SQLException e) {
			throw new RuntimeException("uncaught", e);
		}
		return userList;
	}

	@Override
	public User getUserById(int id) { // dùng phương thức getUser.. truy vấn thông tin cửa user dựa vào id truyền vào
		Connection conn = DBConnectionUtil.getConnection();
		User user = null; // khởi tạo user = null nếu không có bản ghi phù hợp nào tìm trong csdl
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM db_user WHERE id = ?");
			//để thực hiện truy vấn với sql
			ps.setInt(1, id); // thêm giá trị cửa biến id vàp câu lệnh truy vấn ?
			ResultSet rs = ps.executeQuery();
			// "ps" và lưu kết quả trả về vào đối tượng ResultSet "rs" chứa các bản ghi kết quả.
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("address"));
			}
			//kết quả truy vấn tồn tại lấy ra các giá trị trong bảng
		} catch (SQLException e) {
			throw new RuntimeException("uncaught", e);
		}
		return user;
	}

	@Override
	public void addUser(User user) {
		Connection conn = DBConnectionUtil.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO db_user (name, age ,address) VALUES (?,?,?)");
			ps.setString(1, user.getName());
			ps.setInt(2, user.getAge());
			ps.setString(3, user.getAddress());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("uncaught", e);
		}finally {
            DBConnectionUtil.closeConnection(conn);
        }

	}

	@Override
	public void updateUser(User user) {
		Connection conn = DBConnectionUtil.getConnection();
		try {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE db_user SET name = ?, age = ? , address = ? WHERE id = ?");
			ps.setString(1, user.getName());
			ps.setInt(2, user.getAge());
			ps.setString(3, user.getAddress());
			ps.setInt(4, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("uncaught", e);
		}finally {
            DBConnectionUtil.closeConnection(conn);
        }

	}

	@Override
	public void deleteUser(int id) {
		Connection conn = DBConnectionUtil.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM db_user WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("uncaught", e);
		} finally {
            DBConnectionUtil.closeConnection(conn);
        }

	}

}
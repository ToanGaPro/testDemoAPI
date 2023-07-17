package net.codejava.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;   


@Path("/user")
public class UserRersource {
	static Logger log = Logger.getLogger(UserRersource.class.getName());  
	// biến log trong logger trong lớp userresouce để ghi lại log trong nhật ký
	//getLogger tạo đối tượng logger có tên là UserRersource.class.getName()
	// log.infor để ghi mức độ nhật ký thông tin

	@GET
	@Produces(MediaType.APPLICATION_JSON)// định dạng dữ liệu
	//khi client gửi một yêu cầu GET đến API RESTful, phương thức này sẽ được gọi
	//để xử lý yêu cầu và trả về dữ liệu được định dạng JSON.
	public List<User> findAll() {
		try {
			List<User> user =  UserDAO.getInstance().getAllUser();
			log.info("Lấy ra tất cả danh sách user thành công!");
			return user;
		} catch (Exception e) {
			log.error("Lỗi khi lấy ra tất cả danh sách", e);
            throw new WebApplicationException("Unable to retrieve user", Response.Status.NOT_FOUND);
		}
    }

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findById(@PathParam("id") Integer id) {
		User user = UserDAO.getInstance().getUserById(id);
		if (user == null) {
			log.warn("User với id là: " + id + " Không tồn tại");
		} else {
			log.info("Tìm user thành công với id là: " + id);
		}
			return user;
		}
	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
	//được sử dụng để chỉ định loại biểu diễn phương tiện MediaType.APPLICATION_JSON,
	//cho biết rằng điểm cuối này chỉ chấp nhận yêu cầu được định dạng dưới dạng JSON.
	public Response create(User user) {
		try {
			UserDAO.getInstance().addUser(user);
			log.info("Thêm mới user thành công!");
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			log.error("Lỗi khi lấy ra tất cả danh sách",e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Integer id, User user) {
		User existingUser = UserDAO.getInstance().getUserById(id);
        if (existingUser != null) {
            user.setId(id);
            UserDAO.getInstance().updateUser(user);
    		log.info("Cập nhập user thành công!");
            return Response.status(Response.Status.OK).build();
        } else {
        	log.error("Cập nhập user thất bại");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id) {
		User existingUser = UserDAO.getInstance().getUserById(id);
        if (existingUser != null) {
        	UserDAO.getInstance().deleteUser(id);
    		log.info("Xóa user thành công!");
            return Response.status(Response.Status.OK).build();
        } else {
        	log.error("Xóa user thất bại");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
	}
}
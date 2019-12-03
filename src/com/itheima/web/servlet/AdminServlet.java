package com.itheima.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.alibaba.fastjson.JSON;
import com.itheima.domain.CommentList;
import com.itheima.domain.Lgcount;
import com.itheima.domain.OrderList;
import com.itheima.domain.User;
import com.itheima.service.AdminService;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.baseServlet.BaseServlet;
 

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private AdminService service = (AdminService) BeanFactory.getBean("AdminService");

	/**
	 * 获取用户的信息列表
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getAllUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<User> list = service.getAllUser();
			int ammount = service.getCount();
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 添加用户信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> map1 = new HashMap<String, String>();
		try {
			Map<String, String[]> parameterMap = request.getParameterMap();
			User user = new User();
			Map<String, String> map = new HashMap<String, String>();
			// 创建一个磁盘文件的工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 创建一个文件上传的核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 判断表单是不是一个文件类型的表单
			boolean ismultip = upload.isMultipartContent(request);
			if (ismultip) {
				// 接收文件上传的表单集合
				List<FileItem> listFile = upload.parseRequest(request);
				// 判断是否有文件
				if (listFile != null) {
					for (FileItem fileitem : listFile) {
						// 判断是否为普通的表单的上传项
						boolean formField = fileitem.isFormField();
						// 封装普通的文本项
						if (formField) {
							String fieldName = fileitem.getFieldName();
							String filevalue = fileitem.getString("UTF-8");
							map.put(fieldName, filevalue);
						}
						// 封装文件上传项
						else {
							// 获取文件的上传名字
							String filename = fileitem.getName();
							long l = System.currentTimeMillis();
							filename = l + filename;
							// 获取上传流
							InputStream inputStream = fileitem.getInputStream();
							// 获取文件上传的绝对的路径
							String realpath = this.getServletContext().getRealPath("upload");
							OutputStream outputStream = new FileOutputStream(realpath + "/" + filename);
							IOUtils.copy(inputStream, outputStream);
							outputStream.close();
							inputStream.close();
							fileitem.delete();
							map.put("image", "upload/" + filename);
						}
					}
				}
			}
			BeanUtils.populate(user, map);
			UserService service = (UserService) BeanFactory.getBean("UserService");
			service.register(user);
			map1.put("msg", "用户信息添加成功");
		} catch (Exception e) {
			map1.put("msg", "用户信息添加失败！");
		}
		response.getWriter().print(JSON.toJSONString(map1));
	}

	/**
	 * 根据id查找用户数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getUserById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("uid");
		try {
			User user = service.getUserByuid(uid);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/admin_update_user.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新用户的信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, String[]> parameterMap = request.getParameterMap();
			User user = new User();
			Map<String, String> map = new HashMap<String, String>();
			// 创建一个磁盘文件的工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 创建一个文件上传的核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 判断表单是不是一个文件类型的表单
			boolean ismultip = upload.isMultipartContent(request);
			if (ismultip) {
				// 接收文件上传的表单集合
				List<FileItem> listFile = upload.parseRequest(request);
				// 判断是否有文件
				if (listFile != null) {
					for (FileItem fileitem : listFile) {
						// 判断是否为普通的表单的上传项
						boolean formField = fileitem.isFormField();
						// 封装普通的文本项
						if (formField) {
							String fieldName = fileitem.getFieldName();
							String filevalue = fileitem.getString("UTF-8");
							map.put(fieldName, filevalue);
						}
						// 封装文件上传项
						else {
							// 获取文件的上传名字
							String filename = fileitem.getName();
							long l = System.currentTimeMillis();
							filename = l + filename;
							// 获取上传流
							InputStream inputStream = fileitem.getInputStream();
							// 获取文件上传的绝对的路径
							String realpath = this.getServletContext().getRealPath("upload");
							OutputStream outputStream = new FileOutputStream(realpath + "/" + filename);
							IOUtils.copy(inputStream, outputStream);
							outputStream.close();
							inputStream.close();
							fileitem.delete();
							map.put("image", "upload/" + filename);
						}
					}
				}
			}
			BeanUtils.populate(user, map);
			UserService service1 = (UserService) BeanFactory.getBean("UserService");
			System.out.println(user.getUid());
			service.delete(user.getUid() + "");
			service1.register(user);
			request.setAttribute("msg", "该用户信息更新成功!");
			List<User> list = service.getAllUser();
			int ammount = service.getCount();
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "该用户信息更新失败!");
		}
		request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		try {
			service.delete(uid);
			List<User> list = service.getAllUser();
			int ammount = service.getCount();
			request.setAttribute("msg", "该用户已经删除!");
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户激活
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService service1 = (UserService) BeanFactory.getBean("UserService");
		String code = request.getParameter("code");
		try {
			service1.active(code);
			int ammount = service.getCount();
			List<User> list = service.getAllUser();
			request.setAttribute("msg", "该用户已激活!");
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 用户qq激活
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService service1 = (UserService) BeanFactory.getBean("UserService");
		String code = request.getParameter("code");
		try {
			service1.active(code);
			response.getWriter().print("<script>alert('激活成功')</script>");
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 批量删除用户的数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteUserS(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] arrays = request.getParameter("array").split(",");
		try {
			for (String string : arrays) {
				service.delete(string);
			}
			int ammount = service.getCount();
			request.setAttribute("ammount", ammount);
			request.setAttribute("msg", "用户批量成功!");
			List<User> list = service.getAllUser();
			request.setAttribute("list", list);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查看用户的所有的订单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getAllOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<OrderList> list = service.getAllOrder();
			int ammount = service.getOrderCount();
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-order-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户的所有的评论
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getAllComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<CommentList> commentList = service.getAllComment();

			request.setAttribute("commentList", commentList);
			int ammount = service.getCommentCount();
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-comment-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除评论
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commid = request.getParameter("commid");
		try {
			service.deleteComment(commid);
			List<CommentList> commentList = service.getAllComment();
			int ammount = service.getCommentCount();
			request.setAttribute("ammount", ammount);
			request.setAttribute("msg", "评论已删除!");
			request.setAttribute("commentList", commentList);
			request.getRequestDispatcher("/admin-comment-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteCommentS(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] arrays = request.getParameter("array").split(",");
		try {
			for (String string : arrays) {
				service.deleteComment(string);
			}
			int ammount = service.getCommentCount();
			request.setAttribute("ammount", ammount);
			request.setAttribute("msg", "批量评论已删除!");
			List<CommentList> commentList = service.getAllComment();
			request.setAttribute("commentList", commentList);
			request.getRequestDispatcher("/admin-comment-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取图表的数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getdLgCount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int number = 12;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		try {
			number = Integer.parseInt(request.getParameter("number"));
		} catch (Exception e) {
			number = 12;
		}
		List<String> listDate =  new ArrayList<String>();
		List<String> listCount =  new ArrayList<String>();
		try {
			List<Lgcount> list = service.getdLgCount(number);
			for (Lgcount lgcount : list) {
				listDate.add(lgcount.getLoginDate());
				listCount.add(lgcount.getCount());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("time", listDate);
		map.put("count", listCount);
		response.getWriter().println(JSON.toJSONString(map));
	}
	/**
	 * 后端的用户数据的搜索功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void likesearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		String message = request.getParameter("message");
		try {
			List<User> list =service.likesearch(time1,time2,message);
			int ammount = service.getCount();
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

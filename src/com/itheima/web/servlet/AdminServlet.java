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
	 * ��ȡ�û�����Ϣ�б�
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
	 * ����û���Ϣ
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
			// ����һ�������ļ��Ĺ���
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ����һ���ļ��ϴ��ĺ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			// �жϱ��ǲ���һ���ļ����͵ı�
			boolean ismultip = upload.isMultipartContent(request);
			if (ismultip) {
				// �����ļ��ϴ��ı�����
				List<FileItem> listFile = upload.parseRequest(request);
				// �ж��Ƿ����ļ�
				if (listFile != null) {
					for (FileItem fileitem : listFile) {
						// �ж��Ƿ�Ϊ��ͨ�ı����ϴ���
						boolean formField = fileitem.isFormField();
						// ��װ��ͨ���ı���
						if (formField) {
							String fieldName = fileitem.getFieldName();
							String filevalue = fileitem.getString("UTF-8");
							map.put(fieldName, filevalue);
						}
						// ��װ�ļ��ϴ���
						else {
							// ��ȡ�ļ����ϴ�����
							String filename = fileitem.getName();
							long l = System.currentTimeMillis();
							filename = l + filename;
							// ��ȡ�ϴ���
							InputStream inputStream = fileitem.getInputStream();
							// ��ȡ�ļ��ϴ��ľ��Ե�·��
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
			map1.put("msg", "�û���Ϣ��ӳɹ�");
		} catch (Exception e) {
			map1.put("msg", "�û���Ϣ���ʧ�ܣ�");
		}
		response.getWriter().print(JSON.toJSONString(map1));
	}

	/**
	 * ����id�����û�����
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
	 * �����û�����Ϣ
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
			// ����һ�������ļ��Ĺ���
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ����һ���ļ��ϴ��ĺ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			// �жϱ��ǲ���һ���ļ����͵ı�
			boolean ismultip = upload.isMultipartContent(request);
			if (ismultip) {
				// �����ļ��ϴ��ı�����
				List<FileItem> listFile = upload.parseRequest(request);
				// �ж��Ƿ����ļ�
				if (listFile != null) {
					for (FileItem fileitem : listFile) {
						// �ж��Ƿ�Ϊ��ͨ�ı����ϴ���
						boolean formField = fileitem.isFormField();
						// ��װ��ͨ���ı���
						if (formField) {
							String fieldName = fileitem.getFieldName();
							String filevalue = fileitem.getString("UTF-8");
							map.put(fieldName, filevalue);
						}
						// ��װ�ļ��ϴ���
						else {
							// ��ȡ�ļ����ϴ�����
							String filename = fileitem.getName();
							long l = System.currentTimeMillis();
							filename = l + filename;
							// ��ȡ�ϴ���
							InputStream inputStream = fileitem.getInputStream();
							// ��ȡ�ļ��ϴ��ľ��Ե�·��
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
			request.setAttribute("msg", "���û���Ϣ���³ɹ�!");
			List<User> list = service.getAllUser();
			int ammount = service.getCount();
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "���û���Ϣ����ʧ��!");
		}
		request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
	}

	/**
	 * ɾ���û�
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
			request.setAttribute("msg", "���û��Ѿ�ɾ��!");
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �û�����
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
			request.setAttribute("msg", "���û��Ѽ���!");
			request.setAttribute("list", list);
			request.setAttribute("ammount", ammount);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * �û�qq����
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
			response.getWriter().print("<script>alert('����ɹ�')</script>");
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * ����ɾ���û�������
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
			request.setAttribute("msg", "�û������ɹ�!");
			List<User> list = service.getAllUser();
			request.setAttribute("list", list);
			request.getRequestDispatcher("/admin-user-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �鿴�û������еĶ���
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
	 * ��ѯ�û������е�����
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
	 * ɾ������
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
			request.setAttribute("msg", "������ɾ��!");
			request.setAttribute("commentList", commentList);
			request.getRequestDispatcher("/admin-comment-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ����ɾ��
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
			request.setAttribute("msg", "����������ɾ��!");
			List<CommentList> commentList = service.getAllComment();
			request.setAttribute("commentList", commentList);
			request.getRequestDispatcher("/admin-comment-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡͼ�������
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
	 * ��˵��û����ݵ���������
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

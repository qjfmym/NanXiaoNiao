package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.baseServlet.BaseServlet;


public class RegisterServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	public String register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, String[]> map = request.getParameterMap();
			User user= new User();
			BeanUtils.populate(user, map);
			UserService service = (UserService) BeanFactory.getBean("UserService");
			HttpSession session = request.getSession();
			String scode = (String) session.getAttribute("scode");
			System.out.println(scode+"code");
			String check = request.getParameter("check");
			System.out.println(check+"check");
			session.removeAttribute("scode");
			session.removeAttribute("msg");
			if(!check.equalsIgnoreCase(scode)){
				request.getSession().setAttribute("msg", "验证码错误");
				//回显
				request.setAttribute("user", user);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
//				response.sendRedirect(request.getContextPath()+"/register.jsp");
			}else{
				service.register(user);
				response.sendRedirect(request.getContextPath()+"/register_ok.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//返回主界面
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		
				return null;
		
	}
	public void  findByUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserService service = (UserService) BeanFactory.getBean("UserService");
			String username = request.getParameter("username");
			User user = service.findByUsername(username);
			System.out.println(user);
			if(user!=null){
				response.getWriter().print("用户名已存在");
				
			}else{
				//response.getWriter().print("用户名OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void  findByTelephone(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserService service = (UserService) BeanFactory.getBean("UserService");
			String telephone = request.getParameter("telephone");
			User user = service.findByTelephone(telephone);
			//System.out.println(user);
			if(user!=null){
				response.getWriter().print("该手机号已被注册");
				
			}else{
				//response.getWriter().print("用户名OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void  findByEmail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserService service = (UserService) BeanFactory.getBean("UserService");
			String email = request.getParameter("email");
			User user = service.findByEmail(email);
			//System.out.println(user);
			if(user!=null){
				response.getWriter().print("该邮箱已被注册");
				
			}else{
				//response.getWriter().print("用户名OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
}

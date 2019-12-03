package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.baseServlet.BaseServlet;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	/*
	 * 用户登录
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String ucode = request.getParameter("ucode");
			HttpSession session = request.getSession();
			String scode = (String) session.getAttribute("scode");
			session.removeAttribute("scode"); 
			if(ucode==""){
				request.setAttribute("msg", "验证码不可为空");
				return "/login.jsp";
			}
			if(!ucode.equalsIgnoreCase(scode)){
				request.setAttribute("msg", "验证码错误");
				return "/login.jsp";
			}
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserService service=(UserService)BeanFactory.getBean("UserService");
			User user = service.login(username,password);
			if(user==null){
				request.setAttribute("msg", "用户名或密码错误");
				return "/login.jsp";
			}
			HttpSession session2 = request.getSession();
			session2.setAttribute("user", user);
			String autoLogin = request.getParameter("autoLogin");
			if("ok".equals(autoLogin)){
				Cookie cookie = new Cookie("auto", username+"&"+password);
				cookie.setMaxAge(3600000);
				cookie.setPath("/");
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
			}
			if(!"Y".equals(user.getStatus())) {
				request.setAttribute("msg", "您的账号未激活！");
				return "/login.jsp";
			}
			if("admin".equals(user.getName())) {
				response.sendRedirect(request.getContextPath()+"/admin_index.jsp");
				return null;
			}
			response.sendRedirect(request.getContextPath());
			
				
		} catch (Exception e) {
			e.printStackTrace();
			return "/404.html";
		}
		return null;

	}
	
	
	/*
	 * 用户退出
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		  Cookie[] cookies = request.getCookies();
	        if (cookies != null && cookies.length > 0) {
	            for (Cookie c : cookies) {
	                if ("auto".equals(c.getName())) {
	                    c.setMaxAge(0);
//	                    c.setValue(null);
	                    response.addCookie(c);
	                    break;
	                }
	            }
	        }
	        response.sendRedirect(request.getContextPath());
		return null;	
	}
	
	
	/*
	 * 用户激活
	 */
		public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				String code = request.getParameter("code");
				UserService service=(UserService)BeanFactory.getBean("UserService");
				User user=service.active(code);
				if(user==null){
					request.setAttribute("msg", "激活失败");
				}else{
					response.setHeader("refresh", "3;url="+request.getContextPath()+"/login.jsp");
					request.setAttribute("msg", "激活成功,3秒后跳像登录页面");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "/404.html";
			}
			return "/msg.jsp"; 
		
		}

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
					response.sendRedirect(request.getContextPath()+"/register.jsp");
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
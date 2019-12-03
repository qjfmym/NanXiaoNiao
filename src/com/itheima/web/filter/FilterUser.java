package com.itheima.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;

public class FilterUser implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	/**
	 * ���������Ϣ����
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ��������
		HttpServletRequest req=(HttpServletRequest)request;
		Object obj = req.getSession().getAttribute("user");
		if(obj==null){
			Cookie[] cookies = req.getCookies();
			Cookie cookie=null;
			if(cookies!=null&&cookies.length>0){
				for (Cookie c : cookies) {
					if("auto".equalsIgnoreCase(c.getName())){
						cookie=c;
					}
					
				}
			}
			if(cookie!=null){
				try {
					String username = cookie.getValue().split("&")[0];
					String password = cookie.getValue().split("&")[1];
					UserService service=(UserService)BeanFactory.getBean("UserService");
					User user = service.login(username, password);
					if(user!=null){
						System.out.println("�Զ���¼��");
						req.getSession().setAttribute("user", user);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// ����
		chain.doFilter(request, response);

		// ������Ӧ

	}

	public void destroy() {

	}

}

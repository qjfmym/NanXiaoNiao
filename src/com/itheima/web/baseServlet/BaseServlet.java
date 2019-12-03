package com.itheima.web.baseServlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.utils.Privilege;

@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取请求参数标识
		String methodName = request.getParameter("method");
		if (methodName != null) {
			try {
				// 2.获取执行者的字节码对象
				// System.out.println(this);
				Class clazz = this.getClass();
				// 3.获取指定的方法字节码对象(公共的方法public)
				Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
				// 判断该方法上是否携带了指定的注解
				boolean b = method.isAnnotationPresent(Privilege.class);
				if (b) {
					Object user = request.getSession().getAttribute("user");
					if (user == null) {
						request.setAttribute("msg", "权限不足啊,请求登录.....");
						request.getRequestDispatcher("/msg.jsp").forward(request, response);
						return;
					}
				}
				// 4.让方法执行
				String str = (String) method.invoke(this, request, response);
				if (str != null) {
					// 请求转发到指定页面
					request.getRequestDispatcher(str).forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

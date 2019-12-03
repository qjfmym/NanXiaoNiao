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
		// 1.��ȡ���������ʶ
		String methodName = request.getParameter("method");
		if (methodName != null) {
			try {
				// 2.��ȡִ���ߵ��ֽ������
				// System.out.println(this);
				Class clazz = this.getClass();
				// 3.��ȡָ���ķ����ֽ������(�����ķ���public)
				Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
				// �жϸ÷������Ƿ�Я����ָ����ע��
				boolean b = method.isAnnotationPresent(Privilege.class);
				if (b) {
					Object user = request.getSession().getAttribute("user");
					if (user == null) {
						request.setAttribute("msg", "Ȩ�޲��㰡,�����¼.....");
						request.getRequestDispatcher("/msg.jsp").forward(request, response);
						return;
					}
				}
				// 4.�÷���ִ��
				String str = (String) method.invoke(this, request, response);
				if (str != null) {
					// ����ת����ָ��ҳ��
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

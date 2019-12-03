package com.itheima.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodingUtils implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ��ȡ����ʽ
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rqs = (HttpServletResponse)response;
		rqs.setContentType("text/html;charset=utf-8");
		String method = req.getMethod();
		if("get".equalsIgnoreCase(method)){
			// �Է�����ǿ
			MyRequest myRequest = new MyRequest(req);
			//����
			chain.doFilter(myRequest, rqs);
		}else if("post".equalsIgnoreCase(method)){
			req.setCharacterEncoding("utf-8");
			//����
			chain.doFilter(req, rqs);
		}else{
			//����
			chain.doFilter(req, rqs);
		}
	}


	public void destroy() {
		// TODO Auto-generated method stub

	}

}
class MyRequest extends HttpServletRequestWrapper{

	public MyRequest(HttpServletRequest request) {
		super(request);
	}
	//��ǿ
	@Override
	public String getParameter(String name) {
		//��ȡ���������
		String value = super.getParameter(name);
		if(value==null){
			return null;
		}
		try {
			value = new String(value.getBytes("iso-8859-1"),"utf-8");
			return value;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.getParameter(name);
	}
	
}

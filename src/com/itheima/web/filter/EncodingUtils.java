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
		// 获取请求方式
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rqs = (HttpServletResponse)response;
		rqs.setContentType("text/html;charset=utf-8");
		String method = req.getMethod();
		if("get".equalsIgnoreCase(method)){
			// 对方法增强
			MyRequest myRequest = new MyRequest(req);
			//放行
			chain.doFilter(myRequest, rqs);
		}else if("post".equalsIgnoreCase(method)){
			req.setCharacterEncoding("utf-8");
			//放行
			chain.doFilter(req, rqs);
		}else{
			//放行
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
	//增强
	@Override
	public String getParameter(String name) {
		//获取乱码的内容
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

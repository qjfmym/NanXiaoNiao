package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.itheima.domain.Comment;
import com.itheima.domain.CommentAnduser;
import com.itheima.domain.Order;
import com.itheima.domain.PageBean;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.CategoryService;
import com.itheima.service.CommentService;
import com.itheima.service.FavoriteService;
import com.itheima.service.IndexService;
import com.itheima.service.RouteService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.PhoneMessage.GetPhoneMessage;
import com.itheima.utils.WeixinPay.WXPayUtil;
import com.itheima.web.baseServlet.BaseServlet;

@SuppressWarnings("all")
public class IndexServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * �����û������۹���
	 * 
	 * @author Jiayutao
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CommentService service = (CommentService) BeanFactory.getBean("CommentService");
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			Comment commentObj = new Comment();
			String comment = request.getParameter("comment");
			int rid = Integer.parseInt(request.getParameter("rid"));
			commentObj.setComment(comment);
			commentObj.setRid(rid);
			commentObj.setUid(1);
			commentObj.setCraeteDdate(new Date());
			service.addComment(commentObj);
			response.getWriter().println("������ӳɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���۵��������ʧ��!");
		}
	}

	/**
	 * ��ѯ�����������
	 * 
	 * @author Jiayutao
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAllComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommentService service = (CommentService) BeanFactory.getBean("CommentService");
		int pageNumber = 1;
		int pageSize = 3;
		PageBean<CommentAnduser> pageBean = null;
		try {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		} catch (Exception e) {
			pageNumber = 1;
		}
		try {
			HttpSession session = request.getSession();
			int rid = Integer.parseInt(request.getParameter("rid"));
			// System.out.println(pageNumber);
			pageBean = service.findPageAllComment(pageNumber, pageSize, rid);
			// System.out.println("�õ���·�ߵ�ridΪ:" + rid);
			// List<CommentAnduser> commentList = service.findAllComment(rid);
			String jsonString = JSON.toJSONString(pageBean);
			response.getWriter().println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���۵��������ʧ��!");
		}
	}

	/**
	 * ����΢��֧���Ķ�ά��
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void weixinpay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String rid = request.getParameter("rid");
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			Route route = service.findByrid(rid);
			String orderid = UUIDUtils.getId();
			String pay = WXPayUtil.pay(orderid, "1");
			System.out.println(route);
			request.setAttribute("route", route);
			request.setAttribute("orderid", orderid);
			request.setAttribute("pay", pay);
			request.getRequestDispatcher("/pay_jsp/pay.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ֧�����صĽ��
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void searchResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String out_trade_no = request.getParameter("out_trade_no");
		String result = null;
		int index = 0;
		while (true) {
			result = WXPayUtil.searchResult(out_trade_no);
			if ("PAYERROR".equals(result)) {
				response.getWriter().print("payfail");
				break;
			} else if ("SUCCESS".equals(result)) {
				response.getWriter().print("paysucceed");
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			index++;
			if (index ==6) {
				response.getWriter().print("timeout");
				break;
			}
		}
	}

	/**
	 * ΢��֧���ɹ��޸Ķ�����״̬
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void weixinPaysucceed(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Order order = new Order();
			User user = (User) session.getAttribute("user");
			String rid = request.getParameter("rid");
			String orderid = request.getParameter("orderid");
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			Route route = service.findByrid(rid);
			order.setCreatetime(new Date());
			order.setOid(orderid);
			order.setRid(Integer.parseInt(rid));
			order.setUid(user.getUid());
			service.addOreder(order);

			// ���û����Ͷ����ɹ���ʾ���ţ�
			User user1 = service.getOrderUser(1);
			String result = GetPhoneMessage.getResult(user1.getTelephone(), route.getRname());
			System.out.println(result);
			request.setAttribute("route", route);
			request.getRequestDispatcher("/pay_jsp/pay_succeed.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ҳ��������չʾ
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String popularitytravel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			String json = service.popularitytravel();
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ҳ��������չʾ
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String newtravel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			String json = service.newtravel();
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ҳ��������չʾ
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String themetravel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			String json = service.themetravel();
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * ��ҳ����������չʾ
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String inland(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			String json = service.inland();
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ��ҳ����������չʾ
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String overseas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			String json = service.overseas();
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����ҳ�ĵ���������
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryService");
			String jlist = service.findAllCategory();
			response.getWriter().print(jlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ṩ�����ķ���findRouteByPage ���ڷ����ҳ��Ϣ��չʾ
	 * 
	 * @author
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findRouteByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ����Я���ķ�����Ϣ��
		try {
			int cid = Integer.parseInt(request.getParameter("cid"));
			int curPage = 1;
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (Exception e) {
				curPage = 1;
				e.printStackTrace();
			}
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			PageBean<Route> pb = service.findRouteByPage(cid, curPage);
			request.setAttribute("pb", pb);
			request.setAttribute("cid", cid);
			return "route_list.jsp";
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ṩ�����ķ���findRouteByCidPage
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findRouteByCidPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ����Я���ķ�����Ϣ��
		try {
			int cid = Integer.parseInt(request.getParameter("cid"));
			int curPage = 1;
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));

			} catch (Exception e) {
				curPage = 1;
				e.printStackTrace();
			}
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			PageBean<Route> pb = service.findRouteByPage(cid, curPage);
			request.setAttribute("pb", pb);
			request.setAttribute("cid", cid);
			return "route_list.jsp";
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ģ��������ȡ��ҳ
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getRoute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String rname = request.getParameter("rname");
		IndexService service = (IndexService) BeanFactory.getBean("IndexService");
		try {
			int curPage = 1;
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (Exception e) {
				curPage = 1;
			}
			int pageSize = 5;
			PageBean<Route> pageBean = service.getRoute(rname, curPage, pageSize);

			request.setAttribute("rname", rname);
			request.setAttribute("pageBean", pageBean);

			request.getRequestDispatcher("routeSearch_list.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ղ����а�����
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getFavoriterank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String rname2 = request.getParameter("rname2");
		String startPrice = request.getParameter("startPrice");
		String endPrice = request.getParameter("endPrice");
		IndexService service = (IndexService) BeanFactory.getBean("IndexService");
		try {
			int curPage = 1;
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (Exception e) {
				curPage = 1;
			}
			int pageSize = 8;
			PageBean<Route> pageBean = service.getFavoriterank(rname2, startPrice, endPrice, curPage, pageSize);
			String jsonList = JSON.toJSONString(pageBean);
			response.getWriter().print(jsonList);
		} catch (Exception e) {
		}
	}

	/**
	 * ·�ߵ���ϸ����Ϣҳ
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getRouteDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String rid = request.getParameter("rid");
		IndexService service = (IndexService) BeanFactory.getBean("IndexService");
		try {
			Route route = service.getRouteDetail(rid);
			request.setAttribute("route", route);
			request.getRequestDispatcher("route_detail.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ж��Ƿ��ղ�
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String isFavoriteByRid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡrid
		int rid = Integer.parseInt(request.getParameter("rid"));
		// ��ȡuser
		User user = (User) request.getSession().getAttribute("user");
		ResultInfo result;
		if (user != null) {
			int uid = user.getUid();
			FavoriteService service = (FavoriteService) BeanFactory.getBean("FavoriteService");
			result = service.isFavoriteByRid(rid, uid);

		} else {
			result = new ResultInfo();
			result.setFlag(true);
			result.setData(false);
		}
		String resultInfo = JSON.toJSONString(result);
		response.getWriter().print(resultInfo);
		return null;
	}

	/**
	 * ͨ��ID��ѯ������Ŀ
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findRouteByRid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡrid
		int rid = Integer.parseInt(request.getParameter("rid"));
		// ����dao�㣬����rid��ȡroute����
		FavoriteService service = (FavoriteService) BeanFactory.getBean("FavoriteService");
		ResultInfo result = service.findRouteByRid(rid);
		String resultInfo = JSON.toJSONString(result);
		response.getWriter().print(resultInfo);
		return null;
	}

	/**
	 * ����ղ�
	 * 
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addFavorite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		ResultInfo result;
		if (user == null) {
			result = new ResultInfo();
			result.setFlag(true);
			result.setData(0);
			String resultInfo = JSON.toJSONString(result);
			response.getWriter().print(resultInfo);
			return null;
		}
		int rid = Integer.parseInt(request.getParameter("rid"));
		int uid = user.getUid();
		FavoriteService service = (FavoriteService) BeanFactory.getBean("FavoriteService");
		result = service.addFavorite(rid, uid);
		String resultInfo = JSON.toJSONString(result);
		response.getWriter().print(resultInfo);
		return null;
	}

	// servlet����,usernameд��,��Ҫ�Ķ�
	/**
	 * 
	 * @author ��½��չʾ�ղ��б�,����ͨ��user��ѯ�ղ�
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getFavoriteByUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		User user = (User) session.getAttribute("user");
//		System.out.println(user);
		IndexService service = (IndexService)BeanFactory.getBean("IndexService");
		try {
			int curPage = 1;
            try{
            	curPage = Integer.parseInt(request.getParameter("curPage"));
            } catch (Exception e){
            	curPage = 1;
            }
            int pageSize = 8;
            // ��Ҫ�Ķ�,�����session�л�ȡ��usernameֵ
            PageBean<Route> pageBean = service.getFavoriteByUser(curPage,pageSize);
            String jsonList = JSON.toJSONString(pageBean);
			response.getWriter().print(jsonList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * �����Ƽ�
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String Hottravel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RouteService service = (RouteService) BeanFactory.getBean("RouteService");
			String json = service.Hottravel();
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
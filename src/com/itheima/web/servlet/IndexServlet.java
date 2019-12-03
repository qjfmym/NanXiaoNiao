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
	 * 新增用户的评论功能
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
			response.getWriter().println("数据添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("评论的数据添加失败!");
		}
	}

	/**
	 * 查询所有与的评论
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
			// System.out.println("得到的路线的rid为:" + rid);
			// List<CommentAnduser> commentList = service.findAllComment(rid);
			String jsonString = JSON.toJSONString(pageBean);
			response.getWriter().println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("评论的数据添加失败!");
		}
	}

	/**
	 * 生成微信支付的二维码
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
	 * 查询支付返回的结果
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
	 * 微信支付成功修改订单的状态
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

			// 给用户发送订购成功提示短信！
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
	 * 首页人气数据展示
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
	 * 首页最新数据展示
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
	 * 首页主题数据展示
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
	 * 首页国内游数据展示
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
	 * 首页境外游数据展示
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
	 * 首首页的导航条功能
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
	 * 提供公共的方法findRouteByPage 用于分类分页信息的展示
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
		// 获取请求携带的分类信息号
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
	 * 提供公共的方法findRouteByCidPage
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
		// 获取请求携带的分类信息号
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
	 * 模糊搜索获取分页
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
	 * 收藏排行榜搜索
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
	 * 路线的详细的信息页
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
	 * 判断是否收藏
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
		// 获取rid
		int rid = Integer.parseInt(request.getParameter("rid"));
		// 获取user
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
	 * 通过ID查询旅游项目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findRouteByRid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取rid
		int rid = Integer.parseInt(request.getParameter("rid"));
		// 调用dao层，根据rid获取route对象
		FavoriteService service = (FavoriteService) BeanFactory.getBean("FavoriteService");
		ResultInfo result = service.findRouteByRid(rid);
		String resultInfo = JSON.toJSONString(result);
		response.getWriter().print(resultInfo);
		return null;
	}

	/**
	 * 添加收藏
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

	// servlet代码,username写死,需要改动
	/**
	 * 
	 * @author 登陆后展示收藏列表,并且通过user查询收藏
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
            // 需要改动,处理从session中获取的username值
            PageBean<Route> pageBean = service.getFavoriteByUser(curPage,pageSize);
            String jsonList = JSON.toJSONString(pageBean);
			response.getWriter().print(jsonList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * 热门推荐
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
package com.itheima.serviceImpl;
import java.sql.SQLException;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.Constant;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.JavaMail.MailSenderInfo;
import com.itheima.utils.JavaMail.SimpleMailSender;

public class UserServiceImpl implements UserService {
	private UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");

	// 用户登录
	public User login(String username, String password) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		User user = dao.login(username, password);
		return user;
	}

	// 用户激活
	public User active(String code) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		User user = dao.findByCode(code);
		if (user != null) {
			dao.active(user);
		}
		return user;
	}

	public void register(User user) throws SQLException {
		// 开始注册,使用事务(注册和发送邮件)
		try {
			C3P0Utils.startTransaction();
			// uid 为null
			user.setStatus(Constant.STATUS_NOT_ACTIVATED);
			String code = UUIDUtils.getCode();
			user.setCode(code);
			if ("" == user.getBirthday()) {
				user.setBirthday(null);
			}
			if(user.getImage()==null||user.getImage().trim()=="") {
				user.setImage("upload/1.jpg");
			}
			userDao.register(user);
			// 注册成功 发送邮件进行账号的激活
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setToAddress(user.getEmail());// 邮件接收者的地址
			mailInfo.setContent("恭喜你注册成功！请点击下面的连接进行账号激活:"
					+ "<a href='http://localhost:8080/admin?method=active&code=" + code + "'>"
					+ "http://localhost:8080/HeimaTravel/admin?method=activeUser&code=" + code + "</a>");// 邮件的文本内容
			mailInfo.setSubject("【账号激活】");
			SimpleMailSender sms = new SimpleMailSender();
			sms.sendHtmlMail(mailInfo);// 发送文体格式 发送html格式的邮件
			// ....
			C3P0Utils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			C3P0Utils.rollbackAndClose();
		}
	}

	public User findByUsername(String username) throws SQLException {
		if (username != "" && username != null) {
			User user = userDao.findUserByUsername(username);
			return user;
		}
		return null;
	}

	public User findByTelephone(String telephone) throws SQLException {
		if (telephone != "" && telephone != null) {
			User user = userDao.findUserByTel(telephone);
			return user;
		}
		return null;
	}

	public User findByEmail(String email) throws SQLException {
		if (email != "" && email != null) {
			User user = userDao.findUserByEmail(email);
			return user;
		}
		return null;
	}

}

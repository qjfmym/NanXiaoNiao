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

	// �û���¼
	public User login(String username, String password) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		User user = dao.login(username, password);
		return user;
	}

	// �û�����
	public User active(String code) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		User user = dao.findByCode(code);
		if (user != null) {
			dao.active(user);
		}
		return user;
	}

	public void register(User user) throws SQLException {
		// ��ʼע��,ʹ������(ע��ͷ����ʼ�)
		try {
			C3P0Utils.startTransaction();
			// uid Ϊnull
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
			// ע��ɹ� �����ʼ������˺ŵļ���
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setToAddress(user.getEmail());// �ʼ������ߵĵ�ַ
			mailInfo.setContent("��ϲ��ע��ɹ���������������ӽ����˺ż���:"
					+ "<a href='http://localhost:8080/admin?method=active&code=" + code + "'>"
					+ "http://localhost:8080/HeimaTravel/admin?method=activeUser&code=" + code + "</a>");// �ʼ����ı�����
			mailInfo.setSubject("���˺ż��");
			SimpleMailSender sms = new SimpleMailSender();
			sms.sendHtmlMail(mailInfo);// ���������ʽ ����html��ʽ���ʼ�
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

package com.itheima.utils.JavaMail;

public class Demo {
	public static void main(String[] args) {

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress("19514458@qq.com");// 邮件接收者的地址
		mailInfo.setContent("恭喜你注册成功！请点击下面的连接进行账号激活:"
				+ "<a href='http://localhost:8080/NanXiaoNiaoTravel/activeServlet?uuid="+ 1111 + "'>"
				+ "http://localhost:8080/NanXiaoNiaoTravel/activeServlet?uuid=" + 111 + "</a>");// 邮件的文本内容
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		//sms.sendHtmlMail(mailInfo);// 发送html格式
		System.out.println("成功发送！");
	}

}



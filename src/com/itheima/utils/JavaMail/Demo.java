package com.itheima.utils.JavaMail;

public class Demo {
	public static void main(String[] args) {

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress("19514458@qq.com");// �ʼ������ߵĵ�ַ
		mailInfo.setContent("��ϲ��ע��ɹ���������������ӽ����˺ż���:"
				+ "<a href='http://localhost:8080/NanXiaoNiaoTravel/activeServlet?uuid="+ 1111 + "'>"
				+ "http://localhost:8080/NanXiaoNiaoTravel/activeServlet?uuid=" + 111 + "</a>");// �ʼ����ı�����
		// �������Ҫ�������ʼ�
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// ���������ʽ
		//sms.sendHtmlMail(mailInfo);// ����html��ʽ
		System.out.println("�ɹ����ͣ�");
	}

}



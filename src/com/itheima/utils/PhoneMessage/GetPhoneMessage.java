package com.itheima.utils.PhoneMessage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * 
 *	获取短信验证码类
 *	MessageService
 *	@author 
 *	@data 2017-7-12 5:36:06
 *	@version 1.0.0
 */

public class GetPhoneMessage {

	//用户ID
	public static final String ACCOUNT_SID = "7da469d98eed4ae384251ac7f72b323f1";
	//密钥
	public static final String AUTH_TOKEN = "438427124e864e6e896cce68e7117821a";
	//请求地址前半部分
	public static final String BASE_URL = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
	//随机数
	public static String randNum = RandomUtil.getRandom();
	//语音验证码
	public static String verifyCode = randNum;
	//短信内容
	public static String smsContent =  "【南小鸟在线购物平台】尊敬的用户，您本次操作的验证码为："+randNum+"，请于1分钟内正确输入您的验证码进行信息的验证，请您确认是本人操作，如非本人操作，请忽略。";
	/**                
	 * 获取短信验证码
	 * @param to
	 * @return
	 */
	public static String getResult(String to,String content){
		smsContent =  "【南小鸟旅游网】尊敬的用户，您已经成功预约"+content+"旅游项目，南小鸟祝您旅途愉快！";
		String args = QueryUtil.qureyArguments(ACCOUNT_SID, AUTH_TOKEN, smsContent, to);
		System.out.println(args);
		OutputStreamWriter out = null;
		InputStream in = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(BASE_URL);
			URLConnection connection = url.openConnection();//打开链接
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setConnectTimeout(5000);//设置请求链接超时时间
			connection.setReadTimeout(10000);//设置读取结果超时
			//提交数据
			out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			out.write(args);
			out.flush();
			//读取返回数据
			
			in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line =br.readLine())!= null){
				sb.append(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return randNum;
	}
	public static void main(String[] args) {
		System.out.println("验证码："+randNum);
		System.out.println(getResult("15019339176","测试数据"));
		System.out.println("成功调用");
	}
}

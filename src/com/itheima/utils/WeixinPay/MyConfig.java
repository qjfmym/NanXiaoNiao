package com.itheima.utils.WeixinPay;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

public class MyConfig implements WXPayConfig {

	//��ȡ App ID����ҵ�����ں�Id��
		public String getAppID() {
			return "wx8397f8696b538317";
		}
		//��ȡ API ��Կ
		public String getKey() {
			return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
		}
		
		//��ȡ Mch ID���̻��˺ţ�
		public String getMchID() {
			return "1473426802";
		}

		//��ȡ�̻�֤�����ݣ��������ﲻ��Ҫ֤�飩

		public InputStream getCertStream() {
			// TODO Auto-generated method stub
			return null;
		}

		//HTTP(S) ���ӳ�ʱʱ�䣬��λ����
		public int getHttpConnectTimeoutMs() {
			// TODO Auto-generated method stub
			return 100*1000;
		}

		//HTTP(S) �����ݳ�ʱʱ�䣬��λ����
		public int getHttpReadTimeoutMs() {
			return 100*1000;
		}

}

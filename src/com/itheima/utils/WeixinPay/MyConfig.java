package com.itheima.utils.WeixinPay;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

public class MyConfig implements WXPayConfig {

	//获取 App ID（企业方公众号Id）
		public String getAppID() {
			return "wx8397f8696b538317";
		}
		//获取 API 密钥
		public String getKey() {
			return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
		}
		
		//获取 Mch ID（商户账号）
		public String getMchID() {
			return "1473426802";
		}

		//获取商户证书内容（我们这里不需要证书）

		public InputStream getCertStream() {
			// TODO Auto-generated method stub
			return null;
		}

		//HTTP(S) 连接超时时间，单位毫秒
		public int getHttpConnectTimeoutMs() {
			// TODO Auto-generated method stub
			return 100*1000;
		}

		//HTTP(S) 读数据超时时间，单位毫秒
		public int getHttpReadTimeoutMs() {
			return 100*1000;
		}

}

package com.itheima.utils.WeixinPay;

import com.github.wxpay.sdk.WXPay;
import com.itheima.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class WXPayUtil {

    public static void main(String[] args) {
    pay(UUIDUtils.getId(),"1");
    }
    /**
     * 单方法，用于获取支付的地址
     * @param out_trade_no
     * @return
     */
    public static String pay(String out_trade_no,String total_fee){
        //获取微信支付的配置信息
        MyConfig config = new MyConfig();
        //创建微信支付对象
        WXPay wxpay = new WXPay(config);
        //创建一个map，用于存放支付参数
        Map<String, String> data = new HashMap<String, String>();
        //支付商品的商品描述
        data.put("body", "南小鸟商城支付中心-微信支付");
        //商户的订单号，就是交易的订单id
        // data.put("out_trade_no", "2016010910595900000012");
        data.put("out_trade_no", out_trade_no);
        //交易的币种，人民币：CNY
        data.put("fee_type", "CNY");
        //订单总金额，单位为分，
        data.put("total_fee", total_fee);
        //用户的ip地址
        data.put("spbill_create_ip", "123.12.12.123");
        //支付成功后的回调地址，用于通知调用方支付的信息
        data.put("notify_url", "https://api.mch.weixin.qq.com/pay/closeorder");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        //商品id，这个参数是扫码支付的时候必传的参数
        data.put("product_id", "12");
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            //将微信支付接口生成的支付地址返回
            return resp.get("code_url");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信支付异常");
        }
    }

    /**
     * 查询订单信息，用于获取订单的支付状态
     * @return		SUCCESS—支付成功
        REFUND—转入退款
        NOTPAY—未支付
        CLOSED—已关闭
        REVOKED—已撤销（刷卡支付）
        USERPAYING--用户支付中
        PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    public static String  searchResult(String out_trade_no){
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        //交易的单号
        //data.put("out_trade_no", "2016010910595900000012");
        data.put("out_trade_no", out_trade_no);
        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
            String trade_state = resp.get("trade_state");
            System.out.println(trade_state);
            return trade_state;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询订单信息异常");
        }
    }


}

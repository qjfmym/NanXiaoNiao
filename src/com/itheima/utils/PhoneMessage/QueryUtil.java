 package com.itheima.utils.PhoneMessage;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

 /**
  *
  *	发送验证码工具类
  *	QueryUtil
  *	@author houzhiyang
  *	@data 2017-7-12 15:41:12
  *	@version 1.0.0
  */
 public class QueryUtil {

     public static String qureyArguments(String ACCOUNT_SID,String AUTH_TOKEN,String smsContent,String to){
         //时间戳
         String timestamp = getTimestamp();
         //签名验证
         String sig = MD5(ACCOUNT_SID,AUTH_TOKEN,timestamp);
         //地址参数拼接
         String str = "accountSid="+ACCOUNT_SID+"&smsContent="+smsContent+
                 "&to="+to+"&playTimes=2"+"&timestamp="+timestamp+"&sig="+sig;
         return str;
     }
     /**
      * 获取时间戳
      * @return
      */

     public static String getTimestamp(){
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
         return format.format(new Date());
     }

     public static String MD5(String... args){
         StringBuffer result = new StringBuffer();
         if(args==null||args.length==0){
             return "";
         }else{
             StringBuffer sb = new StringBuffer();
             for (String string : args) {
                 sb.append(string);
             }
             System.out.println("编码前：\t"+sb.toString());
             MessageDigest digest;
             try {
                 digest = MessageDigest.getInstance("MD5");

                 byte[] bytes = digest.digest(sb.toString().getBytes());
                 for (byte b : bytes) {
                     String hex = Integer.toHexString(b&0xff);//16进制转换
                     if(hex.length()==1){
                         result.append("0"+hex);
                     }else{
                         result.append(hex);
                     }
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return result.toString();
     }
 }

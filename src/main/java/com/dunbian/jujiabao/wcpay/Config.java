package com.dunbian.jujiabao.wcpay;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
public class Config {
    //微信支付商户开通后 微信会提供appid和appsecret和商户号partner
    public static String appid = "wx523867a009cb9d8a";
    public static String appsecret = "cdba8422389d0a5c9ea608bd3865d52d";
    public static String partner = "1248733501";//mch_id
    public static String partnerkey = "2799481738dhf8371849972jjb363700"; //这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
    public static String notifyurl = "http://www.66jjb.com/wcpay/result"; //微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    public static String notifyrechargeurl = "http://www.66jjb.com/wcrecharge/result"; //微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    public static String hostip = "211.149.227.111"; //本机ip；
    public static String saveConfigName="/config.xml";//配置文件存储名;
    public static String saveSettingName="/setting.xml";//配置文件存储名;
    public static String saveConfigPath="/WEIXIN_CONFIG/";//配置文件存储路径;
    public static String url302="/wx/content.html";
    //public static int time=7200000;//2个小时；
    public static int time=7200000;//2个小时；
}

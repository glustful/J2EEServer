package com.dunbian.jujiabao.mvc;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.PayLogAO;
import com.dunbian.jujiabao.appobj.extend.RechargeRecordAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.UserWalletAO;
import com.dunbian.jujiabao.db.generator.OrderMapper;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.order.IOrderService;
import com.dunbian.jujiabao.service.pay.IPayLogService;
import com.dunbian.jujiabao.service.recharge.IRechargeService;
import com.dunbian.jujiabao.wcpay.Config;
import com.dunbian.jujiabao.wcpay.token.GetAccessToken;
import com.dunbian.jujiabao.wcpay.token.GetJsApiTicket;
import com.dunbian.jujiabao.wcpay.utils.GetWxOrderno;
import com.dunbian.jujiabao.wcpay.utils.RequestHandler;
import com.dunbian.jujiabao.wcpay.utils.Sha1Util;
import com.dunbian.jujiabao.wcpay.utils.http.HttpHelp;
import com.dunbian.jujiabao.wcpay.weixin.PrOrder;
import com.dunbian.jujiabao.wcpay.weixin.WCReadSetting;
import com.dunbian.jujiabao.wcpay.weixin.WxPayResult;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
@Controller
@RequestMapping(value = "/wcpay")
public class WCPayController {
    @Resource
    private IRechargeService rechargeService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IAddressService addressService;
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IPayLogService payLogService;
    @ResponseBody
    @RequestMapping(value = "/result")
    public Object result(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        //把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
        System.out.print("微信支付回调数据开始");
        String inputLine;
        String notityXml = "";
        String resXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("接收到的报文：" + notityXml);
        Map m = parseXmlToList2(notityXml);
        WxPayResult wpr = new WxPayResult();
        wpr.setAppid(m.get("appid").toString());
        wpr.setBankType(m.get("bank_type").toString());
        wpr.setCashFee(m.get("cash_fee").toString());
        wpr.setFeeType(m.get("fee_type").toString());
        wpr.setIsSubscribe(m.get("is_subscribe").toString());
        wpr.setMchId(m.get("mch_id").toString());
        wpr.setNonceStr(m.get("nonce_str").toString());
        wpr.setOpenid(m.get("openid").toString());
        wpr.setOutTradeNo(m.get("out_trade_no").toString());
        wpr.setResultCode(m.get("result_code").toString());
        wpr.setReturnCode(m.get("return_code").toString());
        wpr.setSign(m.get("sign").toString());
        wpr.setTimeEnd(m.get("time_end").toString());
        wpr.setTotalFee(m.get("total_fee").toString());
        wpr.setTradeType(m.get("trade_type").toString());
        wpr.setTransactionId(m.get("transaction_id").toString());
        OrderAO orderAO = orderService.getOrderByOrderNo(wpr.getOutTradeNo());
        PayLogAO log = new PayLogAO();
        String resXmlSuccess = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        String resXmlFail = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        if ("SUCCESS".equals(wpr.getResultCode())) {
            if(orderAO == null || OrderAO.STATUS_PAID.equals(orderAO.getStatus())) {//状态已经是付款的，表示付过款的，就直接返回
                return resXmlSuccess;
            }
            //支付成功
            if(payOrder(orderAO,wpr.getTotalFee())){
                System.out.println("微信支付成功：正在修改订单成功状态");
                log.setCreateTime(new Date());
                log.setTitle("微信支付成功");
                log.setContent("微信支付回调及付款成功");
                payLogService.log(log);
                return resXmlSuccess;
            }else{
                log.setCreateTime(new Date());
                log.setTitle("微信支付成功");
                log.setContent("微信支付回调,付款失败！");
                payLogService.log(log);
                return resXmlFail;
            }
        } else {
            log.setCreateTime(new Date());
            log.setTitle("微信回调失败");
            log.setContent("微信回调及付款均失败");
            payLogService.log(log);
            return resXmlFail;
        }

//        System.out.println("微信支付回调数据结束");
//        BufferedOutputStream out = new BufferedOutputStream(
//                response.getOutputStream());
//        out.write(resXml.getBytes());
//        out.flush();
//        out.close();
    }

    /**
    * 进行支付操作
    * */
    private Boolean payOrder(OrderAO order,String totalFee){
            order.setStatus(OrderAO.STATUS_PAID);
            order.setToPay(new BigDecimal(0));
            order.setPaymentTime(new Date());
            order.setCommentStatus(OrderAO.COMMENT_STATUS_WAITFOR);
            String strTotalFee = totalFee;
            BigDecimal payAmount = null;
            if(strTotalFee != null && !strTotalFee.isEmpty()) {
                payAmount = new BigDecimal(strTotalFee);
            }
        BigDecimal b2 = new BigDecimal("100");
        BigDecimal payAmountFinal= payAmount.divide(b2,2,BigDecimal.ROUND_FLOOR);
       return orderService.payOrder(order, payAmountFinal);
    }

    /**
     * 获取accessToken等
     */
    @ResponseBody
    @RequestMapping(value = "/gettoken")
    private Object getToken(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        WCReadSetting.readSetting(request);
        String access = GetAccessToken.getAccessToken(request);
        String Ticket = GetJsApiTicket.getJsApiTicket(request, access);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("appid", Config.appid);
        retMap.put("accesstoken", access);
        retMap.put("jsapi_ticket", Ticket);
        return retMap;
    }

    /**
     * js初始化支付
     */
    @ResponseBody
    @RequestMapping(value = "/getInitParment")
    private Object getInitParment(String url,HttpServletRequest request) {
        WCReadSetting.readSetting(request);
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();//获取时间戳；
        String nonceStr = PrOrder.getNonceStr();//获取随机数；
        packageParams.put("timestamp", timestamp);
        packageParams.put("noncestr", nonceStr);
        packageParams.put("url", url);
        String access = GetAccessToken.getAccessToken(request);
        String Ticket = GetJsApiTicket.getJsApiTicket(request, access);
        packageParams.put("jsapi_ticket",Ticket);
        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(Config.appid, Config.appsecret, Config.partnerkey);
        String sign="";
        try {
             sign= Sha1Util.createSHA1Sign(packageParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("timestamp："+timestamp+"   noncestr："+nonceStr+"   url："+url+"jsapi_ticket："+Ticket+"   signature"+sign);
        //String sign = reqHandler.createSign(packageParams);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("appid", Config.appid);
        retMap.put("timestamp", timestamp);
        retMap.put("nonceStr", nonceStr);
        retMap.put("signature", sign);
        return retMap;
    }

    /**
     * 接收微信回调，并获取OpenId、生成PrePayId等；
     */
    @ResponseBody
    @RequestMapping(value = "/canOrderWithTime")
    private Object canOrderWithTime(String orderNo) {
        boolean canOrder = true;
        Map<String, Object> retMap = new HashMap<String, Object>();
        try{
            OrderAO order= orderService.getOrderByOrderNo(orderNo);
            RegionTimeAO regionTimeAO = addressService.getRegionTime(order.getTownId());
            if(regionTimeAO != null && regionTimeAO.getEndTime() != null) {
                Date endTime = regionTimeAO.getEndTime();
                //首先把当前的时间的年月日设置到订购限制时间上
                Date now = new Date();
                String realEndTimeStr = DateTimeUtil.format(now, DateTimeUtil.FORMAT_YMD) + " "
                        + DateTimeUtil.format(endTime, DateTimeUtil.FORMAT_HM);
                endTime = DateTimeUtil.parse(realEndTimeStr, DateTimeUtil.FORMAT_YMD_HM);
                long between=endTime.getTime()-now.getTime();
                if(between < 0) { //已经超过下单时间
                    canOrder = false;
                }
            }
            if(canOrder){
                retMap.put("res", true);
            }else{
                retMap.put("res", false);
                retMap.put("msg","超出下单时间，不能支付。");
            }
        }catch (Exception error){
            retMap.put("res", false);
            retMap.put("msg","未找到该订单！");
        }

        return retMap;
    }
    /**
     * 接收微信回调，并获取OpenId、生成PrePayId等；
     */
    @ResponseBody
    @RequestMapping(value = "/getPrepayId")
    private ModelAndView getPrepayId(String openId,String orderNo,HttpServletRequest request) {
        WCReadSetting.readSetting(request);
        ModelAndView mav = new ModelAndView();
        System.out.println("openID：" + openId+" orderNo:"+orderNo);
        String getPayStr = "";
        OrderAO orderAO = new OrderAO();
        SortedMap<String, String> getMap=new TreeMap<String, String>();
        try {
            orderAO = orderService.getOrderByOrderNoWithDetail(orderNo);
        } catch (Exception error) {
            getPayStr = "{\"res\":" + false + ",\"msg\":\"get order error\"}";
        }
        try {
            System.out.println(Config.hostip+" "+orderAO.getAmount().toString()+" "+orderAO.getOrderNo()+" "+openId);
            getMap = PrOrder.createPrOrder("居家宝微信支付", Config.hostip, orderAO.getAmount().toString(), orderAO.getOrderNo(), openId, Config.notifyurl);
            getPayStr = "{\"res\":" + true + ",\"getPayStr\":{" + getPayStr + "}}";
        } catch (Exception error) {
            getPayStr = "{\"res\":" + false + ",\"msg\":\"get prepayid error\"}";
        }
        System.out.println("getPayStr：" + getPayStr);
        String userId = orderAO.getUserId();

        mav.setViewName("/wechat/pay"); //返回的文件名
        mav.addObject("appId", getMap.get("appId"));
        mav.addObject("timeStamp", getMap.get("timeStamp"));
        mav.addObject("nonceStr", getMap.get("nonceStr"));
        mav.addObject("package", getMap.get("package"));
        mav.addObject("signType",getMap.get("signType"));
        mav.addObject("paySign",getMap.get("paySign"));
        mav.addObject("orderId",orderAO.getId());
        mav.addObject("orderNo",orderAO.getOrderNo());
        mav.addObject("userId",userId);
        mav.addObject("walletMoney",orderAO.getAmount());
        try{
            UserWalletAO userWalletAO = rechargeService.getUserWallet(userId);
            mav.addObject("validAmount",userWalletAO.getValidAmount());
            mav.addObject("frozenAmount",userWalletAO.getFrozenAmount());
        }catch(Exception error){
            mav.addObject("validAmount","0");
            mav.addObject("frozenAmount","0");
        }

        return mav;
    }



    /**
     * 调用支付页面
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPayPage")
    private ModelAndView getPayPage(String openId,String recordId,HttpServletRequest request) {
        WCReadSetting.readSetting(request);
        System.out.println("openID：" + openId+" recordId:"+recordId);
        //这里读取订单数据
        String getPayStr = "";
        Map<String, Object> retMap = new HashMap<String, Object>();
        RechargeRecordAO record = new RechargeRecordAO();
        SortedMap<String, String> getMap=new TreeMap<String, String>();
        try {
            record = rechargeService.getRechargeById(recordId);
        } catch (Exception error) {
            getPayStr = "{\"res\":" + false + ",\"msg\":\"get record error\"}";
            System.out.println(retMap);
        }
        try {
            System.out.println(Config.hostip+" "+record.getAmount().toString()+" "+record.getId()+" "+openId);
            getMap = PrOrder.createPrOrder("居家宝充值", Config.hostip, record.getAmount().toString(), record.getId(), openId, Config.notifyrechargeurl);
            getPayStr = "{\"res\":" + true + ",\"getPayStr\":{" + getPayStr + "}}";
        } catch (Exception error) {
            getPayStr = "{\"res\":" + false + ",\"msg\":\"get prepayid error\"}";
        }
        System.out.println("getPayStr：" + getPayStr);
        //  return assembleHtml(getPayStr);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/wechat/recharge"); //返回的文件名
        mav.addObject("appId", getMap.get("appId"));
        mav.addObject("timeStamp", getMap.get("timeStamp"));
        mav.addObject("nonceStr", getMap.get("nonceStr"));
        mav.addObject("package", getMap.get("package"));
        mav.addObject("signType",getMap.get("signType"));
        mav.addObject("paySign",getMap.get("paySign"));
        return mav;
    }

    /**
     * 更新配置文件
     */
    @ResponseBody
    @RequestMapping(value = "/updataConfig")
    private Object updataConfig(String pass,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(pass.equals("klkVvK47sIhkDV_E6l_1pdF_9NQuRpluyht0vuVpEtK7eBondABl_IiRMF_ZT6I5FcVZ6_4qkSpEpgOXq77yqtSFs79N-FOx8r_dmZPpF8Y")){
            retMap.put("res", GetJsApiTicket.updata(request));
        }else{
            retMap.put("res", false);
        }
        return retMap;
    }

    /**
     * 更新AccessToken
     */
    @ResponseBody
    @RequestMapping(value = "/updataAccessToken")
    private Object updataAccessToken(String pass,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(pass.equals("klkVvK47sIhkDV_E6l_1pdF_9NQuRpluyht0vuVpEtK7eBondABl_IiRMF_ZT6I5FcVZ6_4qkS pEpgOXq77yqtSFs79N-FOx8r_dmZPpF8Y")){
            retMap.put("res", true);
            retMap.put("data",GetAccessToken.updataAccessToken(request));
        }else{
            retMap.put("res", false);
            retMap.put("data","worry pass");
        }
        return retMap;
    }

    /**
     * 获取openid
     */
    @ResponseBody
    @RequestMapping(value = "/getOpenId")
    private void getOpenId(String code,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WCReadSetting.readSetting(request);
        String OpenId = getOpenIdNow(code);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("OpenId",OpenId);
        response.setHeader("location", Config.url302 + "?openId=" + OpenId);
        response.setStatus(302);
        //return retMap;
    }

    /**
     * 获取appid
     */
    @ResponseBody
    @RequestMapping(value = "/getAppId")
    private Object getAppId(HttpServletRequest request) {
        WCReadSetting.readSetting(request);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("appId", Config.appid);
        return retMap;
    }

    /**
     * 从微信获取订单信息
     */
    @ResponseBody
    @RequestMapping(value = "/getWcOrderInf")
    private Object getWcOrderInf(String orderNo,HttpServletRequest request) {
        WCReadSetting.readSetting(request);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            SortedMap<String, String> packageParams = new TreeMap<String, String>();
            String nonceStr = PrOrder.getNonceStr();//获取随机数；
            packageParams.put("appid", Config.appid);
            packageParams.put("mch_id", Config.partner);
            packageParams.put("nonce_str", nonceStr);
            packageParams.put("out_trade_no", orderNo);
            RequestHandler reqHandler = new RequestHandler(null, null);
            reqHandler.init(Config.appid, Config.appsecret, Config.partnerkey);
            String sign = reqHandler.createSign(packageParams);
            String getInfStr = "<xml>"
                    + "<appid>" + Config.appid + "</appid>"
                    + "<mch_id>" + Config.partner + "</mch_id>"
                    + "<nonce_str>" + nonceStr + "</nonce_str>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<sign>" + sign + "</sign>"
                    + "</xml>";
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            String res = new GetWxOrderno().getPayNo(url, getInfStr);
            Map<String, String> mRes = parseXmlToList2(res);
            String return_code=mRes.get("return_code");
            String result_code=mRes.get("result_code");

            if(return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")){
                resultMap.put("res",false );
                resultMap.put("msg","获取成功");
                resultMap.put("openid",mRes.get("openid") );
                resultMap.put("is_subscribe",mRes.get("is_subscribe") );
                resultMap.put("trade_type",mRes.get("trade_type") );
                resultMap.put("bank_type",mRes.get("bank_type") );
                resultMap.put("total_fee", mRes.get("total_fee"));
                resultMap.put("fee_type", mRes.get("fee_type"));
                resultMap.put("transaction_id",mRes.get("transaction_id"));
                resultMap.put("out_trade_no",mRes.get("out_trade_no") );
                resultMap.put("attach", mRes.get("attach"));
                resultMap.put("time_end",mRes.get("time_end") );
                resultMap.put("trade_state",mRes.get("trade_state") );
            }else{
                resultMap.put("res",false );
                resultMap.put("msg","获取出错");
            }
        }catch(Exception error){
            resultMap.put("res",false );
            resultMap.put("msg",error.getMessage());
        }
        return  resultMap;
    }

    /**
     * 获取openId
     *
     * @param code
     * @return
     */
    private String getOpenIdNow(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Config.appid + "&secret=" + Config.appsecret + "&code=" + code + "&grant_type=authorization_code";
        String result = HttpHelp.Get(url);
        String openId=null;
        try{
            JSONObject jb=JSONObject.parseObject(result);
            openId=  jb.getString("openid");
            System.out.println("获取到的openID：" + openId);
        }catch(Exception error){
        }
        System.out.println("获取到result：" + result);
        return openId;
    }

    /*
    *组装一个页面
     */
    private static String assembleHtml(String paramentStr){
        String htmlPage="<html lang=\"zh-CN\"><body><h1>"+paramentStr+"</h1></body><script>var parament="+paramentStr+"</script></body></html>";
        return htmlPage;
    }
    /**
     * description: 解析微信通知xml
     */
    @SuppressWarnings({"unused", "rawtypes", "unchecked"})
    private static Map parseXmlToList2(String xml) {
        Map retMap = new HashMap();
        try {
            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            SAXBuilder sb = new SAXBuilder();// 创建一个新的SAXBuilder
            Document doc = (Document) sb.build(source);// 通过输入源构造一个Document
            Element root = doc.getRootElement();// 指向根节点
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

}

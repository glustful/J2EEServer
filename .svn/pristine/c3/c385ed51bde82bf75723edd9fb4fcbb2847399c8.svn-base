package com.dunbian.jujiabao.mvc;

import com.dunbian.jujiabao.appobj.extend.*;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.session.SessionUtil;
import com.dunbian.jujiabao.framework.util.IPUtil;
import com.dunbian.jujiabao.framework.util.VerifyUtil;
import com.dunbian.jujiabao.message.ISMSService;
import com.dunbian.jujiabao.service.recharge.IRechargeService;
import com.dunbian.jujiabao.service.user.IUserService;
import com.dunbian.jujiabao.util.UserUtil;
import com.dunbian.jujiabao.wcpay.Config;
import com.dunbian.jujiabao.wcpay.weixin.PrOrder;
import com.dunbian.jujiabao.wcpay.weixin.WCReadSetting;
import com.dunbian.jujiabao.wcpay.weixin.WxPayResult;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
@Controller
@RequestMapping(value = "/wcrecharge")
public class WCRechargeController {
    @Resource
    private IRechargeService rechargeService;
    @Resource
    private ISMSService smsService;
    @Resource
    private IUserService userService;

    /**
     * 当前用户钱包是否存在
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isWalletExists")
    public Object isWalletExists(HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        // Boolean res=rechargeService.isExistsWallet(user.getId());
        Map<String,Object> reslut=new HashMap<String,Object>();
        Result<Boolean> res=rechargeService.isExistsWallet(user.getId());
        reslut.put("res", res.getData());
        reslut.put("msg",res.getMsg());
        return reslut;
    }

    /**
     * 创建当前用户钱包
     * @param pass
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createWallet")
    public Object createWalletWithPass(String pass,HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        Map<String,Object> reslut=new HashMap<String,Object>();
        Result<Boolean> res=rechargeService.setPass(user.getId(), pass);
        reslut.put("res", res.getData());
        reslut.put("msg",res.getMsg());
        return reslut;
    }

    /**
     * 获取当前钱包及充值活动
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRechargeSetList")
    public Object getRechargeSetList(HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        UserWalletAO wallet = rechargeService.getUserWallet(user.getId());
        List<RechargeSetAO> setList = rechargeService.getRechargeSetList();
        Map<String, Object> ret = new HashMap<>();
        ret.put("wallet", wallet);
        ret.put("setList", setList);
        return ret;
    }

    /**
     * 增加充值记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/genRechargeRecord")
    public Object genRechargeRecord(String setId, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        Result<RechargeRecordAO> ret = rechargeService.genRecharge(setId, user.getId());
        Map<String, Object> res = new HashMap<>();
        res.put("res", ret.isSuccess());
        res.put("msg", ret.getMsg());
        res.put("data", ret.getData());
        return res;
    }

    /**
     * 使用钱包支付
     */
    @RequestMapping(value="/walletPay")
    @ResponseBody
    public Object walletPay(String orderId, String payPass, BigDecimal walletMoney,String userId ,HttpServletRequest request) {
        System.out.println("orderId：" + orderId+" walletMoney:"+walletMoney+" payPass:"+payPass+" userId:"+userId);
        Map<String, Object> reslut = new HashMap<>();
        Result<OrderAO> res=rechargeService.walletPay(userId, orderId, payPass, walletMoney);
        reslut.put("res", res.isSuccess());
        reslut.put("msg",res.getMsg());
        return reslut;
    }

    /**
     * 发验证短信
     */
    @RequestMapping(value="/findPayPassMsg", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findPayPassMsg(String mobile, HttpServletRequest request) {
        if(mobile == null || mobile.length() != 11 || !mobile.startsWith("1")) {
            return new Result<>(false, "请输入正确的手机号");
        }
        String yzm = VerifyUtil.genSmsWords(mobile, request);
        Map<String, Object> reslut = new HashMap<>();
        Result<Boolean> res=smsService.send(mobile, "您的验证码是：" + yzm + "。请不要把验证码泄露给其他人。 ", IPUtil.getRealIp(request), request.getSession().getId(), null);
        reslut.put("res", res.isSuccess());
        reslut.put("msg",res.getMsg());
        return reslut;
    }

    /**
     * 根据短信重置密码
     */
    @RequestMapping(value="/findPayPass", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findPayPass(String mobile, String verifyCode, String newPass, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        Map<String, Object> reslut = new HashMap<>();
        if(mobile == null || !mobile.equals(user.getUserName())) {
            reslut.put("res", false);
            reslut.put("msg", "请使用注册手机号找回");
            return reslut;
        }
        if(newPass == null || "".equals(newPass.trim())) {
            reslut.put("res", false);
            reslut.put("msg", "新密码不能为空");
            return reslut;
        }
        boolean verified = false;
        verified = VerifyUtil.isSmsVerified(verifyCode, mobile, request);
        if(!verified) {
            if(!checkSMSError(mobile, request)) {
                VerifyUtil.clearSmsVerify(request);
            }
            reslut.put("res", false);
            reslut.put("msg", "短信验证码输入错误");
            return reslut;
        }
        Result<Boolean> ret = rechargeService.resetPass(user.getId(), newPass);
        reslut.put("res", ret.isSuccess());
        reslut.put("msg",ret.getMsg());
        if(ret != null && ret.isSuccess()) {
            VerifyUtil.clearSmsVerify(request);
        }
        return reslut;
    }


    /**
     * 获取充值记录列表
     * @param pageNo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getRecordList")
    public Object getRecordList(Integer pageNo, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        if(pageNo == null) {
            pageNo = 1;
        }
        Page page = new Page(pageNo, 5);
        page.setPageSize(page.getPageSize() + 1);
        List<AccountDetailAO> dataList = rechargeService.getDetailList(user.getId(), page);
        Map<String, Object> ret = new HashMap<>();
        ret.put("recordList", dataList);
        return ret;
    }

    /**
     * 重置密码
     * @param oldPwd
     * @param newPwd
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/resetPwd")
    public Object resetPwd(String oldPwd, String newPwd, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        Map<String,Object> reslut=new HashMap<String,Object>();
        UserWalletAO userWalletAO = rechargeService.getUserWallet(user.getId());
        if(oldPwd == null || oldPwd.isEmpty() || newPwd == null || newPwd.isEmpty()) {
            reslut.put("res", false);
            reslut.put("msg","新旧密码都不能为空！");
            return reslut;
        }

        Result<Boolean> res= rechargeService.resetPass(userWalletAO.getId(), oldPwd, newPwd);
        reslut.put("res", res.isSuccess());
        reslut.put("msg",res.getMsg());
        return reslut;
    }

    /**
     * 微信异步通知回调接口
     */
    @ResponseBody
    @RequestMapping(value = "/result")
    public void result(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        System.out.println("==========微信异步通知回调接口===========");
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
        if ("SUCCESS".equals(wpr.getResultCode())) {
            //支付成功
            if(setRecharge(wpr.getOutTradeNo())){
                System.out.println("微信充值成功：正在修改订单成功状态");
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }else{
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                System.out.println("微信充值失败：本地充值失败！");
            }
            return;
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            System.out.println("微信充值失败：正在修改订单失败状态");
        }

        System.out.println("微信充值回调数据结束");
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 进行充值事物事件：修改充值记录状态、总账操作、用户钱包操作、总账记录操作；
     * @param recordId
     */
    private Boolean setRecharge(String recordId){
        return rechargeService.recharge(recordId).getData();
    }

    private boolean checkSMSError(String mobile, HttpServletRequest request) {
        SMSValidationError error = SessionUtil.getAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, SMSValidationError.class, request);
        if(error == null) {
            error = new SMSValidationError();
            error.setKeepTime(System.currentTimeMillis());
            error.setLastTime(System.currentTimeMillis());
            error.setCount(1);
            error.setMobile(mobile);
            SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
        } else {
            if(System.currentTimeMillis() - error.getKeepTime() > SMSValidationError.CLEAN_TIME) {
                error.setKeepTime(System.currentTimeMillis());
                error.setLastTime(System.currentTimeMillis());
                error.setCount(1);
                error.setMobile(mobile);
                SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
            } else {
                if(error.getCount() + 1 >= SMSValidationError.MAX_COUNT) {
                    return false;
                } else {
                    error.setLastTime(System.currentTimeMillis());
                    error.setCount(error.getCount() + 1);
                    error.setMobile(mobile);
                    SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
                }
            }
        }

        return true;
    }

    /**
     *
     */

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

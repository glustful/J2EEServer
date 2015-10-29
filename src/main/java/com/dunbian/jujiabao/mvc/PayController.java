package com.dunbian.jujiabao.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.alipay.config.AlipayConfig;
import com.dunbian.jujiabao.alipay.util.AlipaySubmit;
import com.dunbian.jujiabao.alipay.util.UtilDate;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.RechargeRecordAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.order.IOrderService;
import com.dunbian.jujiabao.service.recharge.IRechargeService;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping(value="/pay")
public class PayController {

	private static final Logger LOG = Logger.getLogger(PayController.class.getCanonicalName());
	
	@Resource
	private IOrderService orderService;
	
	@Resource
	IAddressService addressService;
	
	@Resource
	IRechargeService rechargeService;
	
	//支付宝wap网关地址
	private String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";
    /**
     * 支付宝网站支付URL(新)
     */
    private static final String ALIPAY_GATEWAY_SITE = "https://mapi.alipay.com/gateway.do?";
    
	//返回格式
	private String format = "xml";
	private String v = "2.0";
	
	private String host = "http://www.66jjb.com";
	
	//wap支付服务器异步通知页面路径
	private String phone_notify = host + "/order/phone/nofilter/alipay-call-back";
	//wap支付页面跳转同步通知页面路径
	private String phone_payreturn = host + "/order/phone/nofilter/payreturn";
	//wap支付操作中断返回地址
	private String phone_merchant = host + "/order/phone/nofilter/paybreak";
	
	//web支付服务器异步通知页面路径
	private String web_notify = host + "/order/nofilter/alipay-call-back";
	//web支付页面跳转同步通知页面路径
	private String web_payreturn = host + "/order/nofilter/payreturn";
	//web支付操作中断返回地址
	private String web_merchant = host + "/order/orderlist/" + OrderAO.STATUS_ORDERED;
		
	
	
	//wap充值服务器异步通知页面路径
	private String recharge_phone_notify = host + "/order/phone/nofilter/recharge-alipay-call-back";
	//wap充值支付页面跳转同步通知页面路径
	private String recharge_phone_payreturn = host + "/order/phone/nofilter/recharge-payreturn";
	//wap充值付款操作中断返回地址
	private String recharge_phone_merchant = host + "/order/phone/nofilter/recharge-paybreak";
		
	//web充值服务器异步通知页面路径
	private String recharge_web_notify = host + "/order/nofilter/recharge-alipay-call-back";
	//web充值支付页面跳转同步通知页面路径
	private String recharge_web_payreturn = host + "/order/nofilter/recharge-payreturn";
	//web充值付款操作中断返回地址
	private String recharge_web_merchant = host + "/recharge/detaillist";
	
	
	//订单名称
	private String subject =  "居家宝订餐";
	//卖家支付宝帐户
	private String seller_email = AlipayConfig.seller_email;

	@Resource
	private ICartService cartService;
	
	public void setCurrentUser(Model model, HttpServletRequest request) {
		try {
			UserAO user = UserUtil.getCurrentLoginUser(request);
			model.addAttribute("currentUser", user);
			int cartCount = cartService.getCartCount(user.getId());
			model.addAttribute("cartCount", cartCount);
		} catch (Exception e) {}
	}
	
	
	@RequestMapping(value="")
	public ModelAndView payPc(String orderNo, String from, Model model,  HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		//这里读取订单数据
		UserAO user = UserUtil.getCurrentLoginUser(request);
		OrderAO order = orderService.getOrderByOrderNoWithDetail(orderNo);
		
		if(user != null && order != null && user.getId().equals(order.getUserId())) {
			model.addAttribute("order", order);
			UserAddressAO userAddressAO = addressService.getDefaultAddress(order.getAddressId());
		    String deliveryTime = "12:00 - 13:00";
		    if(userAddressAO != null && !userAddressAO.getTownId().isEmpty()) {
					RegionTimeAO regionTimeAO = addressService.getRegionTime(order.getAddressInfo().getTownId());
					if(regionTimeAO != null && regionTimeAO.getDeliveryTime() != null) {
						deliveryTime = regionTimeAO.getDeliveryTime();
					}
		    }
		        
		    String orderTip = "我们将在今天" + deliveryTime + "为您送达，请耐心等待.";
		   
		    order.setOrderTip(orderTip);
		}
		if(from != null && from.equals("1")) {
			model.addAttribute("title", "请确认订单信息");
		} else {
			model.addAttribute("title", "已成功下单，请完成付款");
		}
		
		setCurrentUser(model, request);
		return MVCViewName.INDEX_ORDER_SUCCESS.view();
	}
	
	@RequestMapping(value = "/do")
	public void doPayPc(String orderNo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//这里读取订单数据
		 OrderAO order = orderService.getOrderByOrderNo(orderNo);
		 boolean needBreak = false;
		String breakMsg = "";
		if(order == null) {
			needBreak = true;
			breakMsg = "数据异常，没找到对应订单";
		} else {
			if(!canOrderWithTime(order)) {
				needBreak = true;
				breakMsg = "超过下单时间，无法完成付款！";
				orderService.cancel(order.getUserId(), order.getId());
			}
		}
		if(needBreak) {
			breakPay(response, breakMsg, web_merchant);
			return;
		}
			
		//付款金额
		String totalFee = order.getToPay().toString();   //必填
		doAliPayWeb(orderNo, web_notify, web_payreturn, totalFee, response);
	}
	
	
	@RequestMapping(value = "/walletdo")
	public Object doWalletPayPc(String orderNo, HttpServletRequest request, Model model, HttpServletResponse response) throws IOException{
		UserAO user = UserUtil.getCurrentLoginUser(request);
		setCurrentUser(model, request);
		
		//这里读取订单数据
		 OrderAO order = orderService.getOrderByOrderNo(orderNo);
		 
		 boolean needBreak = false;
		String breakMsg = "";
		if(order == null) {
			needBreak = true;
			breakMsg = "数据异常，没找到对应订单";
		} else {
			if(!canOrderWithTime(order)) {
				needBreak = true;
				breakMsg = "超过下单时间，无法完成付款！";
				orderService.cancel(order.getUserId(), order.getId());
			}
		}
		if(needBreak) {
			breakPay(response, breakMsg, web_merchant);
			return null;
		}
		
		OrderAO tmpOrderAO = rechargeService.prepare4Pay(user.getId(), order.getId()).getData();
		model.addAttribute("order", tmpOrderAO);
		return MVCViewName.INDEX_PAYBANK.view();		 
	}
	
	/**
	 * 
	 * @param recordId 充值记录id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/recharge")
	public void rechargePc(String setId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//这里读取订单数据
		UserAO user = UserUtil.getCurrentLoginUser(request);
		Result<RechargeRecordAO> ret = rechargeService.genRecharge(setId, user.getId());
		boolean needBreak = false;
		String breakMsg = "";
		RechargeRecordAO record = null;
		if(ret == null || !ret.isSuccess()) {
			needBreak = true;
			breakMsg = "数据异常，没找到对应充值记录！";
		} else {
			record = ret.getData();
		}
		
		if(record == null) {
			needBreak = true;
			breakMsg = "数据异常，没找到对应充值记录！";
		} else {//检查是否是本人的账号
			if(!user.getId().equals(record.getUserId())) {
				needBreak = true;
				breakMsg = "充值记录非当前用户！";
			}else if(!RechargeRecordAO.STATUS_TOPAY.equals(record.getStatus())) {
				needBreak = true;
				breakMsg = "充值记录不是待付款状态！";
			}
		}
		
		if(needBreak) {
			breakPay(response, breakMsg, recharge_web_merchant);
			return;
		}
		
		//付款金额
		String totalFee = record.getAmount().toString();   //必填
		doAliPayWeb(record.getId(), recharge_web_notify, recharge_web_payreturn, totalFee, response);
	}
	
	/**
	 * 
	 * @param recordId 充值记录id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/phone/recharge")
	public void rechargePhone(String recordId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//这里读取订单数据
		RechargeRecordAO record = rechargeService.getRechargeById(recordId);
		boolean needBreak = false;
		String breakMsg = "";
		if(record == null) {
			needBreak = true;
			breakMsg = "数据异常，没找到对应充值记录！";
		} else {//检查是否是本人的账号
			UserAO user = UserUtil.getCurrentLoginUser(request);
			if(!user.getId().equals(record.getUserId())) {
				needBreak = true;
				breakMsg = "充值记录非当前用户！";
			}else if(!RechargeRecordAO.STATUS_TOPAY.equals(record.getStatus())) {
				needBreak = true;
				breakMsg = "充值记录不是待付款状态！";
			}
		}
		
		if(needBreak) {
			breakPay(response, breakMsg, recharge_phone_merchant);
			return;
		}
		
		//付款金额
		String totalFee = record.getAmount().toString();   //必填
		doAliPayWap(recordId, recharge_phone_notify, recharge_phone_payreturn, recharge_phone_merchant, totalFee, response);
	}
	
	@RequestMapping(value="/phone")
	public void pay(String orderNo, Model model,  HttpServletResponse response) throws UnsupportedEncodingException {
		
		//必填，须保证每次请求都是唯一
		//这里读取订单数据
		OrderAO order = orderService.getOrderByOrderNo(orderNo);
		Result<Boolean> testUserResult = orderService.deal4AppleTest(order);
		if(testUserResult != null && testUserResult.getData() != null && testUserResult.getData()) {
			breakForTest(response);
			return;
		}
		
		boolean needBreak = false;
		String breakMsg = "";
		if(order == null) {
			needBreak = true;
			breakMsg = "数据异常，没找到对应订单";
		} else {
			if(!canOrderWithTime(order)) {
				needBreak = true;
				breakMsg = "超过下单时间，无法完成付款！";
				orderService.cancel(order.getUserId(), order.getId());
			}
		}
		if(needBreak) {
			breakPay(response, breakMsg, phone_merchant);
			return;
		}
		//付款金额
		String totalFee = order.getToPay().toString();
		
		doAliPayWap(orderNo, phone_notify, phone_payreturn, phone_merchant, totalFee, response);
	}
	
	//调用支付宝付款页面
	/**
	 * 
	 * @param dataNo 订单no或者充值的recordid
	 * @param totalFee
	 * @param response
	 * @throws IOException
	 */
	private void doAliPayWeb(String dataNo, String notify, String payreturn, String totalFee, HttpServletResponse response)  throws IOException{
		//支付类型
		String paymentType = "1";    //必填，不能修改
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", paymentType);
		sParaTemp.put("notify_url", notify);
		sParaTemp.put("return_url", payreturn);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", dataNo);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", totalFee);
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_SITE, AlipayConfig.web_sign_type, sParaTemp,"get","确认");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println(sHtmlText);
		writer.flush();
	}
	
		
	//调用支付宝付款页面
	private void doAliPayWap(String orderNo, String notify, String payreturn, String paybreak, String totalFee, HttpServletResponse response) {
		//请求号
		String reqId = UtilDate.getOrderNum();
		
		//请求业务参数详细
		String req_dataToken = "<direct_trade_create_req><notify_url>" + notify + "</notify_url><call_back_url>" + payreturn 
				+ "</call_back_url><seller_account_name>" + seller_email 
				+ "</seller_account_name><out_trade_no>" + orderNo + "</out_trade_no><subject>" + subject 
				+ "</subject><total_fee>" + totalFee + "</total_fee><merchant_url>" 
				+ paybreak + "</merchant_url></direct_trade_create_req>";
		//必填
		
		//把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", AlipayConfig.partner);
		sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
		sParaTempToken.put("sec_id", AlipayConfig.wap_sign_type);
		sParaTempToken.put("format", format);
		sParaTempToken.put("v", v);
		sParaTempToken.put("req_id", reqId);
		sParaTempToken.put("req_data", req_dataToken);
		LOG.info("sParaTempToken:"+sParaTempToken);
		//建立请求
		String sHtmlTextToken = null;
		String request_token = null;
		try {
			sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, AlipayConfig.wap_sign_type, "", "",sParaTempToken);
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,AlipayConfig.input_charset);
			//URLDECODE返回的信息
			LOG.info("sHtmlTextToken:"+sHtmlTextToken);
			//获取token
			request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		////////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////
		
		//业务详细
		String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
		//必填
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("sec_id", AlipayConfig.wap_sign_type);
		sParaTemp.put("format", format);
		sParaTemp.put("v", v);
		sParaTemp.put("req_data", req_data);
		LOG.info("sParaTemp:"+sParaTemp.toString());
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, AlipayConfig.wap_sign_type, sParaTemp, "get", "确认");
		OutputStream out = null;
		try {
			response.reset();
			response.setContentType("text/html");
			out = response.getOutputStream();
			out.write(sHtmlText.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null) {
				try{
					response.flushBuffer();
				} catch(Exception e){}
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//检查订单是否超时
	private Boolean canOrderWithTime(OrderAO order) {
		boolean canOrder = true;
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
		return canOrder;
	}
	
	//用于苹果测试账号的跳转逻辑
	private void breakForTest(HttpServletResponse response) {
		OutputStream out = null;
		try {
			response.reset();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			out.write(("<span style='font-weight: 400;font-size: 18px'>您当前在使用账号为苹果app测试账号，已成功支付<a href='" + phone_payreturn + "'>返回商家页面</a></span>").getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}
	
	//不符合支付条件的时候终止支付过程
	private void breakPay(HttpServletResponse response, String breakMsg, String url) {
		OutputStream out = null;
		try {
			response.reset();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			StringBuffer countent = new StringBuffer("<script>var gnl=confirm('").append(breakMsg).append("');");
			countent.append("window.location='").append(url).append("';");
			countent.append("</script>");
			out.write(countent.toString().getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}
}

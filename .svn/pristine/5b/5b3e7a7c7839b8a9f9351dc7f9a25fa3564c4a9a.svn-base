package com.dunbian.jujiabao.mvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.JavaType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.alipay.config.AlipayConfig;
import com.dunbian.jujiabao.alipay.util.AlipayNotify;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderExtraAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.json.JsonUtil;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.comment.ICommentService;
import com.dunbian.jujiabao.service.order.IOrderService;
import com.dunbian.jujiabao.service.recharge.IRechargeService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.CookieUtil;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	
	private static final Logger LOG = Logger.getLogger(OrderController.class.getName());
	
	private String host = "http://www.66jjb.com";
	
	@Resource
	private IOrderService orderService;
	
	@Resource
	private ICommentService commentService;
	
	@Resource
	private IAddressService addressService;
	
	@Resource
	private ICartService cartService;

	@Resource
	private IRechargeService rechargeService;
	
	public void setCurrentUser(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("currentMenu", "user");
			UserAO user = UserUtil.getCurrentLoginUser(request);
			model.addAttribute("currentUser", user);
			int cartCount = cartService.getCartCount(user.getId());
			model.addAttribute("cartCount", cartCount);
		} catch (Exception e) {}
	}
	
	@ResponseBody
	@RequestMapping(value="/submitorder")
	public Result<OrderAO> submitOrderPc(String address, String[] carts, 
			HttpServletRequest request, HttpServletResponse response) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		OrderExtraAO orderExtra = new OrderExtraAO();
		orderExtra.setOrderFrom(OrderAO.ORDER_FROM_PC);

		
		Cookie ck = CookieUtil.getCookie("discount", request);
		String disJson = null;
		if(ck != null) {
			disJson = ck.getValue();
		}
		
		List<String> disList = null;
		if(disJson != null) {
			JsonUtil jsonUtil = JsonUtil.buildNonDefaultMapper();
			JavaType javaType = jsonUtil.constructParametricType(List.class, String.class);
			disList = jsonUtil.fromJson(disJson, javaType);
		}
		
		Result<OrderAO> ret = orderService.submitOrder(address, carts, user, orderExtra, disList);
		if(ret != null && ret.getData() != null) {
			CookieUtil.setCookie("town", ret.getData().getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
		} else {
			UserAddressAO userAddress = addressService.getAddress(address);
			CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
		}
		
		return ret;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/phone/getcookie")
	public Object getCurrentCookie(HttpServletRequest request) {
		Cookie[] cks = request.getCookies();
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		if(cks != null) {
			for(Cookie ck : cks) {
				Map<String, Object> rec = new HashMap<String, Object>();
				rec.put("name", ck.getName());
				rec.put("value", ck.getValue());
				rec.put("path", ck.getPath());
				rec.put("maxage", ck.getMaxAge());
				ret.add(rec);
			}
		}
		
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/resetcookie")
	public Object resetCookie(String json, HttpServletResponse response) {
		if(json == null || "".equals(json.trim())) {
			return null;
		}
		LOG.info(json);
		JsonUtil jsonUtil = JsonUtil.buildNonDefaultMapper();
		JavaType javaType = jsonUtil.constructParametricType(Map.class, String.class, Object.class);
		JavaType tp = jsonUtil.constructParametricType(List.class, javaType);
		List<Map<String, Object>> cks = jsonUtil.fromJson(json, tp);
		if(cks != null) {
			for(Map<String, Object> ck : cks) {
				CookieUtil.setCookie((String)ck.get("name"), (String)ck.get("value"), (String)ck.get("path"), (Integer)ck.get("maxage"), response);
			}
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/submitorder")
	public Result<OrderAO> submitOrder(String address, String[] carts, OrderExtraAO orderExtra, 
			HttpServletRequest request, HttpServletResponse response) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		if(orderExtra.getPlatform() != null) {
			if("ios".equals(orderExtra.getPlatform().toLowerCase())) {
				orderExtra.setOrderFrom(OrderAO.ORDER_FROM_IOS);
			} else if("android".equals(orderExtra.getPlatform().toLowerCase())) {
				orderExtra.setOrderFrom(OrderAO.ORDER_FROM_ANDROID);
			} else {
				orderExtra.setOrderFrom(OrderAO.ORDER_FROM_PHONE);
			}
		} else {
			orderExtra.setOrderFrom(OrderAO.ORDER_FROM_PHONE);
		}
		
		Cookie ck = CookieUtil.getCookie("discount", request);
		String disJson = null;
		if(ck != null) {
			disJson = ck.getValue();
		}
		
		List<String> disList = null;
		if(disJson != null) {
			JsonUtil jsonUtil = JsonUtil.buildNonDefaultMapper();
			JavaType javaType = jsonUtil.constructParametricType(List.class, String.class);
			disList = jsonUtil.fromJson(disJson, javaType);
		}
		
		Result<OrderAO> ret = orderService.submitOrder(address, carts, user, orderExtra, disList);
		
		if(ret != null && ret.getData() != null) {
			CookieUtil.setCookie("town", ret.getData().getTownId(), "/", CookieUtil.MAXAGE_EVER, response);

		    String deliveryTime = "12:00 - 13:00";
		    if(ret.getData().getTownId() != null && !ret.getData().getTownId().isEmpty()) {
					RegionTimeAO regionTimeAO = addressService.getRegionTime(ret.getData().getTownId());
					if(regionTimeAO != null && regionTimeAO.getDeliveryTime() != null) {
						deliveryTime = regionTimeAO.getDeliveryTime();
					}
		    }
		        
		    String orderTip = "我们将在今天" + deliveryTime + "为您送达，请耐心等待.";
		    ret.getData().setOrderTip(orderTip);
		} else {
			UserAddressAO userAddress = addressService.getAddress(address);
			CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
		}
		
		
		
		
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/showorder")
	public OrderAO getOrder(String id, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		OrderAO order = orderService.getOrder(user.getId(), id);
		if(order == null) {
			return null;
		}
		
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
	    return order;
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/cancel")
	public Object cancel(String id, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		return orderService.cancel(user.getId(), id);
	}
	
	@ResponseBody
	@RequestMapping(value="/cancel")
	public Object cancelPc(String id, HttpServletRequest request) {
		return cancel(id, request);
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/comment")
	public Object comment(String[] orderDetail, String[] comment, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		return commentService.comment(user, orderDetail, comment);
	}
	
	@ResponseBody
	@RequestMapping(value="/comment")
	public Object commentPc(String[] orderDetail, String[] comment, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		return commentService.comment(user, orderDetail, comment);
	}
	
	
	@RequestMapping(value="/orderlist/{status}")
	public ModelAndView getOrderListPc(Integer pageNo, @PathVariable String status, HttpServletRequest request, Model model) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		if(pageNo == null) {
			pageNo = 1;
		}
		
		if(pageNo == 1) {
			setCurrentUser(model, request);
		}
		Page page = new Page(pageNo, 5);
		int count = orderService.countOrder(user.getId(), OrderAO.STATUS_ORDERED);
		List<OrderAO> orderList = orderService.getOrderList(user.getId(), status, page);
		if(orderList != null && orderList.size() > 5) {
			orderList.remove(5);
			model.addAttribute("haveMore", true);
		} else {
			model.addAttribute("haveMore", false);
		}
		model.addAttribute("unpaid", count);
		model.addAttribute("dataList", orderList);
		model.addAttribute("status", status);
		model.addAttribute("pageNo", ++pageNo);
		if(pageNo > 2) {
			return MVCViewName.ORDER_ORDERITEM.view();
		}
		return MVCViewName.ORDER_ORDERLIST.view();
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/orderlist")
	public Object getOrderList(Integer pageNo, String status, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		Map<String, Object> ret = new HashMap<>();
		
		if(pageNo == null) {
			pageNo = 1;
		}
		Page page = new Page(pageNo, 5);
		int unpaidCount = orderService.countOrder(user.getId(), OrderAO.STATUS_ORDERED);
		List<OrderAO> orderList = orderService.getOrderList(user.getId(), status, page);
		if(orderList != null && orderList.size() > 5) {
			orderList.remove(5);
			ret.put("haveMore", true);
		} else {
			ret.put("haveMore", false);
		}
		ret.put("unpaidCount", unpaidCount);
		ret.put("orderList", orderList);
		
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/alipay-call-back")
	public Object alipayCallBack(HttpServletRequest request) {
		LOG.info("==========wap支付宝回调接口===========");
		Map<String, String> verifyRs = verifyWapPay(request);
		if(verifyRs != null){//验证成功
			//修改订单状态
			OrderAO order=new OrderAO();
			order.setOrderNo(verifyRs.get("outTradeNo"));
			order.setStatus(OrderAO.STATUS_PAID);
			order.setToPay(new BigDecimal(0));
			order.setPaymentTime(new Date());
			order.setCommentStatus(OrderAO.COMMENT_STATUS_WAITFOR);
			order.setTradeNo(verifyRs.get("tradeNo"));
			String strTotalFee = verifyRs.get("totalFee");
			BigDecimal payAmount = null;
			if(strTotalFee != null && !strTotalFee.isEmpty()) {
				payAmount = new BigDecimal(strTotalFee);
			}
			orderService.payOrder(order, payAmount);
			LOG.info("更新订单状态成功！订单号："+verifyRs.get("outTradeNo"));
		}else{
			LOG.error("验证失败");
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/payreturn")
	public Object payreturn(Integer pageNo, String status, HttpServletRequest request, HttpServletResponse response) {
		return "pay return";
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/paybreak")
	public Object paybreak(Integer pageNo, String status, HttpServletRequest request, HttpServletResponse response) {
		return "pay break";
	}
	
	@ResponseBody
	@RequestMapping(value="/nofilter/alipay-call-back")
	public Object alipayCallBackPc(HttpServletRequest request) {
		LOG.info("==========网站支付宝异步通知回调接口===========");
		Map<String, String> verifyRs = verifyWebPay(request);
		if(verifyRs != null){//验证成功
				//修改订单状态
				OrderAO order=new OrderAO();
				order.setOrderNo(verifyRs.get("outTradeNo"));
				order.setStatus(OrderAO.STATUS_PAID);
				order.setPaymentTime(new Date());
				order.setToPay(new BigDecimal(0));
				order.setCommentStatus(OrderAO.COMMENT_STATUS_WAITFOR);
				order.setTradeNo(verifyRs.get("tradeNo"));
				String strTotalFee = verifyRs.get("totalFee");
				BigDecimal payAmount = null;
				if(strTotalFee != null && !strTotalFee.isEmpty()) {
					payAmount = new BigDecimal(strTotalFee);
				}
				orderService.payOrder(order, payAmount);
				LOG.info("更新订单状态成功！订单号："+verifyRs.get("outTradeNo"));
		}else{
			LOG.error("异步通知：验证失败");
		}
		//返回支付宝异步通知接收成功的标志语
		return "success";
	}
	
	@RequestMapping(value="/nofilter/payreturn")
	public void payreturnPc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.info("=========网站支付宝同步通知回调接口===========");
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		LOG.info("同步参数params: " + params);
		
		//交易状态
		String trade_status = request.getParameter("trade_status");
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		if(verify_result){//验证成功
			LOG.info("同步通知：验证成功<br />");
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				response.sendRedirect(host +"/pay?orderNo=" + request.getParameter("out_trade_no"));
				return;
			}
		}else{
			LOG.error("同步通知：验证失败<br />");
			response.sendRedirect(host +"/pay?orderNo="  + request.getParameter("out_trade_no"));
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/recharge-alipay-call-back")
	public Object rechargeAlipayCallBack(HttpServletRequest request) {
		LOG.info("==========wap账号充值支付宝回调接口===========");
		Map<String, String> verifyRs = verifyWapPay(request);
		if(verifyRs != null){//验证成功
			//修改充值记录状态
			rechargeService.recharge(verifyRs.get("outTradeNo"));
			LOG.info("更新充值记录状态成功！充值记录id："+verifyRs.get("outTradeNo"));
		}else{
			LOG.error("验证失败");
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/recharge-payreturn")
	public Object rechargePayreturn(Integer pageNo, String status, HttpServletRequest request, HttpServletResponse response) {
		return "recharge pay return";
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/nofilter/recharge-paybreak")
	public Object rechargePaybreak(Integer pageNo, String status, HttpServletRequest request, HttpServletResponse response) {
		return "recharge pay break";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/nofilter/recharge-alipay-call-back")
	public Object rechargeCallBackPc(HttpServletRequest request) {
		LOG.info("==========网站支付宝异步通知回调接口===========");
		Map<String, String> verifyRs = verifyWebPay(request);
		if(verifyRs != null){//验证成功
			//修改充值记录状态
			rechargeService.recharge(verifyRs.get("outTradeNo"));
			LOG.info("更新充值记录状态成功！充值记录id："+verifyRs.get("outTradeNo"));
		}else{
			LOG.error("异步通知：验证失败");
		}
		//返回支付宝异步通知接收成功的标志语
		return "success";
	}
	
	@RequestMapping(value="/nofilter/recharge-payreturn")
	public void rchargePayreturnPc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.info("=========网站支付宝同步通知回调接口===========");
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		LOG.info("同步参数params: " + params);
		
		//交易状态
		String trade_status = request.getParameter("trade_status");
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		if(verify_result){//验证成功
			LOG.info("同步通知：验证成功<br />");
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				response.sendRedirect(host + "/recharge/detaillist");
				return;
			}
		}else{
			LOG.error("同步通知：验证失败<br />");
			response.sendRedirect(host + "/recharge/detaillist");
		}
	}
	
	
	private Map<String, String> verifyWebPay(HttpServletRequest request) {
		Map<String, String> ret = null;
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		LOG.info("异步参数params: " + params);
		//商户订单号
		String outTradeNo = request.getParameter("out_trade_no");

		//支付宝交易号
		String tradeNo = request.getParameter("trade_no");
		
		//交易状态
		String tradeStatus = request.getParameter("trade_status");
		
		//支付金额
		String totalFee = request.getParameter("total_fee");

		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if(verify_result){//验证成功
			if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
				ret = new HashMap<String, String>();
				ret.put("outTradeNo",outTradeNo);
				ret.put("tradeNo",tradeNo);
				ret.put("totalFee",totalFee);
			}
		}else{
			LOG.error("异步通知：验证失败");
		}
		return ret;
	}
	
	private Map<String, String> verifyWapPay(HttpServletRequest request) {
		Map<String, String> ret = null;
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		LOG.info("获取支付宝POST过来反馈信息:"+requestParams.toString());
		
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//解密（如果是RSA签名需要解密，如果是MD5签名则下面一行清注释掉）
		//支付宝交易号
		try {
			//RSA签名解密
			if(AlipayConfig.wap_sign_type.equals("0001")) {
				params = AlipayNotify.decrypt(params);
			}
			//XML解析notify_data数据
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			//商户订单号
			//商户订单号
			String outTradeNo = doc_notify_data.selectSingleNode( "//notify/out_trade_no" ).getText();
			//支付宝交易号
			String tradeNo = doc_notify_data.selectSingleNode( "//notify/trade_no" ).getText();
			//交易金额
			String totalFee = doc_notify_data.selectSingleNode( "//notify/total_fee" ).getText();
			//计算得出通知验证结果
			boolean verifyResult = AlipayNotify.verifyWapNotify(params);
			LOG.info("验证结果："+verifyResult);
			if(verifyResult){//验证成功
				ret = new HashMap<String, String>();
				ret.put("outTradeNo",outTradeNo);
				ret.put("tradeNo",tradeNo);
				ret.put("totalFee",totalFee);
			}else{
				LOG.error("验证失败");
			}
			
		} catch (Exception e) {
			LOG.error(e);
			ret = null;
		}
		
		return ret;
	}
}

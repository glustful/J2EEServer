package com.dunbian.jujiabao.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderExtraAO;
import com.dunbian.jujiabao.appobj.extend.SeckillAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;

public interface IOrderService {

	Result<OrderAO> submitOrder(String addressId, String[] cartList, UserAO user, OrderExtraAO orderExtra, List<String> discountList);
	
	List<OrderAO> getOrderList(String userId, String status, Page page);
	
	OrderAO getOrderByOrderNo(String orderNo);
	
	OrderAO getOrderByOrderNoWithDetail(String orderNo);
	
	int countOrder(String userId, String status);
	
	/**
	 * 支付订单，支付宝回调的时候调用该方法
	 * @param orderAO 要更改的订单信息
	 * @param payAmount 支付宝付掉的金额，用于校验
	 * @return
	 */
	boolean payOrder(OrderAO orderAO, BigDecimal payAmount);
	
	OrderAO getOrder(String userId, String id);
	
	Result<Boolean> cancel(String userId, String id);

	Result<Boolean> deal4AppleTest(OrderAO order);
	
	Result<Boolean> validateAddress(UserAddressAO address);
	
	Result<OrderAO> submitOrder4Seckill(UserAO user, FoodSetAO foodSet, SeckillAO sec, UserAddressAO userAddress, OrderExtraAO orderExtra);
}

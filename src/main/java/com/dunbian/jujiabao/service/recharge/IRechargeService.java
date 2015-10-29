package com.dunbian.jujiabao.service.recharge;

import java.math.BigDecimal;
import java.util.List;

import com.dunbian.jujiabao.appobj.extend.AccountDetailAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.RechargeRecordAO;
import com.dunbian.jujiabao.appobj.extend.RechargeSetAO;
import com.dunbian.jujiabao.appobj.extend.UserWalletAO;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;

public interface IRechargeService {

	/**
	 * 充值回调
	 * @param rechargeId 充值记录表id
	 * @return  成功与否
	 */
	Result<Boolean> recharge(String rechargeId);
	
	/**
	 * 获取充值套餐列表
	 * @return 充值套餐列表
	 */
	List<RechargeSetAO> getRechargeSetList();
	
	/**
	 * 生成充值记录
	 * @param setId  充值套餐id
	 * @param userId 用户id
	 * @return 充值记录
	 */
	Result<RechargeRecordAO> genRecharge(String setId, String userId);
	
	/**
	 * 订单支付前判断是否可以使用钱包支付
	 * @param userId 用户id
	 * @param orderId 订单id
	 * @return 订单对象
	 */
	Result<OrderAO> prepare4Pay(String userId, String orderId);
	
	/**
	 * 钱包支付
	 * @param userId 用户id
	 * @param orderId 订单id
	 * @param payPass 支付密码
	 * @param walletMoney 钱包支付金额
	 * @return 订单对象
	 */
	Result<OrderAO> walletPay(String userId, String orderId, String payPass, BigDecimal walletMoney);
	
	/**
	 * 获取充值记录
	 * @param recordId 充值记录id
	 * @return 充值记录
	 */
	RechargeRecordAO getRechargeById(String recordId);
	
	/**
	 * 付款完成的回调
	 * @param order 订单对象
	 * @return 成功与否
	 */
	Result<Boolean> callback4Pay(OrderAO order);
	
	/**
	 * 用户钱包是否已经存在
	 * @param userId 用户id
	 * @return 存在与否
	 */
	Result<Boolean> isExistsWallet(String userId);
	
	/**
	 * 设置支付密码并生成用户钱包
	 * @param userId 用户id
	 * @param pass 支付密码
	 * @return 成功与否
	 */
	Result<Boolean> setPass(String userId, String pass);
	
	/**
	 * 找回密码
	 * @param userId 用户id
	 * @param pass 新密码
	 * @return 成功与否
	 */
	Result<Boolean> resetPass(String userId, String pass);
	
	/**
     * 修改用户支付密码
     * @param  walletId 钱包id
     * @param oldPass
     * @param newPass
     * @return
     */
    Result<Boolean> resetPass(String  walletId, String oldPass, String newPass);
    
	/**
	 * 获取电子账单信息
	 * @param userId 用户id
	 * @param page 分页信息
	 * @return 交易流水列表
	 */
	List<AccountDetailAO> getDetailList(String userId, Page page);
	
	/**
	 * 获取用户钱包
	 * @param userId 用户id
	 * @return 用户钱包对象
	 */
	UserWalletAO getUserWallet(String userId);
	
	/**
	 * 取消订单
	 * @param userId 用户id
	 * @param orderId 订单id
	 */
	void cancelOrder(String userId, String orderId);
}

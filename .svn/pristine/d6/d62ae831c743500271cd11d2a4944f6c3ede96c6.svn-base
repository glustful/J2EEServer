package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dunbian.jujiabao.appobj.generator.Order;

public class OrderAO extends Order implements Serializable {

	private static final long serialVersionUID = 9177700390961961371L;

	public static final String STATUS_DELETED = "00";
	
	public static final String STATUS_ORDERED = "10";
	
	public static final String STATUS_PAID = "20";
	
	public static final String STATUS_PACKED = "30";
	
	public static final String STATUS_RECEIVED = "40";
	
	public static final String STATUS_CANCELED = "50";
	
	public static final String STATUS_REFUND = "60";
	
	public static final String STATUS_SEARCH_PAID = "70";

	public static final String ORDER_FROM_PC = "00";
	
	public static final String ORDER_FROM_ANDROID = "10";
	
	public static final String ORDER_FROM_IOS = "20";
	
	public static final String ORDER_FROM_PHONE = "30";

	public static final String ORDER_FROM_WEIXIN = "40";
	
	public static final String SET_TYPE_LUNCH = "10";
	
	public static final String SET_TYPE_DINNER = "10";
	
	public static final String COMMENT_STATUS_WAITFOR = "00";
	
	public static final String COMMENT_STATUS_COMMENTED = "10";
	
	//钱包无钱
	public static final String WALLET_PAY_TYPE_NONE = "0";
	
	//部分支付
	public static final String WALLET_PAY_TYPE_PART = "1";
	
	//钱包余额全部支付
	public static final String WALLET_PAY_TYPE_ALL = "2";
		
	private List<OrderDetailAO> detailList;
	
	private UserAddressAO addressInfo;

	public UserAddressAO getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(UserAddressAO addressInfo) {
		this.addressInfo = addressInfo;
	}

	public List<OrderDetailAO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetailAO> detailList) {
		this.detailList = detailList;
	}
	
	public void addDetail(OrderDetailAO detail) {
		if(detailList == null) {
			detailList = new ArrayList<>();
		}
		
		detailList.add(detail);
	}
	
	public String getStatusName() {
		String st = getStatus();
		if(st == null) {
			return "未知状态";
		}
		
		switch (st) {
		case STATUS_CANCELED:
			return "订单已取消";
		case STATUS_DELETED:
			return "订单已删除";
		case STATUS_ORDERED:
			return "已下单，未付款";
		case STATUS_PACKED:
			return "已打包装车";
		case STATUS_RECEIVED:
			return "订单已完成";
		case STATUS_REFUND:
			return "订单已退款";
		case STATUS_PAID:
			return "已付款，待发货";
		default:
			break;
		}
		
		return "未知状态";
	}
	
	private String orderTip;

	public String getOrderTip() {
		return orderTip;
	}

	public void setOrderTip(String orderTip) {
		this.orderTip = orderTip;
	}
	
	private String walletPayType;
	
	public String getWalletPayType() {
		return walletPayType;
	}

	public void setWalletPayType(String walletPayType) {
		this.walletPayType = walletPayType;
	}

	public BigDecimal walletMoney;

	public BigDecimal getWalletMoney() {
		return walletMoney;
	}

	public void setWalletMoney(BigDecimal walletMoney) {
		this.walletMoney = walletMoney;
	}
	
	private UserWalletAO wallet;

	public UserWalletAO getWallet() {
		return wallet;
	}

	public void setWallet(UserWalletAO wallet) {
		this.wallet = wallet;
	}
	
}

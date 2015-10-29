package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.AccountDetail;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;

public class AccountDetailAO extends AccountDetail implements Serializable, Cloneable {

	private static final long serialVersionUID = 2753886554158964486L;

	public static final String BALANCE_TYPE_AMOUNT = "00";
	
	public static final String BALANCE_TYPE_GIFT = "10";
	
	public static final String CREDIT_FLAG_SPENDING = "0";
	
	public static final String CREDIT_FLAG_INCOME = "1";
	
	public static final String VALID_TYPE_OFF_SHEETS = "0";
	
	public static final String VALID_TYPE_SHEETS = "1";
	
	public static final String TRADE_TYPE_RECHARGE = "000";
	
	public static final String TRADE_TYPE_COMSUME = "100";
	
	public static final String TRADE_TYPE_ALIPAY = "200";
	
	public static final String TRADE_TYPE_BACKEND = "300";
	
	public static final String HEAD_INTERNAL = "000";
	
	public static final String HEAD_USER = "100";
	
	public String getTradeTimeStr() {
		if(super.getTradeTime() == null) {
			return null;
		} else {
			return DateTimeUtil.format(this.getTradeTime(), DateTimeUtil.FORMAT_YMD_HMS);
		}
    }
	
	public String getBalanceTypeName() {
		String ret = null;
		if(this.getBalanceType() == null || this.getBalanceType().isEmpty()) {
			return null;
		}
		switch (this.getBalanceType()) {
		case BALANCE_TYPE_AMOUNT:
			ret = "充值";
			break;
		case BALANCE_TYPE_GIFT:
			ret = "红包";
			break;

		default:
			break;
		}
		return ret;
	}
	
	public String getTradeTypeName() {
		if(VALID_TYPE_OFF_SHEETS.equals(getValidType())) {
			if(CREDIT_FLAG_INCOME.equals(getCreditFlag())) {
				return "解冻";
			} else {
				return "冻结";
			}
		} else {
			String tradeType = getTradeType();
			if(tradeType == null) {
				return "未知";
			}
			
			switch (tradeType) {
			case TRADE_TYPE_ALIPAY:
				return "支付宝消费";
			case TRADE_TYPE_BACKEND:
				return "系统调整";
			case TRADE_TYPE_COMSUME:
				return "余额消费";
			case TRADE_TYPE_RECHARGE:
				return "充值";
			default:
				return "未知";
			}
		}
	}
	
	
	public String getCreditFlagName() {
		String ret = null;
		if(this.getCreditFlag() == null || this.getCreditFlag().isEmpty()) {
			return null;
		}
		switch (this.getCreditFlag()) {
		case CREDIT_FLAG_INCOME:
			ret = "收入";
			break;
		case CREDIT_FLAG_SPENDING:
			ret = "支出";
			break;
		default:
			break;
		}
		return ret;
	}
	
	public AccountDetailAO clone() {
		try {
			return (AccountDetailAO)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}

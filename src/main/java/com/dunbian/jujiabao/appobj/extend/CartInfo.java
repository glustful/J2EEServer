package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6914177157651759325L;

	private Integer cnt;
	
	private BigDecimal amount;

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}

package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dunbian.jujiabao.appobj.generator.UserWallet;

@JsonSerialize(include=Inclusion.NON_NULL)
public class UserWalletAO extends UserWallet implements Serializable {

	private static final long serialVersionUID = 6697007594790422112L;

	public BigDecimal getValidAmount() {
		if(getGiftBalance() != null) {
			return getBalance().add(getGiftBalance());
		} else {
			return getBalance();
		}
	}
	
	public BigDecimal getFrozenAmount() {
		BigDecimal amt = new BigDecimal(0);
		
		if(getFrozenBalance() != null) {
			amt = amt.add(getFrozenBalance());
		}
		
		if(getFrozenGift() != null) {
			amt = amt.add(getFrozenGift());
		}
		
		return amt;
	}
}

package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

public class SMSValidationError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1907369806165705052L;

	public static final int MAX_COUNT = 5;
	
	public static final long CLEAN_TIME = 3600000L;
	
	public static final String SMS_VALIDATAION_ERROR = "SMS_VALIDATAION_ERROR";
	
	private long keepTime;
	
	private long lastTime;
	
	private int count;
	
	private String mobile;

	public long getKeepTime() {
		return keepTime;
	}

	public void setKeepTime(long keepTime) {
		this.keepTime = keepTime;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}

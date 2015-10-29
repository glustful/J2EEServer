package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.RegionTime;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;

public class RegionTimeAO extends RegionTime implements Serializable {

	private static final long serialVersionUID = 3876854970729597566L;

	public String getStartTimeStr() {
		if(this.getStartTime() == null) {
			return null;
		} else {
			return DateTimeUtil.format(this.getStartTime(), DateTimeUtil.FORMAT_HM);
		}
	}
	
	public String getEndTimeStr() {
		if(this.getEndTime() == null) {
			return null;
		} else {
			return DateTimeUtil.format(this.getEndTime(), DateTimeUtil.FORMAT_HM);
		}
	}
}

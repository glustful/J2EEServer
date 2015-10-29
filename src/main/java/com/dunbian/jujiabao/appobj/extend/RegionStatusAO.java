package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.RegionStatus;

public class RegionStatusAO extends RegionStatus implements Serializable {

	private static final long serialVersionUID = -2240112829618457877L;

	//正常
	public static final String STATUS_NORMAL = "10";
	
	//停用
	public static final String STATUS_DISABLE = "00";
}

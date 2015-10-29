package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dunbian.jujiabao.appobj.generator.Seckill;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;
import com.dunbian.jujiabao.service.good.IGoodTypeService;
import com.puddingnet.mvc.servlet.SpringContext;

@JsonSerialize(include=Inclusion.NON_NULL)
public class SeckillAO extends Seckill implements Serializable {

	private static final long serialVersionUID = 6445815887373775445L;

	public static final String STATUS_INVALID = "00";
	
	public static final String STATUS_NOMARL = "10";
	
	/* type字段常量 套餐类型分别表示A餐B餐C餐 
	public static final String TYPE_A = "10";
	public static final String TYPE_B = "20";
	public static final String TYPE_C = "30";*/
	
	private int inventory;

	
	public int getInventory() {
		return inventory;
	}


	public void setInventory(int inventory) {
		this.inventory = inventory;
	}


	public Date getCurrentTime() {
		return new Date();
	}
	
	
	public String getTypeName() {
		String ret = null;
		if(this.getType() == null) {
			return ret;
		}
		
		/*
		switch (this.getType()) {
		case SeckillAO.TYPE_A:
			ret = "A餐";
			break;
		case SeckillAO.TYPE_B:
			ret = "B餐";
			break;
		case SeckillAO.TYPE_C:
			ret = "C餐";
			break;
		default:
			ret = "未知";
			break;
		}*/
		
		IGoodTypeService goodTypeService = SpringContext.getBean(IGoodTypeService.class);
		FoodTypeAO foodType = goodTypeService.getFoodTypeCache(this.getType());
		
		return foodType == null ? "未知" : foodType.getName();
	}
	
	public String getSetTypeStyle() {
		String ret = null;
		if(this.getType() == null) {
			return ret;
		}
//		switch (this.getType()) {
//		case SeckillAO.TYPE_A:
//			ret = "#ff4200";
//			break;
//		case SeckillAO.TYPE_B:
//			ret = "#2baa3f";
//			break;
//		case SeckillAO.TYPE_C:
//			ret = "#1b9efc";
//			break;
//		default:
//			ret = "";
//			break;
//		}
		
		
		return "#ff4200";
	}
	
	public Long getStartTimeLong() {
		if(this.getStartTime() == null) {
			return null;
		}
		return this.getStartTime().getTime();
	}
	
	public Long getEndTimeLong() {
		if(this.getEndTime() == null) {
			return null;
		}
		return this.getEndTime().getTime();
	}
	
	public String getStartTimeStr() {
		if(this.getStartTime() != null) {
			return DateTimeUtil.format(this.getStartTime(), DateTimeUtil.FORMAT_HMS);
		}
		return null;
	}

	public String getEndTimeStr() {
		if(this.getEndTime() != null) {
			return DateTimeUtil.format(this.getEndTime(), DateTimeUtil.FORMAT_HMS);
		}
		return null;
	}
}

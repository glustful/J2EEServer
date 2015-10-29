package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.FoodDetail;

public class FoodDetailAO extends FoodDetail implements Serializable {

	private static final long serialVersionUID = 536003624682465346L;


	//删除
	public static final String STATUS_DELETED = "00";
	
	//正常
	public static final String STATUS_NOMAL = "10";
	
	//下架
	public static final String STATUS_OFFLINE = "20";
	
	//类型-素菜
	public static final String TYPE_VEGETABLE = "10";
	//类型-荤菜
	public static final String TYPE_MEAT = "20";
	
	public String getTypeName() {
		String type = getType();
		if(type == null) {
			return "";
		}
		
		switch (type) {
		case TYPE_VEGETABLE:
			return "素菜";
		case TYPE_MEAT:
			return "荤菜";
		default:
			return "";
		}
	}
	
	public String getStatusName() {
		String status = getStatus();
	    if(status == null) {
	    	return "";
	    }
	    
	    switch (status) {
		case STATUS_NOMAL:
			return "上架";
		case STATUS_OFFLINE:
			return "下架";
		case STATUS_DELETED:
			return "删除";
		default:
			return "";
		}
	}
}

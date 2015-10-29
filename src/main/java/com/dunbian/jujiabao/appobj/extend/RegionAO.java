package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.Region;

public class RegionAO extends Region implements Serializable {

	private static final long serialVersionUID = 1417236829918441012L;

	//删除
		public static final String STATUS_DELETED = "00";
		
		//正常
		public static final String STATUS_NORMAL = "10";
		
		//停用
		public static final String STATUS_DISABLE = "20";
		
		//类型（10：城市）
		public static final String TYPE_CITY = "10";
		//类型（20：曲县）
		public static final String TYPE_COUNTRY = "20";
		//类型（30：片区）
		public static final String TYPE_TOWN = "30";
		//类型（ 40：大厦）
		public static final String TYPE_BLOCK = "40";
		//类型（50小区）
		public static final String TYPE_COMMUNITY = "50";
		//类型（60座）
		public static final String TYPE_SUITE = "60";
		//类型（70栋）
		public static final String TYPE_BUILDING = "70";
		//类型（80单元）
		public static final String TYPE_CELL = "80";
		
		public static final String CITY_ID_KUNMING = "23877403403092068";
		
		public String getTypeName() {
			String type = getType();
			if(type == null) {
				return "";
			}
			switch (type) {
			case TYPE_CITY:
				return "城市";
			case TYPE_COUNTRY:
				return "区县";
			case TYPE_TOWN:
				return "片区";
			case TYPE_BLOCK:
				return "大厦";
			case TYPE_COMMUNITY:
				return "小区";
			case TYPE_SUITE:
				return "楼座";
			case TYPE_BUILDING:
				return "栋";
			case TYPE_CELL:
				return "单元";
			default:
				return "未知";
			}
		}
		
		public String getStatusName() {
			String status = getStatus();
		    if(status == null) {
		    	return "";
		    }
		    
		    switch (status) {
			case STATUS_NORMAL:
				return "正常";
			case STATUS_DISABLE:
				return "停用";
			case STATUS_DELETED:
				return "删除";
			default:
				return "";
			}
		}
}

package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.OrderDetail;
import com.dunbian.jujiabao.service.good.IGoodTypeService;
import com.puddingnet.mvc.servlet.SpringContext;

public class OrderDetailAO extends OrderDetail implements Serializable {

	private static final long serialVersionUID = -3557382163745174651L;

	public String getFoodSetId() {
		return super.getSetId();
	}
	
	public String getTypeName() {
		String type = getType();
		if(type == null) {
			return "未知";
		}
//		switch (type) {
//		case FoodWeekAO.TYPE_A:
//			return "A餐";
//		case FoodWeekAO.TYPE_B:
//			return "B餐";
//		case FoodWeekAO.TYPE_C:
//			return "C餐";
//		default:
//			return "A餐";
//		}
		
		IGoodTypeService goodTypeService = SpringContext.getBean(IGoodTypeService.class);
		FoodTypeAO foodType = goodTypeService.getFoodTypeCache(this.getType());
		
		return foodType == null ? "未知" : foodType.getName();
	}
}

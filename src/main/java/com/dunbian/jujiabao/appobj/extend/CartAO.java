package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dunbian.jujiabao.appobj.generator.Cart;
import com.dunbian.jujiabao.service.good.IGoodTypeService;
import com.puddingnet.mvc.servlet.SpringContext;

public class CartAO extends Cart implements Serializable {

	private static final long serialVersionUID = 3263333658853784822L;

	public static final int CART_MAX_COUNT = 500;
	
	private FoodSetAO foodSet;

	public FoodSetAO getFoodSet() {
		return foodSet;
	}

	public void setFoodSet(FoodSetAO foodSet) {
		this.foodSet = foodSet;
	}
	
	public String getTypeName() {
		String type = getType();
//		if(type == null) {
//			return "A餐";
//		}
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
		FoodTypeAO foodType = goodTypeService.getFoodTypeCache(type);
		
		return foodType == null ? "未知" : foodType.getName();
	}
	
	public BigDecimal getCartPrice() {
		if(foodSet != null && getCount() != null) {
			return foodSet.getPrice().multiply(new BigDecimal(getCount()));
		}
		
		return new BigDecimal(0);
	}
	
}

package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.util.List;

import com.dunbian.jujiabao.appobj.generator.FoodSet;
import com.dunbian.jujiabao.service.good.IGoodTypeService;
import com.puddingnet.mvc.servlet.SpringContext;

public class FoodSetAO extends FoodSet implements Serializable {

	private static final long serialVersionUID = -7377134225659544373L;

	//删除
	public static final String STATUS_DELETED = "00";
	
	//正常
	public static final String STATUS_NOMAL = "10";
	
	//下架
	public static final String STATUS_OFFLINE = "20";
	
	//时间：今天
	public static final String TYPE_TODAY = "10";
	
	//时间：明天
	public static final String TYPE_TOMORROW = "20";
	
	private List<FoodDetailAO> foodDetailList;
	
	//套餐类型（A餐，B餐）用于查询今天或者明天套餐列表时
	private String setType;
	
	private String remainMsg;
	
	private boolean sellOut;
	//用于查询今天或者明天套餐列表时，存储对应的FoodWeek对象的id
	private String weekId;
	
	
	//轮番图片的路径
	private List<String> multiImgPaths;
		
	public List<FoodDetailAO> getFoodDetailList() {
		return foodDetailList;
	}


	public void setFoodDetailList(List<FoodDetailAO> foodDetailList) {
		this.foodDetailList = foodDetailList;
	}
	

	public String getSetType() {
		return setType;
	}
	
	public Integer getSaleCount() {
		if(super.getSaleCount() == null) {
			return 0;
		} else {
			return super.getSaleCount();
		}
	}


	public String getSaleMsg() {
		return "已订购" + super.getSaleCount() + "份";
	}
	
	public String getRemainMsg() {
		return remainMsg;
	}


	public void setRemainMsg(String remainMsg) {
		this.remainMsg = remainMsg;
	}

	
	public boolean isSellOut() {
		return sellOut;
	}


	public void setSellOut(boolean sellOut) {
		this.sellOut = sellOut;
	}


	public String getSetTypeName() {
		String ret = null;
		if(this.setType == null) {
			return ret;
		}
//		switch (this.setType) {
//		case FoodWeekAO.TYPE_A:
//			ret = "A餐";
//			break;
//		case FoodWeekAO.TYPE_B:
//			ret = "B餐";
//			break;
//		case FoodWeekAO.TYPE_C:
//			ret = "C餐";
//			break;
//		default:
//			ret = "未知";
//			break;
//		}
//		return ret;
		IGoodTypeService goodTypeService = SpringContext.getBean("goodTypeService", IGoodTypeService.class);
		FoodTypeAO foodType = goodTypeService.getFoodTypeCache(this.setType);
		
		return foodType == null ? "未知" : foodType.getName();
	}
	
	public String getSetTypeColor() {
		String ret = null;
		if(this.setType == null) {
			return ret;
		}
//		switch (this.setType) {
//		case FoodWeekAO.TYPE_A:
//			ret = "red";
//			break;
//		case FoodWeekAO.TYPE_B:
//			ret = "green";
//			break;
//		case FoodWeekAO.TYPE_C:
//			ret = "blue";
//			break;
//		default:
//			ret = "";
//			break;
//		}
//		return ret;
		return "red";
	}
	
	public String getSetTypeStyle() {
		String ret = null;
		if(this.setType == null) {
			return ret;
		}
//		switch (this.setType) {
//		case FoodWeekAO.TYPE_A:
//			ret = "#ff4200";
//			break;
//		case FoodWeekAO.TYPE_B:
//			ret = "#2baa3f";
//			break;
//		case FoodWeekAO.TYPE_C:
//			ret = "#1b9efc";
//			break;
//		default:
//			ret = "";
//			break;
//		}
		
//		return ret;
		return "#ff4200";
	}
	
	public void setSetType(String setType) {
		this.setType = setType;
	}


	public String getWeekId() {
		return weekId;
	}


	public void setWeekId(String weekId) {
		this.weekId = weekId;
	}


	public List<String> getMultiImgPaths() {
		return multiImgPaths;
	}


	public void setMultiImgPaths(List<String> multiImgPaths) {
		this.multiImgPaths = multiImgPaths;
	}


	public String getFoodDetailNames() {
		if(foodDetailList == null || foodDetailList.isEmpty()) {
			return null;
		} else {
			StringBuilder tmp = new StringBuilder();
			for(FoodDetailAO tmpFoodDetailAO : foodDetailList) {
				tmp.append(tmpFoodDetailAO.getName()).append("、");
			}
			return tmp.substring(0,tmp.length()-1);
		}
	}
	
}

package com.dunbian.jujiabao.food;

import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;
import com.dunbian.jujiabao.framework.obj.DataList;

public interface IFoodService {
 
	/**
	 * 根据id查询套餐详情对象
	 * @param id
	 * @return
	 */
	FoodSetAO getFoodSet(String id);
	
	/**
	 * 根据weekId查询套餐详情对象
	 * @param weekId FoodWeek对象的id
	 * @return
	 */
	FoodSetAO getFoodSetByWeekId(String weekId);
	
	/**
	 * 根据weekId查询FoodWeekAO对象
	 * @param weekId FoodWeek对象的id
	 * @return
	 */
	FoodWeekAO getFoodWeekByWeekId(String weekId);
	
	/**
	 * 根据时间类型（01：今天、02：明天）查询列表
	 * @param time
	 * @return
	 */
	DataList<FoodSetAO> getFoodSetList(String time);
}

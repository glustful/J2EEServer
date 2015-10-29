package com.dunbian.jujiabao.db.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dunbian.jujiabao.appobj.extend.FoodSetAO;

public interface FoodSetCustomMapper {
	
	/**
	 * 根据 weekDay获得某天套餐对象列表，并包含主图路径
	 * @param weekDay 周几
	 * @return 当天的套餐列表
	 */
	List<FoodSetAO> getFoodSets(@Param("weekDay") String weekDay);
	
	/**
	 * 根据weekId获得套餐对象
	 * @param weekId FoodWeek对象的id
	 * @return
	 */
	List<FoodSetAO> getFoodSetByWeekId(@Param("weekId") String weekId);
	
	Integer updateCommentCount(@Param("sm") int sm, @Param("id") String id);
	
	Integer updateSaleCount(@Param("sm") int sm, @Param("id") String id);
}

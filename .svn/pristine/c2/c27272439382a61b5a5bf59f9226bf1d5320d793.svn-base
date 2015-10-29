package com.dunbian.jujiabao.db.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;

public interface FoodWeekCustomMapper {

	/**
	 *  查询所有FoodWeek对象，并关联查询价格信息
	 * @return
	 */
	List<FoodWeekAO> getAllFoodWeeks();
	
	Integer updateSaleCount(@Param("sm") int sm, @Param("setid") String setid, @Param("day") String day);
}

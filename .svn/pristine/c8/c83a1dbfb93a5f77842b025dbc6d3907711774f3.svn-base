package com.dunbian.jujiabao.db.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dunbian.jujiabao.appobj.extend.FoodDetailAO;

public interface FoodDetailCustomMapper {
	
    /**
     * 根据套餐id查询套餐包括的所有单品（FoodDetailAO集合）
     * @param setId 套餐ID
     * @return
     */
    List<FoodDetailAO> selectBySetId(@Param("setId") String setId);
}

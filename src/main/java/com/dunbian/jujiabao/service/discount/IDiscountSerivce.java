package com.dunbian.jujiabao.service.discount;

import java.util.List;

import com.dunbian.jujiabao.appobj.extend.DiscountAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;

public interface IDiscountSerivce {

	List<DiscountAO> getDiscountInfo(String town, UserAO user);
}

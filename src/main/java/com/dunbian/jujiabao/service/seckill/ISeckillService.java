package com.dunbian.jujiabao.service.seckill;

import java.util.List;

import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderExtraAO;
import com.dunbian.jujiabao.appobj.extend.SeckillAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.obj.Result;

public interface ISeckillService {

	List<SeckillAO> getSeckillList(String town);
	
	SeckillAO getSeckill(String id);
	
	Result<OrderAO> kill(String inventoryType, UserAO user, SeckillAO sec, OrderExtraAO orderExtra);
}

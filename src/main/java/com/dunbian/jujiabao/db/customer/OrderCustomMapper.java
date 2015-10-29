package com.dunbian.jujiabao.db.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.framework.obj.Page;

public interface OrderCustomMapper {

	List<OrderAO> getOrderList(@Param("userId") String userId, @Param("status") String status, @Param("page") Page page);
	
    
    OrderAO getOrderByOrderNo( @Param("orderNo") String orderNo);
    
    OrderAO getOrderByOrderId( @Param("orderId") String orderId);
}

package com.dunbian.jujiabao.service.address;

import java.util.List;

import com.dunbian.jujiabao.appobj.extend.RegionAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.obj.Result;

public interface IAddressService {

	UserAddressAO getDefaultAddress(String userId);
	
	List<UserAddressAO> getAddressList(String userId);
	
	Result<Boolean> setDefaultAddress(String userId, String addressId);
	
	List<RegionAO> getRegionByParent(String parent);
	
	RegionAO getRegionById(String id);
	
	Result<Boolean> save(UserAddressAO address);
	
	void remove(String userId, String addressId);
	
	 /**
     * 查询一个片区的订单时间限制
     * @param townId
     * @return
     */
    RegionTimeAO getRegionTime(String townId);
    
    UserAddressAO getAddress(String id);
}

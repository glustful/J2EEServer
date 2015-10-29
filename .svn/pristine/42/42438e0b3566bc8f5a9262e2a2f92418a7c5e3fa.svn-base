package com.dunbian.jujiabao.service.address;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.RegionAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.appobj.generator.RegionExample;
import com.dunbian.jujiabao.appobj.generator.RegionTimeExample;
import com.dunbian.jujiabao.appobj.generator.UserAddressExample;
import com.dunbian.jujiabao.db.generator.RegionMapper;
import com.dunbian.jujiabao.db.generator.RegionTimeMapper;
import com.dunbian.jujiabao.db.generator.UserAddressMapper;
import com.dunbian.jujiabao.framework.obj.Result;

@Service
public class AddressService implements IAddressService {

	@Resource
	private UserAddressMapper userAddressMapper;
	
	@Resource
	private RegionMapper regionMapper;
	
	@Resource
	RegionTimeMapper regionTimeMapper;
	
	@Override
	@Transactional(readOnly=true)
	public UserAddressAO getDefaultAddress(String userId) {
		UserAddressExample uae = new UserAddressExample();
		uae.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(UserAddressAO.STATUS_DEFAULT);
		List<UserAddressAO> addressList = userAddressMapper.selectByExample(uae);
		if(addressList == null || addressList.isEmpty()) {
			return null;
		}
		
		return addressList.get(0);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserAddressAO> getAddressList(String userId) {
		UserAddressExample uae = new UserAddressExample();
		uae.createCriteria().andUserIdEqualTo(userId);
		uae.setOrderByClause("status desc, create_time desc");
		
		return userAddressMapper.selectByExample(uae);
	}

	@Override
	@Transactional(readOnly=true)
	public List<RegionAO> getRegionByParent(String parent) {
		RegionExample re = new RegionExample();
		if(parent == null) {
//			re.createCriteria().andParentIdIsNull().andStatusEqualTo(RegionAO.STATUS_NORMAL);
			re.createCriteria().andParentIdIsNull().andStatusNotEqualTo(RegionAO.STATUS_DELETED);
		} else {
//			re.createCriteria().andParentIdEqualTo(parent).andStatusEqualTo(RegionAO.STATUS_NORMAL);
			re.createCriteria().andParentIdEqualTo(parent).andStatusNotEqualTo(RegionAO.STATUS_DELETED);
		}
		
		return regionMapper.selectByExample(re);
	}

	private int countAddress(String userId) {
		UserAddressExample uae = new UserAddressExample();
		uae.createCriteria().andUserIdEqualTo(userId);
		
		return userAddressMapper.countByExample(uae);
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> save(UserAddressAO address) {
		address.setCreateTime(new Date());
		address.prepared();
		if(address.getStatus() == null) {
			address.setStatus(UserAddressAO.STATUS_NOMARL);
		}
		
		if(address.getId() == null) {
			int ads = countAddress(address.getUserId());
			if(ads == 0) {
				address.setStatus(UserAddressAO.STATUS_DEFAULT);
			} else if(ads >= 10) {
				return new Result<>(false, "地址数目达到上限，请删除后重新添加地址");
			}
			userAddressMapper.insert(address);
		} else {
			userAddressMapper.updateByPrimaryKey(address);
		}
		
		if(UserAddressAO.STATUS_DEFAULT.equals(address.getStatus())) {
			setDefaultAddress(address);
		}
		
		Result<Boolean> ret = new Result<Boolean>(true);
		ret.setExtend(address.getId());
		return ret;
	}

	private void setDefaultAddress(UserAddressAO address) {
		UserAddressExample uae = new UserAddressExample();
		uae.createCriteria().andUserIdEqualTo(address.getUserId()).andStatusEqualTo(UserAddressAO.STATUS_DEFAULT);
		UserAddressAO upd = new UserAddressAO();
		upd.setStatus(UserAddressAO.STATUS_NOMARL);
		userAddressMapper.updateByExampleSelective(upd, uae);
		
		address.setStatus(UserAddressAO.STATUS_DEFAULT);
		upd.setId(address.getId());
		upd.setStatus(address.getStatus());
		userAddressMapper.updateByPrimaryKeySelective(upd);
	}
	
	@Override
	@Transactional(readOnly=true)
	public RegionTimeAO getRegionTime(String townId) {
		RegionTimeExample example = new RegionTimeExample();
		example.createCriteria().andRegionIdEqualTo(townId);
		List<RegionTimeAO> dataList = regionTimeMapper.selectByExample(example);
		if(dataList != null && !dataList.isEmpty()) {
			return dataList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void remove(String userId, String addressId) {
		UserAddressExample uae = new UserAddressExample();
		uae.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(addressId);
		
		userAddressMapper.deleteByExample(uae);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> setDefaultAddress(String userId, String addressId) {
		UserAddressAO address = userAddressMapper.selectByPrimaryKey(addressId);
		if(address == null || address.getUserId() == null || !address.getUserId().equals(userId)) {
			return new Result<Boolean>(true);
		}
		setDefaultAddress(address);
		return new Result<Boolean>(true);
	}

	@Override
	@Transactional(readOnly=true)
	public UserAddressAO getAddress(String id) {
		return userAddressMapper.selectByPrimaryKey(id);
	}

	@Override
	public RegionAO getRegionById(String id) {
		return regionMapper.selectByPrimaryKey(id);
	}
}

package com.dunbian.jujiabao.service.advertisement;

import java.util.Date;
import java.util.List;

import com.dunbian.jujiabao.appobj.extend.AdvertisementAO;

public interface IAdvertisementService {

	List<AdvertisementAO> getAdvertisement(String townId, Date now);
	
	AdvertisementAO getAdvertisement(String id);
}

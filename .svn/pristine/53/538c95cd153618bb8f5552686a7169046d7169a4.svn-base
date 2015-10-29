package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.type.JavaType;

import com.dunbian.jujiabao.appobj.generator.UserAddress;
import com.dunbian.jujiabao.framework.json.JsonUtil;

public class UserAddressAO extends UserAddress implements Serializable {

	private static final long serialVersionUID = 3927681658050119765L;

	public static final String STATUS_NOMARL = "10";
	
	public static final String STATUS_DEFAULT = "20";
	
	public boolean isDefault() {
		return STATUS_DEFAULT.equals(getStatus());
	}
	
	private String regionJson;
	
	public String getRegionJson() {
		return regionJson;
	}

	public void setRegionJson(String regionJson) {
		this.regionJson = regionJson;
	}

	public void prepared() {
		if(regionJson == null) {
			return;
		}
		JsonUtil jsonUtil = JsonUtil.buildNonDefaultMapper();
		JavaType tp = jsonUtil.constructParametricType(List.class, RegionAO.class);
		List<RegionAO> regList = jsonUtil.fromJson(regionJson, tp);
		if(regList == null) {
			return;
		}
		
		for(RegionAO region : regList) {
			if(region.getType() == null) {
				continue;
			}
			
			switch (region.getType()) {
			case RegionAO.TYPE_BLOCK:
				setBlockId(region.getId());
				setBlockName(region.getName());
				break;
			case RegionAO.TYPE_BUILDING:
				setBuildingId(region.getId());
				setBuildingName(region.getName());
				break;
			case RegionAO.TYPE_CELL:
				setCellId(region.getId());
				setCellName(region.getName());
				break;
			case RegionAO.TYPE_CITY:
				setCityId(region.getId());
				setCityName(region.getName());
				break;
			case RegionAO.TYPE_COMMUNITY:
				setBlockId(region.getId());
				setBlockName(region.getName());
				break;
			case RegionAO.TYPE_COUNTRY:
				setCountyId(region.getId());
				setCountyName(region.getName());
				break;
			case RegionAO.TYPE_SUITE:
				setBuildingId(region.getId());
				setBuildingName(region.getName());
				break;
			case RegionAO.TYPE_TOWN:
				setTownId(region.getId());
				setTownName(region.getName());
				break;
			default:break;
			}
		}
	}
	
	private void filterAppend(String str, StringBuilder sb) {
		if(str == null) {
			return;
		}
		sb.append(str);
	}
	
	public String getAddress() {
		StringBuilder sb = new StringBuilder();
		filterAppend(getCityName(), sb);
		filterAppend(getCountyName(), sb);
		filterAppend(getTownName(), sb);
		filterAppend(getBlockName(), sb);
		filterAppend(getBuildingName(), sb);
		filterAppend(getCellName(), sb);
		if(getFloor() != null) {
			sb.append(getFloor()).append("层  ");
		}
		if(this.getManualAddress() != null) {
			sb.append(getManualAddress());
		}
		return sb.toString();
	}
}

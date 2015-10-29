package com.dunbian.jujiabao.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.RegionAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.CookieUtil;

@Controller
@RequestMapping(value="/address")
public class AddressController {

	@Resource
	private IAddressService addressService;
	
	@RequestMapping(value="/list")
	public Object listPc(HttpServletRequest request, Model model) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		
		List<UserAddressAO> lst = addressService.getAddressList(user.getId());
		model.addAttribute("addressList", lst);
		model.addAttribute("regionList", getRegionList(null));
		
		return MVCViewName.INDEX_ADDRESS_LIST.view();
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/list")
	public Object list(HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		
		return addressService.getAddressList(user.getId());
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/setdefault")
	public Object setDefault(String id, HttpServletRequest request, HttpServletResponse response) {
		return setDefaultPc(id, request, response);
	}
	
	@ResponseBody
	@RequestMapping(value="/setdefault")
	public Object setDefaultPc(String id, HttpServletRequest request, HttpServletResponse response) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		UserAddressAO userAddress = addressService.getAddress(id);
		if(userAddress != null) {
			CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
		}
		return addressService.setDefaultAddress(user.getId(), id);
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/region")
	public Object getRegionList(String parent) {
		if(parent == null) {
			parent=RegionAO.CITY_ID_KUNMING;
		}
		List<RegionAO> ret = addressService.getRegionByParent(parent);
		List<Map<String, String>> lst = new ArrayList<>();
		if(ret != null) {
			for(RegionAO rg : ret) {
				Map<String, String> rec = new HashMap<>();
				rec.put("id", rg.getId());
				rec.put("name", rg.getName());
				rec.put("type", rg.getType());
				rec.put("hasChild", rg.getHasChild());
				lst.add(rec);
			}
		}
		return lst;
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/region/country")
	public Object getCountryList(String parent) {
		if(parent == null) {
			parent=RegionAO.CITY_ID_KUNMING;
		}
		List<RegionAO> ret = addressService.getRegionByParent(parent);
		List<Map<String, String>> lst = new ArrayList<>();
		if(ret != null) {
			for(RegionAO rg : ret) {
				Map<String, String> rec = new HashMap<>();
				rec.put("id", rg.getId());
				rec.put("name", rg.getName());
				rec.put("type", rg.getType());
				rec.put("hasChild", rg.getHasChild());
				lst.add(rec);
			}
		}
		return lst;
	}
	
	@ResponseBody
	@RequestMapping(value="/region")
	public Object getRegionListPC(String parent) {
		return getRegionList(parent);
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/save")
	public Object saveAddress(UserAddressAO userAddress, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		userAddress.setUserId(user.getId());
		
		return addressService.save(userAddress);
	}
	
	@ResponseBody
	@RequestMapping(value="/save")
	public Object saveAddressPc(UserAddressAO userAddress, HttpServletRequest request) {
		return saveAddress(userAddress, request);
	}
	
	@ResponseBody
	@RequestMapping(value="/phone/remove")
	public Object removeAddress(String id, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		addressService.remove(user.getId(), id);
		return new Result<>("成功删除地址");
	}
	
	@ResponseBody
	@RequestMapping(value="/remove")
	public Object removeAddressPc(String id, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		addressService.remove(user.getId(), id);
		return new Result<>("成功删除地址");
	}
	
	@RequestMapping(value="/getaddress")
	public ModelAndView getAddress(String id, HttpServletRequest request, Model model) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		UserAddressAO ad = addressService.getAddress(id);
		
		if(ad.getUserId() != null && ad.getUserId().equals(user.getId())) {
			model.addAttribute("defaultAddress", ad);
		}
		return MVCViewName.INDEX_ADDRESS.view();
	}
}

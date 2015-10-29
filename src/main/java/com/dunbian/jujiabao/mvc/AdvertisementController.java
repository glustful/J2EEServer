package com.dunbian.jujiabao.mvc;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dunbian.jujiabao.appobj.extend.AdvertisementAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.service.advertisement.IAdvertisementService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController {

	@Resource
	private IAdvertisementService advertisementService;
	
	@Resource
	private ICartService cartService;
	
	public void setCurrentUser(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("currentMenu", "user");
			UserAO user = UserUtil.getCurrentLoginUser(request);
			model.addAttribute("currentUser", user);
			int cartCount = cartService.getCartCount(user.getId());
			model.addAttribute("cartCount", cartCount);
		} catch (Exception e) {}
	}

	@RequestMapping(value="")
	@ResponseBody
	public Object getAdvertisementPc(@CookieValue(value="town", defaultValue="") String town) {
		List<AdvertisementAO> data = advertisementService.getAdvertisement(town, new Date());
		if(data == null || data.isEmpty()) {
			return new TreeMap<>();
		}

		AdvertisementAO notice = null;
		for(Iterator<AdvertisementAO> itr = data.iterator(); itr.hasNext();) {
			AdvertisementAO adv = itr.next();
			if(AdvertisementAO.TYPE_NOTICE.equals(adv.getType())) {
				notice = adv;
				itr.remove();
			}
		}

		Map<String, Object> ret = new TreeMap<>();
		if(data != null && !data.isEmpty()) {
			ret.put("top", data);
		}

		if(notice != null) {
			ret.put("fill", notice);
		}

		return ret;
	}

	@RequestMapping(value="/phone")
	@ResponseBody
	public Object getAdvertisement(@CookieValue(value="town", defaultValue="") String town) {
		return getAdvertisementPc(town);
	}

	 
	@RequestMapping(value="/phone/detail")
	@ResponseBody
	public Object getAdvertisementDetail(String id) {
		return advertisementService.getAdvertisement(id);
	}


	@RequestMapping(value="/detail")
    public ModelAndView detail(String id, HttpServletRequest request, Model model, HttpServletResponse response) {
		setCurrentUser(model, request);
		AdvertisementAO advertisementAO = advertisementService.getAdvertisement(id);
		model.addAttribute("detail", advertisementAO);
        return MVCViewName.ADVERTISEMENT_DETAIL.view();
    }
}

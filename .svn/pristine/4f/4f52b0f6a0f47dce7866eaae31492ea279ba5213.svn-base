package com.dunbian.jujiabao.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.good.IGoodTypeService;
import com.dunbian.jujiabao.util.Constanst;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping(value="/site")
public class SiteController {

	@Resource
	private ICartService cartService;
	
	@Resource
	private IGoodTypeService goodTypeService;

	public void setCurrentUser(Model model, HttpServletRequest request) {
		try {
			UserAO user = UserUtil.getCurrentLoginUser(request);
			model.addAttribute("currentUser", user);
			int cartCount = cartService.getCartCount(user.getId());
			model.addAttribute("cartCount", cartCount);
		} catch (Exception e) {}
	}

    @RequestMapping(value="/client")
    public ModelAndView client(HttpServletRequest request, Model model) {
    	model.addAttribute("currentMenu", "client");
    	model.addAttribute("androidUrl", Constanst.CLIENT_DOWNLOAD_ANDROID + "?_" + System.currentTimeMillis());
    	model.addAttribute("iosUrl", Constanst.CLIENT_DOWNLOAD_IOS);
    	setCurrentUser(model, request);
    	return MVCViewName.SITE_CLIENT.view();
    }

    @RequestMapping(value="/about")
    public ModelAndView about(HttpServletRequest request, Model model) {
    	model.addAttribute("currentMenu", "about");
    	setCurrentUser(model, request);
    	return MVCViewName.SITE_ABOUT.view();
    }

    @RequestMapping(value="/wechat")
    public ModelAndView wechat(HttpServletRequest request, Model model) {
    	return MVCViewName.SITE_WECHAT_DOWNLOAD.view();
    }
    
    @RequestMapping(value="/phone/buding")
    public ModelAndView aboutBuding() {
    	return MVCViewName.SITE_BUDING.view();
    }
    
    @ResponseBody
    @RequestMapping(value="/nofilter/refresh")
    public Object refreshCache() {
    	goodTypeService.reload();
    	return new Result<Boolean>(true);
    }
}
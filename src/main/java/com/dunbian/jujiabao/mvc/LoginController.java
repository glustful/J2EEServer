package com.dunbian.jujiabao.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.exception.NoLoginException;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.session.SessionUtil;
import com.dunbian.jujiabao.service.user.IUserService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.Constraint;
import com.puddingnet.mvc.servlet.CookieUtil;
import com.puddingnet.mvc.servlet.SessionServiceFactory;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Resource
	private IUserService userService;
	
//	@RequestMapping("")
//	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model) {
//	}
	
	@ResponseBody
	@RequestMapping("/phone/islogin") 
	public Object isLogin(HttpServletRequest request){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", true);
		try {
			UserUtil.getCurrentLoginUser(request);
			ret.put("islogin", true);
		} catch (NoLoginException e) {
			ret.put("islogin", false);
		}
		
		return ret;
	}
	
	@ResponseBody
	@RequestMapping("/do")
	public Object dologin(String user, String pass, Boolean chkRememberMe, String returnUrl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result<UserAO> ret = userService.check4Login(user, pass);
		if(ret == null || !ret.isSuccess()) {
			return ret;
		}
		
		if(chkRememberMe != null && chkRememberMe) {
			CookieUtil.setCookie(Constraint.COOKIE_SESSION_ID, request.getSession().getId(), null, CookieUtil.MAXAGE_REMEMBER, response);
		} else {
			CookieUtil.setCookie(Constraint.COOKIE_SESSION_ID, request.getSession().getId(), null, CookieUtil.MAXAGE_BROWSER, response);
		}
		SessionUtil.setAttribute(UserUtil.SESSION_USER, ret.getData(), request);
		if(chkRememberMe != null && chkRememberMe) {
			SessionServiceFactory.getInstance().getService().login4Remember(ret.getData().getId(), request, response);
		} else {
			SessionServiceFactory.getInstance().getService().login(ret.getData().getId(), request, response);
		}
		return  ret;
	}
	
	
	@ResponseBody
	@RequestMapping("/phone")
	public Object login4Phone(String user, String pass, String returnUrl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Boolean chkRememberMe = true;
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			UserAO current = SessionUtil.getAttribute(UserUtil.SESSION_USER, UserAO.class, request);
			model.addAttribute("currentUser", current);
			if(returnUrl != null) {
				model.addAttribute("returnUrl", returnUrl);
			}
			return "/login/login";
		} else {
			Result<UserAO> ret = userService.check4Login(user, pass);
			if(ret == null || !ret.isSuccess()) {
				return new Result<>(false, ret == null ? "未知错误" : ret.getMsg());
			}
			
			if(chkRememberMe != null && chkRememberMe) {
				CookieUtil.setCookie(Constraint.COOKIE_SESSION_ID, request.getSession().getId(), null, CookieUtil.MAXAGE_REMEMBER, response);
			} else {
				CookieUtil.setCookie(Constraint.COOKIE_SESSION_ID, request.getSession().getId(), null, CookieUtil.MAXAGE_BROWSER, response);
			}
			SessionUtil.setAttribute(UserUtil.SESSION_USER, ret.getData(), request);
			
			if(chkRememberMe != null && chkRememberMe) {
				SessionServiceFactory.getInstance().getService().login4Remember(ret.getData().getId(), request, response);
			} else {
				SessionServiceFactory.getInstance().getService().login(ret.getData().getId(), request, response);
			}
			Result<UserAO> result = new Result<UserAO>(ret.getData());
			result.setExtend(request.getSession().getId());
			
			return result;
		}
	}
	
	@RequestMapping("/logoff")
	public ModelAndView logoffPc(HttpServletRequest request) {
		SessionUtil.removeAttribute( UserUtil.SESSION_USER, request);
		return new ModelAndView("redirect:http:/");
	}
	
	@ResponseBody
	@RequestMapping("/phone/logoff")
	public Object logoff(HttpServletRequest request) {
		SessionUtil.removeAttribute(UserUtil.SESSION_USER, request);
		return new Result<>("成功注销");
	}
	
	@ResponseBody
	@RequestMapping("/phone/validation")
	public Object getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		UserUtil.getCurrentLoginUser(request);
		
		return "";
	}
}

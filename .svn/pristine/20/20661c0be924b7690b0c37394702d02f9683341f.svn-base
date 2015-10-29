package com.dunbian.jujiabao.mvc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.SMSValidationError;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.session.SessionUtil;
import com.dunbian.jujiabao.framework.util.IPUtil;
import com.dunbian.jujiabao.framework.util.VerifyUtil;
import com.dunbian.jujiabao.message.ISMSService;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.user.IUserService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.Constraint;
import com.puddingnet.mvc.servlet.CookieUtil;
import com.puddingnet.mvc.servlet.SessionServiceFactory;
 
@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	
	@Resource
	private IUserService userService;
	
	@Resource
    private ISMSService smsService;
	
	@Resource
	private IAddressService addressService;
    
	
	private boolean checkSMSError(String mobile, HttpServletRequest request) {
		SMSValidationError error = SessionUtil.getAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, SMSValidationError.class, request);
		if(error == null) {
			error = new SMSValidationError();
			error.setKeepTime(System.currentTimeMillis());
			error.setLastTime(System.currentTimeMillis());
			error.setCount(1);
			error.setMobile(mobile);
			SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
		} else {
			if(System.currentTimeMillis() - error.getKeepTime() > SMSValidationError.CLEAN_TIME) {
				error.setKeepTime(System.currentTimeMillis());
				error.setLastTime(System.currentTimeMillis());
				error.setCount(1);
				error.setMobile(mobile);
				SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
			} else {
				if(error.getCount() + 1 >= SMSValidationError.MAX_COUNT) {
					return false;
				} else {
					error.setLastTime(System.currentTimeMillis());
					error.setCount(error.getCount() + 1);
					error.setMobile(mobile);
					SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
				}
			}
		}
		
		return true;
	}
	
    @RequestMapping(value="/phone/do", method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object register(String verifyCode,UserAO user, UserAddressAO userAddress, HttpServletRequest request, HttpServletResponse response) {
    	boolean verified = false;
    	user.setMobile(user.getUserName());
		verified = VerifyUtil.isSmsVerified(verifyCode, user.getMobile(), request);
		if(!verified) {
			if(!checkSMSError(user.getUserName(), request)) {
				VerifyUtil.clearSmsVerify(request);
			}
    		return new Result<>(false, "短信验证码输入错误");
		}
    	
		user.setStatus(UserAO.STATUS_NORMAL);
        user.setCredit(0);
        user.setGradeCredit(0);
        user.setCreateTime(new Date());
        user.setIp(IPUtil.getRealIp(request));
        user.setLogo(UserAO.DEFAULT_USER_LOGO);
        Result<UserAO>  ret = userService.register(user);
        if(ret == null) {
        	return new Result<>(false, "注册失败");
        } else if(!ret.isSuccess()) {
        	return new Result<>(false, "注册失败" + "[" + ret.getMsg() +"]");
        } else {
        	VerifyUtil.clearSmsVerify(request);
        	
        	if(userAddress.getRegionJson() != null && !"".equals(userAddress.getRegionJson().trim())) {
        		userAddress.setUserId(ret.getData().getId());
        		userAddress.setStatus(UserAddressAO.STATUS_DEFAULT);
        		addressService.save(userAddress);
        		
        		if(userAddress.getTownId() != null) {
        			CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
        		}
        	}
        	return ret;
        }
	}
    
    @RequestMapping(value="/do", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object registerPc(String verifyCode,UserAO user, UserAddressAO userAddress, HttpServletRequest request, HttpServletResponse response) {
    	boolean verified = false;
    	user.setMobile(user.getUserName());
    	verified = VerifyUtil.isSmsVerified(verifyCode, user.getMobile(), request);
    	if(!verified) {
    		if(!checkSMSError(user.getUserName(), request)) {
				VerifyUtil.clearSmsVerify(request);
			}
    		return new Result<>(false, "短信验证码输入错误");
    	}
    	
    	user.setStatus(UserAO.STATUS_NORMAL);
    	user.setCredit(0);
    	user.setGradeCredit(0);
    	user.setCreateTime(new Date());
    	user.setIp(IPUtil.getRealIp(request));
    	user.setLogo(UserAO.DEFAULT_USER_LOGO);
    	Result<UserAO>  ret = userService.register(user);
    	if(ret == null) {
    		return new Result<>(false, "注册失败");
    	} else if(!ret.isSuccess()) {
    		return new Result<>(false, "注册失败" + "[" + ret.getMsg() +"]");
    	} else {
    		VerifyUtil.clearSmsVerify(request);
    		
    		if(userAddress.getRegionJson() != null && !"".equals(userAddress.getRegionJson().trim())) {
        		userAddress.setUserId(ret.getData().getId());
        		userAddress.setStatus(UserAddressAO.STATUS_DEFAULT);
        		addressService.save(userAddress);
        		if(userAddress.getTownId() != null) {
        			CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
        		}
        	}
    		//实现自动登录
    		Result<UserAO> ret2 = userService.check4Login(user.getUserName(), user.getPassword());
    		if(ret2 == null || !ret2.isSuccess()) {
    			return ret;
    		}
			CookieUtil.setCookie(Constraint.COOKIE_SESSION_ID, request.getSession().getId(), null, CookieUtil.MAXAGE_BROWSER, response);
			SessionServiceFactory.getInstance().getService().login(ret.getData().getId(), request, response);
    		SessionUtil.setAttribute(UserUtil.SESSION_USER, ret.getData(), request);
    		return ret;
    	}
    }
    
    @RequestMapping(value="/sendSMS", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object sendMessage(String userName, HttpServletRequest request) {
    	if(userName == null || userName.length() != 11 || !userName.startsWith("1")) {
    		return new Result<>(false, "请输入正确的手机号");
    	}
    	if(userService.checkExists(userName)){
    		return new Result<>(false, "当前用户[" + userName + "]在系统中已存在，请尝试使用其他手机号注册");
    	}
    	String yzm = VerifyUtil.genSmsWords(userName, request);
    	return smsService.send(userName, "您的验证码是：" + yzm + "。请不要把验证码泄露给其他人。 ", IPUtil.getRealIp(request), request.getSession().getId(), null);
    }
    
    @RequestMapping(value="/findpassmsg", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object sendPasswordMessagePc(String userName, HttpServletRequest request) {
    	if(userName == null || userName.length() != 11 || !userName.startsWith("1")) {
    		return new Result<>(false, "请输入正确的手机号");
    	}

    	String yzm = VerifyUtil.genSmsWords(userName, request);
    	return smsService.send(userName, "您的验证码是：" + yzm + "。请不要把验证码泄露给其他人。 ", IPUtil.getRealIp(request), request.getSession().getId(), null);
    }
    
    @RequestMapping(value="/findPassword", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findPasswordPc(String userName, String verifyCode, String newPass, HttpServletRequest request) {
    	if(newPass == null || "".equals(newPass.trim())) {
    		return new Result<>(false, "新密码不能为空");
    	}
    	boolean verified = false;
    	verified = VerifyUtil.isSmsVerified(verifyCode, userName, request);
    	if(!verified) {
    		if(!checkSMSError(userName, request)) {
				VerifyUtil.clearSmsVerify(request);
			}
    		return new Result<>(false, "短信验证码输入错误");
    	}
    	
    	Result<Boolean> ret = userService.resetPwd(userName, newPass);
    	if(ret != null && ret.isSuccess()) {
    		VerifyUtil.clearSmsVerify(request);
    	}
    	
    	return ret;
    }
    
    
    @RequestMapping(value="/phone/findpassmsg", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object sendPasswordMessage(String userName, HttpServletRequest request) {
    	return sendPasswordMessagePc(userName, request);
    }
    
    @RequestMapping(value="/phone/findPassword", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findPassword(String userName, String verifyCode, String newPass, HttpServletRequest request) {
    	return findPasswordPc(userName, verifyCode, newPass, request);
    }
    
    
    @RequestMapping(value="/phone/legalprovisions")
    public void getLegalProvisions(HttpServletResponse response){
    	try {
    		response.setContentType("application/json;charset=UTF-8");
    		InputStream in = this.getClass().getClassLoader().getResourceAsStream("legal.txt");
    		byte[] tmp = new byte[1024];
    		int len = -1;
    		
    		while((len=in.read(tmp)) != -1) {
    			response.getOutputStream().write(tmp, 0, len);
    		}
    		response.getOutputStream().flush();
		} catch (Exception e) {
		} finally {
			try {
				response.flushBuffer();
			} catch (IOException e) {}
		}
    }
    
    
    
    @RequestMapping(value="/legalprovisions")
    public ModelAndView getLegalProvisionsPc(HttpServletResponse response, Model model){
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	InputStream in = null;
    	try {
    		in = this.getClass().getClassLoader().getResourceAsStream("legal.txt");
    		byte[] tmp = new byte[1024];
    		int len = -1;
    		
    		while((len=in.read(tmp)) != -1) {
    			bos.write(tmp, 0, len);
    		}
    		bos.flush();
    		
    		String json = new String(bos.toByteArray());
    		model.addAttribute("json", json);
    		return MVCViewName.INDEX_LEGAL.view();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {}
			try {
				bos.close();
			} catch (IOException e) {}
		}
    }
}

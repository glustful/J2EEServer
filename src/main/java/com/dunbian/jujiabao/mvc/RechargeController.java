package com.dunbian.jujiabao.mvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.AccountDetailAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.RechargeRecordAO;
import com.dunbian.jujiabao.appobj.extend.RechargeSetAO;
import com.dunbian.jujiabao.appobj.extend.SMSValidationError;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserWalletAO;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.session.SessionUtil;
import com.dunbian.jujiabao.framework.util.IPUtil;
import com.dunbian.jujiabao.framework.util.VerifyUtil;
import com.dunbian.jujiabao.message.ISMSService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.recharge.IRechargeService;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping(value="/recharge")
public class RechargeController {

	@Resource
	private IRechargeService rechargeService;
	
	@Resource
    private ISMSService smsService;
	
	@Resource
	private ICartService cartService;
	
	public void setCurrentUser(Model model, HttpServletRequest request) {
		try {
			UserAO user = UserUtil.getCurrentLoginUser(request);
			model.addAttribute("currentUser", user);
			int cartCount = cartService.getCartCount(user.getId());
			model.addAttribute("cartCount", cartCount);
		} catch (Exception e) {}
	}
	
	@RequestMapping(value="/phone/exists")
	@ResponseBody
	public Result<Boolean> walletExists(HttpServletRequest request) {
		return walletExistsPc(request);
	}
	
	@RequestMapping(value="/exists")
	@ResponseBody
	public Result<Boolean> walletExistsPc(HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		return rechargeService.isExistsWallet(user.getId());
	}
	
	@RequestMapping(value="/bankindex")
	public ModelAndView bankIndex(HttpServletRequest request, Model model) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		setCurrentUser(model, request);
		model.addAttribute("activetap", "bankindex");
		Result<Boolean> exists = rechargeService.isExistsWallet(user.getId());
		if(exists != null && exists.isSuccess() && exists.getData()) {
			UserWalletAO wallet = rechargeService.getUserWallet(user.getId());
			List<RechargeSetAO> setList = rechargeService.getRechargeSetList();
			model.addAttribute("wallet", wallet);
			model.addAttribute("setList", setList);
			
			return MVCViewName.PROFILE_BANKINDEX.view();
		} else {
			return MVCViewName.PROFILE_BANKPDSET.view();
		}
	}
	
	@RequestMapping(value="/phone/setpass")
	@ResponseBody
	public Result<Boolean> setPass(String pass, HttpServletRequest request) {
		return setPassPc(pass, request);
	}
	
	@RequestMapping(value="/setpass")
	@ResponseBody
	public Result<Boolean> setPassPc(String pass, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		return rechargeService.setPass(user.getId(), pass);
	}
	   
    @RequestMapping(value="/findpaypassmsg", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object sendPasswordMessagePc(String userName, HttpServletRequest request) {
    	if(userName == null || userName.length() != 11 || !userName.startsWith("1")) {
    		return new Result<>(false, "请输入正确的手机号");
    	}

    	String yzm = VerifyUtil.genSmsWords(userName, request);
    	return smsService.send(userName, "您的验证码是：" + yzm + "。请不要把验证码泄露给其他人。 ", IPUtil.getRealIp(request), request.getSession().getId(), null);
    }
    
    @RequestMapping(value="/findpayPassword", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findPasswordPc(String userName, String verifyCode, String newPass, HttpServletRequest request) {
    	UserAO user = UserUtil.getCurrentLoginUser(request);
    	if(userName == null || !userName.equals(user.getUserName())) {
    		return new Result<>(false, "请使用注册手机号找回");
    	}
    	
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
    	
    	Result<Boolean> ret = rechargeService.resetPass(user.getId(), newPass);
    	if(ret != null && ret.isSuccess()) {
    		VerifyUtil.clearSmsVerify(request);
    	}
    	
    	return ret;
    }
    
    
    @RequestMapping(value="/phone/findpaypassmsg", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object sendPasswordMessage(String userName, HttpServletRequest request) {
    	return sendPasswordMessagePc(userName, request);
    }
    
    @RequestMapping(value="/phone/findpayPassword", method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findPassword(String userName, String verifyCode, String newPass, HttpServletRequest request) {
    	return findPasswordPc(userName, verifyCode, newPass, request);
    }
    
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
	
	@RequestMapping(value="/phone/detaillist")
	@ResponseBody
	public Object getDetailList(Integer pageNo, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		if(pageNo == null) {
			pageNo = 1;
		}
		
		Page page = new Page(pageNo, 5);
		page.setPageSize(page.getPageSize() + 1);
		List<AccountDetailAO> dataList = rechargeService.getDetailList(user.getId(), page);
		Map<String, Object> ret = new HashMap<>();
		ret.put("dataList", dataList);
		if(dataList != null && dataList.size() > 5) {
			dataList.remove(5);
			ret.put("haveMore", true);
		} else {
			ret.put("haveMore", false);
		}
		
		return ret;
	}
	
	@RequestMapping(value="/detaillist")
	public ModelAndView getDetailListPc(Integer pageNo, HttpServletRequest request, Model model) {
		setCurrentUser(model, request);
		UserAO user = UserUtil.getCurrentLoginUser(request);
		if(pageNo == null) {
			pageNo = 1;
		}
		
		Page page = new Page(pageNo, 5);
		page.setPageSize(page.getPageSize() + 1);
		List<AccountDetailAO> dataList = rechargeService.getDetailList(user.getId(), page);
		model.addAttribute("dataList", dataList);
		model.addAttribute("activetap", "bankindex");
		model.addAttribute("activeheadertap", "bankbilling");
		model.addAttribute("pageNo", ++pageNo);
		if(dataList != null && dataList.size() > 5) {
			dataList.remove(5);
			model.addAttribute("haveMore", true);
		} else {
			model.addAttribute("haveMore", false);
		}
		if(pageNo > 2) {
			return MVCViewName.PROFILE_BANKBILLINGITEM.view();
		}
        return MVCViewName.PROFILE_BANKBILLING.view();
	}
	
	@RequestMapping(value="/phone/prepare")
	@ResponseBody
	public Result<OrderAO> prepare4Pay(String orderId, HttpServletRequest request) {
		return prepare4PayPc(orderId, request);
	}
	
	@RequestMapping(value="/prepare")
	@ResponseBody
	public Result<OrderAO> prepare4PayPc(String orderId, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		
		return rechargeService.prepare4Pay(user.getId(), orderId);
	}
	
	@RequestMapping(value="/phone/pay")
	@ResponseBody
	public Result<OrderAO> walletPay(String orderId, String payPass, BigDecimal walletMoney, HttpServletRequest request) {
		return walletPayPc(orderId, payPass, walletMoney, request);
	}
	
	@RequestMapping(value="/pay")
	@ResponseBody
	public Result<OrderAO> walletPayPc(String orderId, String payPass, BigDecimal walletMoney, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		return rechargeService.walletPay(user.getId(), orderId, payPass, walletMoney);
	}
	
	@RequestMapping(value="/phone/page")
	@ResponseBody
	public Object rechargePage(HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		UserWalletAO wallet = rechargeService.getUserWallet(user.getId());
		List<RechargeSetAO> setList = rechargeService.getRechargeSetList();
		Map<String, Object> ret = new HashMap<>();
		ret.put("wallet", wallet);
		ret.put("setList", setList);
		
		return ret;
	}
	
	@RequestMapping(value="/phone/finish")
	@ResponseBody
	public Object rechargeFinish(String recordId, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		UserWalletAO wallet = rechargeService.getUserWallet(user.getId());
		Map<String, Object> ret = new HashMap<>();
		ret.put("balance", wallet.getValidAmount());
		//这里读取订单数据
		RechargeRecordAO record = rechargeService.getRechargeById(recordId);
		ret.put("amount", record.getAmount());
		ret.put("giftBalance", record.getGift());
		ret.put("status", record.getStatus());
		return ret;
	}
	
	@RequestMapping(value="/page")
	public ModelAndView rechargePagePc(HttpServletRequest request) {
//		UserAO user = UserUtil.getCurrentLoginUser(request);
		return null;
	}
	
	
	@RequestMapping(value="/phone/do")
	@ResponseBody
	public Object doit(String setId, HttpServletRequest request) {
		return doitPc(setId, request);
	}
	
	@RequestMapping(value="/do")
	@ResponseBody
	public Object doitPc(String setId, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		Result<RechargeRecordAO> ret = rechargeService.genRecharge(setId, user.getId());
		return ret;
	}
	
	@ResponseBody
    @RequestMapping(value="/phone/resetpwd")
    public Object resetpwd(String oldPwd, String newPwd, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        UserWalletAO userWalletAO = rechargeService.getUserWallet(user.getId());
        if(oldPwd == null || oldPwd.isEmpty() || newPwd == null || newPwd.isEmpty()) {
            return new Result<UserAO>(false, "新旧密码都不能为空！");
        }
        Result<Boolean> ret = rechargeService.resetPass(userWalletAO.getId(), oldPwd, newPwd);
        return ret;
    }
    
    @ResponseBody
    @RequestMapping(value="/resetpwd")
    public Object resetpwdPc(String oldPwd, String newPwd, HttpServletRequest request) {
    	return resetpwd(oldPwd, newPwd, request);
    }
}

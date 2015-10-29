package com.dunbian.jujiabao.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderExtraAO;
import com.dunbian.jujiabao.appobj.extend.SeckillAO;
import com.dunbian.jujiabao.appobj.extend.SeckillInstanceAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.EncryptUtil;
import com.dunbian.jujiabao.service.seckill.ISeckillService;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping(value="/seckill")
public class SeckillController {

	@Resource
	private ISeckillService seckillService;
	
	@RequestMapping(value="/phone")
	@ResponseBody
	public Object kill(String id, String md, String answer, OrderExtraAO orderExtra,  HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		
		SeckillAO sec = seckillService.getSeckill(id);
		if(sec == null) {
			return new Result<>(false, "您参加的秒杀活动不存在");
		}
		
		if(System.currentTimeMillis() < sec.getStartTime().getTime()) {
			return new Result<>(false, "秒杀尚未开始，敬请期待");
		} else if(System.currentTimeMillis() > sec.getEndTime().getTime()) {
			return new Result<>(false, "对不起，您来晚了，秒杀已结束");
		}
		
		if(sec.getQuestionPhone() != null && !"".equals(sec.getQuestionPhone().trim()) && (md == null || "".equals(md.trim()))) {
			SeckillAO secRet = new SeckillAO();
			secRet.setQuestionPhone(sec.getQuestionPhone());
			secRet.setMd(EncryptUtil.encrypt(user.getId() + sec.getMd()));
			Result<SeckillAO> ret = new Result<SeckillAO>(secRet);
			ret.setCode(200);
			
			return ret;
		} else if(sec.getQuestionPhone() != null && !"".equals(sec.getQuestionPhone().trim())) {
			String md2 = EncryptUtil.encrypt(user.getId() + sec.getMd());
			if(!md.equals(md2)) {
				return new Result<>(false, "秒杀失败");
			} else if(answer == null || !answer.trim().equals(sec.getAnswerPhone().trim())) {
				SeckillAO secRet = new SeckillAO();
				secRet.setQuestionPhone(sec.getQuestionPhone());
				secRet.setMd(EncryptUtil.encrypt(user.getId() + sec.getMd()));
				Result<SeckillAO> ret = new Result<SeckillAO>(secRet);
				ret.setCode(300);
				
				return ret;
			}
		}
		
		if(orderExtra.getPlatform() != null) {
			if("ios".equals(orderExtra.getPlatform().toLowerCase())) {
				orderExtra.setOrderFrom(OrderAO.ORDER_FROM_IOS);
			} else if("android".equals(orderExtra.getPlatform().toLowerCase())) {
				orderExtra.setOrderFrom(OrderAO.ORDER_FROM_ANDROID);
			} else {
				orderExtra.setOrderFrom(OrderAO.ORDER_FROM_PHONE);
			}
		} else {
			orderExtra.setOrderFrom(OrderAO.ORDER_FROM_PHONE);
		}
		
		return seckillService.kill(SeckillInstanceAO.INVENTORY_TYPE_PHONE, user, sec, orderExtra);
	}
	
	@RequestMapping(value="")
	@ResponseBody
	public Object killPc(String id, String md, String answer, HttpServletRequest request) {
		UserAO user = UserUtil.getCurrentLoginUser(request);
		
		SeckillAO sec = seckillService.getSeckill(id);
		if(sec == null) {
			return new Result<>(false, "您参加的秒杀活动不存在");
		}
		
		if(System.currentTimeMillis() < sec.getStartTime().getTime()) {
			return new Result<>(false, "秒杀尚未开始，敬请期待");
		} else if(System.currentTimeMillis() > sec.getEndTime().getTime()) {
			return new Result<>(false, "对不起，您来晚了，秒杀已结束");
		}
		
		if(sec.getQuestionWeb() != null && !"".equals(sec.getQuestionWeb().trim()) && (md == null || "".equals(md.trim()))) {
			SeckillAO secRet = new SeckillAO();
			secRet.setQuestionWeb(sec.getQuestionWeb());
			secRet.setMd(EncryptUtil.encrypt(user.getId() + sec.getMd()));
			Result<SeckillAO> ret = new Result<SeckillAO>(secRet);
			ret.setCode(200);
			
			return ret;
		} else if(sec.getQuestionWeb() != null && !"".equals(sec.getQuestionWeb().trim())) {
			String md2 = EncryptUtil.encrypt(user.getId() + sec.getMd());
			if(!md.equals(md2)) {
				return new Result<>(false, "秒杀失败");
			} else if(answer == null || !answer.equals(sec.getAnswerWeb())) {
				SeckillAO secRet = new SeckillAO();
				secRet.setQuestionWeb(sec.getQuestionWeb());
				secRet.setMd(EncryptUtil.encrypt(user.getId() + sec.getMd()));
				Result<SeckillAO> ret = new Result<SeckillAO>(secRet);
				ret.setCode(300);
				
				return ret;
			}
		}
		
		OrderExtraAO orderExtra = new OrderExtraAO();
		orderExtra.setOrderFrom(OrderAO.ORDER_FROM_PC);
		
		return seckillService.kill(SeckillInstanceAO.INVENTORY_TYPE_PHONE, user, sec, orderExtra);
	}
}

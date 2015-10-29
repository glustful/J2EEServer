package com.dunbian.jujiabao.message;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.dunbian.jujiabao.appobj.extend.SmsAO;
import com.dunbian.jujiabao.appobj.generator.SmsExample;
import com.dunbian.jujiabao.db.generator.SmsMapper;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.HttpClient;

public class SMSService implements ISMSService {

	@Resource
	private SmsMapper smsMapper;
	
	private static final int MSG_MAX_LEN = 60;
	
	private static final long DAY_MIL_SEC = 3600*24*1000;
	
	private static final long PERIOD_MIL_SEC = 60*1000;
	
	private static final int DAY_MAX = 99999;
	
	private static final int DAY_MOBILE_MAX = 9;
	
	private static final int DAY_IP_MAX = 99999;
	
	private static final int DAY_USER_MAX = 9;
	
	private String user;
	
	private String password;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	Pattern msgPattern = Pattern.compile("<msg>(.*?)</msg>");
	
	@Override
	public Result<Boolean> send(String mobile, String msg, String ip, String sessionId,
			String userId) {
		if(ip == null || "".equals(ip)) {
			return new Result<>(false, "未知错误");
		}
		
//		if(sessionId == null || "".equals(sessionId)) {
//			return new Result<>(false, "会话错误");
//		}
		
		if(msg == null || "".equals(msg.trim()) || msg.length() >= MSG_MAX_LEN) {
			return new Result<>(false, "信息内容错误");
		}
		Date yesterday = new Date(System.currentTimeMillis() - DAY_MIL_SEC);
		
		SmsExample se = new SmsExample();
		se.createCriteria().andCreateTimeGreaterThanOrEqualTo(yesterday);
		int zs = smsMapper.countByExample(se);
		if(zs >= DAY_MAX) {
			return new Result<>(false, "今日短信配额已用完");
		}
		
		se.clear();
		se.createCriteria().andMobileEqualTo(mobile).andCreateTimeGreaterThanOrEqualTo(yesterday);
		int mzs = smsMapper.countByExample(se);
		
		if(mzs >= DAY_MOBILE_MAX) {
			return new Result<>(false, "今日短信配额已用完");
		}
		
//		se.clear();
//		se.createCriteria().andIpEqualTo(ip).andCreateTimeGreaterThanOrEqualTo(yesterday);
//		int izs = smsMapper.countByExample(se);
//		
//		if(izs >= DAY_IP_MAX) {
//			return new Result<>(false, "今日短信配额已用完");
//		}
		
//		se.clear();
//		se.createCriteria().andIpEqualTo(ip).andCreateTimeGreaterThanOrEqualTo(new Date(System.currentTimeMillis() - PERIOD_MIL_SEC));
//		int szs = smsMapper.countByExample(se);
//		
//		if(szs >= 1) {
//			return new Result<>(false, "请耐心等待。。。");
//		}
		
		if(userId != null && !"".equals(userId)) {
			se.clear();
			se.createCriteria().andUserIdEqualTo(userId).andCreateTimeGreaterThanOrEqualTo(yesterday);
			int uzs = smsMapper.countByExample(se);
			
			if(uzs >= DAY_USER_MAX) {
				return new Result<>(false, "今日短信配额已用完");
			}
		}
		
		SmsAO ao = new SmsAO();
		ao.setIp(ip);
		ao.setMessage(msg);
		ao.setMobile(mobile);
		ao.setCreateTime(new Date());
		ao.setSessionId(sessionId);
		ao.setUserId(userId);
		
		smsMapper.insert(ao);
		
		HttpClient client = new HttpClient();
		client.post("http://106.ihuyi.cn/webservice/sms.php?method=Submit");
		client.addParam("account", user);
		client.addParam("password", password);
		client.addParam("mobile", mobile);
		client.addParam("content", msg);
		int ret = client.access();
		if(HttpClient.HTTP_CODE_200 != ret) {
			smsMapper.deleteByPrimaryKey(ao.getId());
			return new Result<>(false, "发送短信失败");
		} else {
			String httpResponse = client.getResponse();
			if(httpResponse != null && httpResponse.indexOf("<code>2</code>") == -1) {
				Matcher m = msgPattern.matcher(httpResponse);
				String err = "";
				if(m.find()) {
					err = m.group(1);
				}
				smsMapper.deleteByPrimaryKey(ao.getId());
				return new Result<>(false, "发送短信失败:" + err);
			} else if(httpResponse == null){
				smsMapper.deleteByPrimaryKey(ao.getId());
				return new Result<>(false, "发送短信失败");
			}
		}
		
		return new Result<>(true);
	}

}

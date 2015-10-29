package com.dunbian.jujiabao.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.util.Constanst;


@Controller
@RequestMapping(value="/download")
public class DownloadController {

	@RequestMapping(value="/nofilter/android")
	public ModelAndView downloadAndroid(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if(userAgent != null && userAgent.contains("MicroMessenger")) {
			return MVCViewName.SITE_WECHAT_DOWNLOAD.view();
		}
		
		InputStream in = null;
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("upgrade", false);
		try {
    		in = this.getClass().getClassLoader().getResourceAsStream("version.properties");
    		Properties config = new Properties();
    		config.load(in);
    		String url = (String)config.get("android_url");
    		return new ModelAndView("redirect:" + url);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:" + Constanst.CLIENT_DOWNLOAD_ANDROID + "?_" + System.currentTimeMillis());
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping(value="/nofilter/ios")
	public Object downloadIos() {
		return new ModelAndView("redirect:" + Constanst.CLIENT_DOWNLOAD_IOS);
	}
	
	@ResponseBody
	@RequestMapping(value="/nofilter/test")
	public Object test(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, String>> heads = new ArrayList<>();
		Enumeration<String> enu = request.getHeaderNames();
		while(enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = request.getHeader(key);
			Map<String, String> rec = new HashMap<>();
			
			rec.put(key, value);
			heads.add(rec);
		}
		
		return heads;
	}
}

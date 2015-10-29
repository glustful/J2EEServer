package com.dunbian.jujiabao.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/version")
public class VersionController {

	@ResponseBody
	@RequestMapping("/phone")
	public Object getCurrentVersion(String version, String platform) {
		InputStream in = null;
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("upgrade", false);
		try {
    		in = this.getClass().getClassLoader().getResourceAsStream("version.properties");
    		Properties config = new Properties();
    		config.load(in);
    		if(version != null && !version.isEmpty() && platform != null && !platform.isEmpty()) {
    			if(platform != null && platform.equalsIgnoreCase("ios") && config.get("ios_version") != null){
    				
    				int versionInt = 100;
    				try {
    					versionInt = Integer.parseInt(version);
					} catch (Exception e) {
						return ret;
					}
        			int new_version = Integer.parseInt(config.get("ios_version").toString());
        			if(new_version > versionInt) {//需要升级
        				ret.put("upgrade", true);
        				ret.put("new_version", new_version);
        				ret.put("content", config.get("ios_content"));
        				ret.put("must", false);
        				ret.put("url", config.get("ios_url"));
        			}
				}else if(platform != null && platform.equalsIgnoreCase("android") 
						&& config.get("android_version") != null && config.get("android_comp_version") != null){
					int versionInt = 100;
    				try {
    					versionInt = Integer.parseInt(version);
					} catch (Exception e) {
						return ret;
					}
        			int new_version = Integer.parseInt(config.get("android_version").toString());
        			if(new_version > versionInt) {//需要升级
        				ret.put("upgrade", true);
        				ret.put("new_version", new_version);
        				ret.put("content", config.get("android_content"));
        				ret.put("must", false);
        				ret.put("url", config.get("android_url"));
        				int comp_version = Integer.parseInt(config.get("android_comp_version").toString());
    					if(comp_version > versionInt) {
    						ret.put("must", true);
    					}
        			}
				} else {
					return ret;
				}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
}

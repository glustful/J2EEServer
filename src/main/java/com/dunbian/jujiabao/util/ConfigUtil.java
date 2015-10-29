package com.dunbian.jujiabao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dunbian.jujiabao.appobj.extend.ConfigAO;
import com.dunbian.jujiabao.appobj.generator.ConfigExample;
import com.dunbian.jujiabao.db.generator.ConfigMapper;


public class ConfigUtil {
	
	private static Map<String, String> configs = null;
	
	public static final String ORDER_LIMIT = "ORDER_LIMIT";
	
	public static final String ORDER_NOTICE_PHONE = "ORDER_NOTICE_PHONE";
	
	@Resource
	 private ConfigMapper configMapper;
	
	 public String getConfigValue(String key) {
		if(configs == null) {
			configs = new HashMap<String,String>();
			ConfigExample example = new ConfigExample();
	    	example.createCriteria().andConfigKeyEqualTo(ORDER_LIMIT);
	    	List<ConfigAO> configList = configMapper.selectByExample(example);
	    	if(configList != null && !configList.isEmpty()) {
	    		 for(ConfigAO config : configList) {
	    			if(config != null && config.getConfigKey() != null) {
	    				configs.put(config.getConfigKey(), config.getConfigValue());
	    			}
	    		 }
	    	}
		}
		
		return configs.get(key);
	}
	 
    public int getOrderLimtCount() {
    	int orderLimt = 200;
    	ConfigExample example = new ConfigExample();
    	example.createCriteria().andConfigKeyEqualTo("ORDER_LIMIT");
    	List<ConfigAO> configs = configMapper.selectByExample(example);
    	if(configs != null && !configs.isEmpty()) {
    		String tmp = configs.get(0).getConfigValue();
    		if(tmp != null) {
    			try {
    				orderLimt = Integer.parseInt(tmp);
				} catch (Exception e) {
					orderLimt = 200;
				}
    		}
    	}
    	return orderLimt;
    }
 
}

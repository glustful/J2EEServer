package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.dunbian.jujiabao.appobj.generator.Advertisement;

@JsonSerialize(include=Inclusion.NON_NULL)
public class AdvertisementAO extends Advertisement implements Serializable {

	private static final long serialVersionUID = 5613188072694303535L;

	public static final String TYPE_CAROUSEL  = "00";
	
	public static final String TYPE_NOTICE = "10";
	
	public static final String STATUS_NOMARL = "00";
	
	public static final String STATUS_INVALID = "10";
	
	public List<AdvDetailAO> getContentDetails() {
		List<AdvDetailAO> ret = null;

		try {
			ret = JSON.parseObject(this.getContent(), new TypeReference<List<AdvDetailAO>>(){});
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return ret;
	}
	
	public AdvActionAO getAction() {
		AdvActionAO ret = null;

		try {
			ret = JSON.parseObject(this.getActions(), new TypeReference<AdvActionAO>(){});
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return ret;
	}
	
}

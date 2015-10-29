package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import com.dunbian.jujiabao.appobj.generator.Resource;

public class ResourceAO extends Resource implements Serializable {

	private static final long serialVersionUID = -7743307667115109494L;

	//删除
	public static final String STATUS_DELETED = "00";
	//正常
	public static final String STATUS_NOMAL = "10";
	
	/** 附件类型 00图片 */
	public static final String RESOURCE_TYPE_IMG = "00";
	/** 附件类型  10视频  */
	public static final String RESOURCE_TYPE_VIDEO = "10";
	/** 附件类型  20附件 */
	public static final String RESOURCE_TYPE_FILE = "20";
	
	/** 业务类型 00套餐主图 */
	public static final String DATA_TYPE_SET_MAIN = "00";
	/** 业务类型 10套餐轮番图 */
	public static final String DATA_TYPE_SET_MULTI = "10";
	/** 业务类型 20单品主图 */
	public static final String DATA_TYPE_DETAIL_MAIN = "20";
	/** 业务类型30用户头像*/
	public static final String DATA_TYPE_DETAIL_USERHEAD = "30";
}

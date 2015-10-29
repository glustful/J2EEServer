package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dunbian.jujiabao.appobj.generator.Discount;

@JsonSerialize(include=Inclusion.NON_NULL)
public class DiscountAO extends Discount implements Serializable {

	private static final long serialVersionUID = 3381828878940779540L;

	public static final String STATUS_NOMARL = "10";
	
	public static final String STATUS_DISABLED = "00";
	
	
	/**是否针对新注册用户  否- 00 */
	public static final String FOR_NEW_NO = "00";
	/**是否针对新注册用户  是- 10 */
	public static final String FOR_NEW_YES = "10";
	
}

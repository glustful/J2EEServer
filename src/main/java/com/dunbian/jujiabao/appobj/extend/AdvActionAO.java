package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;

public class AdvActionAO implements Serializable {

	private static final long serialVersionUID = 800980587157021125L;
 
	private String name;
	
	private String function;
		
	private String args;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
	
}

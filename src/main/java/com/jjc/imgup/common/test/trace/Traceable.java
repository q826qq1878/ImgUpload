package com.jjc.imgup.common.test.trace;

public abstract class Traceable {
	private String name;
	
	public Traceable(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	abstract public void invoke();
}

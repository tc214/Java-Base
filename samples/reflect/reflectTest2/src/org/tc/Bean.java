package org.tc;

public class Bean {

	private String name;
	
	public Bean() {}
	
	public Bean(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
	
}

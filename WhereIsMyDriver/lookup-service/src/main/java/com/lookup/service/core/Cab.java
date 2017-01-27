package com.lookup.service.core;

public class Cab extends Vehicle{

	private CabType cabtype;
	public Cab(String vechicleNumber) {
		super(vechicleNumber);
	}
	public CabType getCabtype() {
		return cabtype;
	}
	public void setCabtype(CabType cabtype) {
		this.cabtype = cabtype;
	}
	
	

}

package com.lookup.service.core;

abstract class Vehicle {
    String vechicleNumber;

	public Vehicle(String vechicleNumber) {
		super();
		this.vechicleNumber = vechicleNumber;
	}

	public String getVechicleNumber() {
		return vechicleNumber;
	}
    
}

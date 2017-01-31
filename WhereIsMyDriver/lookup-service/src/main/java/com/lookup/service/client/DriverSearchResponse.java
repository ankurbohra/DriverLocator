package com.lookup.service.client;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "driverStatusList" })
public class DriverSearchResponse {

	@JsonProperty
	private final List<DriverPositionStatus> driverStatusList;

	@JsonCreator
	public DriverSearchResponse(
			@JsonProperty("driverStatusList") final List<DriverPositionStatus> driverStatusList) {

		this.driverStatusList = driverStatusList;

	}

	public DriverSearchResponse() {
		this(new ArrayList<DriverPositionStatus>(20));
	}

	public List<DriverPositionStatus> getDriverStatusList() {
		return driverStatusList;
	}
}

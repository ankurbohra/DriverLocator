package com.lookup.service.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverPositionStatus {
	@JsonProperty
	final private int driverId;

	@JsonProperty
	final private double latitude;

	@JsonProperty
	final private double longitude;

	@JsonProperty
	final private double distance;

	@JsonCreator
	public DriverPositionStatus(@JsonProperty("driverId") int driverId,
			@JsonProperty("latitude") double latitude,
			@JsonProperty("longitude") double longitude,
			@JsonProperty("distance") double distance) {
		this.driverId = driverId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	public int getDriverId() {
		return driverId;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getDistance() {
		return distance;
	}
}

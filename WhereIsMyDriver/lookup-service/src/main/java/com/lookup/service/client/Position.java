package com.lookup.service.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Position {

	@JsonProperty
	@NotNull
	@Max(90)
	@Min(-90)
	private double latitude; // not making final, Jackson barfs

	@JsonProperty
	@NotNull
	@Max(180)
	@Min(-180)
	private double longitude;

	@JsonProperty
	@NotNull
	private double accuracy = 0;

	@JsonCreator
	public Position(@JsonProperty("latitude") final double latitude,
			@JsonProperty("longitude") final double longitude,
			@JsonProperty("accuracy") final double accuracy) {

		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
	}

	public Position(final double latitude, final double longitude) {
		this(latitude, longitude, 0.0);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Position position = (Position) o;

		if (Double.compare(position.latitude, latitude) != 0)
			return false;
		if (Double.compare(position.longitude, longitude) != 0)
			return false;
		return Double.compare(position.accuracy, accuracy) == 0;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(accuracy);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}

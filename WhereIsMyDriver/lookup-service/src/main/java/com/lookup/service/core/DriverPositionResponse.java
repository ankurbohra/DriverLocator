package com.lookup.service.core;

public class DriverPositionResponse {
	// the driver id itself
	final private String driverId;

	// the distnace is w.r.t to 'fromPosition'
	final private Position fromPosition;

	// lat,long of the Geoobject's in question
	final private Position ownPosition;

	// aerial distance in meters
	final private double distance;

	public DriverPositionResponse(String driverId, Position fromPosition,
			Position ownPosition, double distance) {
		super();
		this.driverId = driverId;
		this.fromPosition = fromPosition;
		this.ownPosition = ownPosition;
		this.distance = distance;
	}

	public String getDriverId() {
		return driverId;
	}

	public Position getFromPosition() {
		return fromPosition;
	}

	public Position getOwnPosition() {
		return ownPosition;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result
				+ ((fromPosition == null) ? 0 : fromPosition.hashCode());
		result = prime * result
				+ ((ownPosition == null) ? 0 : ownPosition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverPositionResponse other = (DriverPositionResponse) obj;
		if (Double.doubleToLongBits(distance) != Double
				.doubleToLongBits(other.distance))
			return false;
		if (driverId == null) {
			if (other.driverId != null)
				return false;
		} else if (!driverId.equals(other.driverId))
			return false;
		if (fromPosition == null) {
			if (other.fromPosition != null)
				return false;
		} else if (!fromPosition.equals(other.fromPosition))
			return false;
		if (ownPosition == null) {
			if (other.ownPosition != null)
				return false;
		} else if (!ownPosition.equals(other.ownPosition))
			return false;
		return true;
	}

}

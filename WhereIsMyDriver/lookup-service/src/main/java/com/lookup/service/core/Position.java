package com.lookup.service.core;

public class Position {final private static double LATITUDE_MAX = 90.0d;
final private static double LATITUDE_MIN = -90.0d;

final private static double LONGITUDE_MAX = 180.0d;
final private static double LONGITUDE_MIN = -180.0d;

public static final String INVALID_LATITUDE_MESSAGE = "Invalid latitude, should be between [-90,90]";
public static final String INVALID_LONGITUDE_MESSAGE = "Invalid longitude, should be between [-180,180]";

private static final double TEN_METER_DECIMAL_OFFSET_IN_LATLONG = 0.0001d;

private final double latitude;
private final double longitude;

public Position(double latitude, double longitude) {

    validate(latitude, longitude);
    this.latitude = latitude;
    this.longitude = longitude;
}

public static void validate(double latitude, double longitude) {

    if (latitude > LATITUDE_MAX || latitude < LATITUDE_MIN)
        throw new InvalidPositionException(INVALID_LATITUDE_MESSAGE + "Provided is " + Double.toString(latitude));

    if (longitude > LONGITUDE_MAX || longitude < LONGITUDE_MIN)
        throw new InvalidPositionException(INVALID_LONGITUDE_MESSAGE + "Provided is " + Double.toString(longitude));
}

public double getLatitude() {
    return latitude;
}

public double getLongitude() {
    return longitude;
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(longitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	Position other = (Position) obj;
	if (Double.doubleToLongBits(latitude) != Double
			.doubleToLongBits(other.latitude))
		return false;
	if (Double.doubleToLongBits(longitude) != Double
			.doubleToLongBits(other.longitude))
		return false;
	return true;
}


public double aerialDistance(final Position otherPoint) {

    final double lat1 = this.getLatitude();
    final double lng1 = this.getLongitude();

    final double lat2 = otherPoint.getLatitude();
    final double lng2 = otherPoint.getLongitude();

    final double earthRadius = 6371.01;
    final double dLat = Math.toRadians(lat2 - lat1);
    final double dLng = Math.toRadians(lng2 - lng1);
    final double sindLat = Math.sin(dLat / 2);
    final double sindLng = Math.sin(dLng / 2);
    final double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
    final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return earthRadius * c * 1000; // meters
}


public Position addDistanceAcrossLatitude(final double distanceInMeters) {

    final double increment = (distanceInMeters / 10) * TEN_METER_DECIMAL_OFFSET_IN_LATLONG;
    return new Position(this.getLatitude() + increment, this.getLongitude());
}

public Position addDistanceAcrossLongitude(final double distanceInMeters) {
    final double increment = (distanceInMeters / 10) * TEN_METER_DECIMAL_OFFSET_IN_LATLONG;
    return new Position(this.getLatitude(), this.getLongitude() + increment);
}

public Position[] getBoundingSquareLatLongLimits(final double distance){

    final double radius = 6371.01 * 1000; //earth radius

    if (distance < 0d)
        throw new IllegalArgumentException();

    final double radLat = Math.toRadians(latitude);
    final double radLon = Math.toRadians(longitude);

    // angular distance in radians on a great circle
    double radDist = distance / radius;

    double minLat = radLat - radDist;
    double maxLat = radLat + radDist;

    double minLon, maxLon;
    if (minLat > LATITUDE_MIN && maxLat < LATITUDE_MAX) {
        double deltaLon = Math.asin(Math.sin(radDist) /
                Math.cos(radLat));
        minLon = radLon - deltaLon;
        if (minLon < LONGITUDE_MIN) minLon += 2d * Math.PI;
        maxLon = radLon + deltaLon;
        if (maxLon > LONGITUDE_MAX) maxLon -= 2d * Math.PI;
    } else {
        minLat = Math.max(minLat, LATITUDE_MIN);
        maxLat = Math.min(maxLat, LATITUDE_MAX);
        minLon = LONGITUDE_MIN;
        maxLon = LONGITUDE_MAX;
    }

    return new Position[]{
            new Position(Math.toDegrees(minLat),Math.toDegrees(minLon)),
            new Position(Math.toDegrees(maxLat), Math.toDegrees(maxLon)) };
}
}

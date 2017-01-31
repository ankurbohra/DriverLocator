package com.lookup.service.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lookup.service.core.Driver;
import com.lookup.service.core.DriverPositionResponse;
import com.lookup.service.core.Position;
import com.lookup.service.services.DriverLocationUpdate;

public class DriverLocationUpdateServiceImpl implements DriverLocationUpdate {
	private final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

	private final Logger logger = LoggerFactory
			.getLogger(DriverLocationUpdateServiceImpl.class);

	private final Map<String, Position> driverCurrentPositionMap = new ConcurrentHashMap<>(
			75000, 0.75f, 32);

	@Override
	public void upsert(Driver driver, Position position) {
		if (driver != null && position != null) {
			driverCurrentPositionMap.putIfAbsent(driver.getId(), position);
		}

	}

	// We need to improve the time complexity of the below mentioned function.
	// It is O(n).
	@Override
	public List<DriverPositionResponse> getNearByDrivers(
			final Position fromPosition, int radius, int resultLimit) {
		List<DriverPositionResponse> list = new ArrayList<>();
		/*Set<Map.Entry<Driver, Position>> entrySet = driverCurrentPositionMap
				.entrySet();
		Iterator<Map.Entry<Driver, Position>> itr = entrySet.iterator();*/
		DriverPositionResponse driverPositionResponse = null;
		for (Map.Entry<String, Position> entry : driverCurrentPositionMap
				.entrySet()) {
			String driverId = entry.getKey();
			Position pos = entry.getValue();
			// We are calculating the distance based on Haversine Formula
			double distance = fromPosition.aerialDistance(pos);
			int retVal = Double.compare(distance, radius);
			// it means cab is in range
			if (retVal <= 0) {
				driverPositionResponse = new DriverPositionResponse(
						driverId, pos, fromPosition, distance);
				list.add(driverPositionResponse);
			}

		}
		/*
		 * while (itr.hasNext()) { Map.Entry<Driver, Position> entry =
		 * itr.next(); Driver key = entry.getKey(); Position pos =
		 * entry.getValue(); // We are calculating the distance based on
		 * Haversine Formula double distance = fromPosition.aerialDistance(pos);
		 * int retVal = Double.compare(distance, radius); // it means cab is in
		 * range if (retVal <= 0) { driverPositionResponse = new
		 * DriverPositionResponse( key.getId(), pos, fromPosition, distance);
		 * list.add(driverPositionResponse); }
		 * 
		 * }
		 */

		return list;
	}

}

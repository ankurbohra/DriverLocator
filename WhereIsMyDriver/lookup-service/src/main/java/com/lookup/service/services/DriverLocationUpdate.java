package com.lookup.service.services;

import java.util.List;

import com.lookup.service.core.Driver;
import com.lookup.service.core.DriverPositionResponse;
import com.lookup.service.core.Position;

public interface DriverLocationUpdate {
	
	void upsert(Driver driver, Position position);
	List<DriverPositionResponse> getNearByDrivers(final Position fromPosition, int radius, int resultLimit) ;

}

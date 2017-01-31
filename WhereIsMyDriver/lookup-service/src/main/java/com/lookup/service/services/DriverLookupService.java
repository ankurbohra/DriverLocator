package com.lookup.service.services;

import java.util.List;

import com.lookup.service.core.DriverPositionResponse;
import com.lookup.service.core.Position;

public interface DriverLookupService {
	
	List<DriverPositionResponse> getDrivers(final Position fromPosition, int radius, int resultLimit) ;

}

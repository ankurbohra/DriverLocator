package com.lookup.service.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.lookup.service.core.DriverPositionResponse;
import com.lookup.service.core.Position;
import com.lookup.service.services.DriverLocationUpdate;
import com.lookup.service.services.DriverLookupService;

@Named
public class DriverLookupServiceImpl implements DriverLookupService {

	private final DriverLocationUpdate driverLocationUpdateService;

    @Inject
    public DriverLookupServiceImpl(final DriverLocationUpdate driverLocationUpdateService) {

        this.driverLocationUpdateService = driverLocationUpdateService;
    }

	@Override
	public List<DriverPositionResponse> getDrivers(Position fromPosition,
			int radius, int resultLimit) {
		return driverLocationUpdateService.getNearByDrivers(fromPosition, radius, resultLimit);
	}

}

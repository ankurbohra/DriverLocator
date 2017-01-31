package com.lookup.service.impl.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

import com.lookup.service.core.Position;
import com.lookup.service.services.DriverLocationUpdate;
import com.lookup.service.services.impl.DriverLookupServiceImpl;


public class DriverLookupServiceImplTest {

    @Test
    public void checkIfDriverLocationUpdateserviceIsInvoked() throws Exception {

        final DriverLocationUpdate driverLocationUpdate = mock(DriverLocationUpdate.class);
        DriverLookupServiceImpl driverLookupService = new DriverLookupServiceImpl(driverLocationUpdate);

        final Position fromPosition = new Position(20, 20);
        driverLookupService.getDrivers(fromPosition, 10, 200);

        verify(driverLocationUpdate, times(1)).getNearByDrivers(fromPosition, 10, 200);
        verifyNoMoreInteractions(driverLocationUpdate);
    }
}
package com.lookup.service.resource.test;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.lookup.service.core.Driver;
import com.lookup.service.core.Position;
import com.lookup.service.exception.InvalidDriverIdException;
import com.lookup.service.resources.DriverController;
import com.lookup.service.services.DriverLocationUpdate;
import com.lookup.service.services.DriverLookupService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


public class DriverControllerTest {
	DriverController driverController;
    DriverLookupService driverLookupService;
    private DriverLocationUpdate driverLocationUpdate;

    @Before
    public void setup() {
        driverLookupService = mock(DriverLookupService.class);
        driverLocationUpdate = mock(DriverLocationUpdate.class);
        driverController = new DriverController(driverLookupService, driverLocationUpdate);
    }

    @Test
    public void shouldReturnSuccessWhenOnSuccessfulUpdate() throws InvalidDriverIdException {
        final Position position = new Position(-60, 30, 0);
        final Response response = driverController.updatePosition(Integer.toString(3), new Position(-60, 30, 0));

        assertThat(response.getStatus(), is(200));
        assertThat(response.getEntity(), is(nullValue()));
        verify(driverLocationUpdate, times(1)).upsert(new Driver(Integer.toString(3)), position);
        verifyNoMoreInteractions(driverLocationUpdate);
    }

}

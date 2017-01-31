package com.lookup.service.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.lookup.service.client.DriverPositionStatus;
import com.lookup.service.client.DriverSearchResponse;
import com.lookup.service.core.Driver;
import com.lookup.service.core.DriverPositionResponse;
import com.lookup.service.core.Position;
import com.lookup.service.exception.GenericRunTimeException;
import com.lookup.service.exception.InvalidDriverIdException;
import com.lookup.service.exception.MissingPositionException;
import com.lookup.service.services.DriverLocationUpdate;
import com.lookup.service.services.DriverLookupService;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DriverController {
	final static private Logger logger = LoggerFactory.getLogger(DriverController.class);

    private static final int DRIVER_ID= 1;
    private static final int DRIVER_ID_END = 50000;

    final private DriverLookupService driverLookupService;
    final private DriverLocationUpdate driverLocationUpdate;

    private final static String LAT_IS_NULL = "Latitude is null";
    private final static String LONG_IS_NULL = "Longitude is null";

    @Inject
    public DriverController(final DriverLookupService driverLookupService,
                                    final DriverLocationUpdate driverLocationUpdate) {

        this.driverLookupService = driverLookupService;
        this.driverLocationUpdate = driverLocationUpdate;
    }
    
    @PUT
    @Path("/driver/{driverid}/location")
    @Timed(name = "updatePosition.time")
    @ExceptionMetered (name = "updatePosition.exceptions")
    public Response updatePosition(@PathParam("driverid") final String driverId,
                                   @Valid final Position currentPosition) throws InvalidDriverIdException {

        try {
            validateDriverId(driverId);
            driverLocationUpdate.upsert(new Driver(driverId), currentPosition);
        } catch (RuntimeException e) {
            throw new GenericRunTimeException(e);
        }
        return Response.ok().build();
    }

    private void validateDriverId(final String driverId) throws InvalidDriverIdException {

        try {
            final Integer id = Integer.valueOf(driverId);
            if (id < DRIVER_ID || id > DRIVER_ID_END)
                throw new InvalidDriverIdException("Invalid Driver Id " + id);
        } catch (NumberFormatException e) {
            throw new InvalidDriverIdException("Invalid Driver Id " + driverId);
        }
    }
    
    @GET
    @Path("/drivers")
    @Timed(name = "locateDriver.time")
    @ExceptionMetered(name = "locateDriver.exceptions")
    public Response locateDrivers(@QueryParam("latitude") @Max(90) @Min(-90) final Double latitude,
                                  @QueryParam("longitude") @Max(180) @Min(-180) final Double longitude,
                                  @QueryParam("radius") @DefaultValue("500") @Min(100) @Max(5000) final int radius,
                                  @QueryParam("limit") @DefaultValue("10") @Min(1) @Max(100) final int limit) throws MissingPositionException {

        try {

        	checkIfLatLongPositionAreNotNull(latitude, longitude); // GET request and mandatory query-param, huh??

            final List<DriverPositionResponse> driverPositionResult =
                    driverLookupService.getDrivers(new Position(latitude, longitude), radius, limit);

            final DriverSearchResponse driverSearchResponse = mapResponseToClientAPI(driverPositionResult);

            return Response.ok().entity(driverSearchResponse).build();

        } catch (RuntimeException e) {
            throw new GenericRunTimeException(e);
        }
    }
    private void checkIfLatLongPositionAreNotNull(Double latitude, Double longitude) throws MissingPositionException {

        final boolean latNull = latitude == null, longNull = longitude == null;
        if (latNull || longNull) {
              throw new MissingPositionException("Invalid Latitude and Longitude position.");
        }
    }
    
    private DriverSearchResponse mapResponseToClientAPI(List<DriverPositionResponse> driverPositionResults) {

        final DriverSearchResponse driverSearchResponse = new DriverSearchResponse();

        driverPositionResults.forEach(new Consumer<DriverPositionResponse>() {
            @Override
            public void accept(DriverPositionResponse driverPositionStatus) {
                driverSearchResponse.getDriverStatusList().
                        add(new DriverPositionStatus(
                                Integer.valueOf(driverPositionStatus.getDriverId()),
                                driverPositionStatus.getOwnPosition().getLatitude(),
                                driverPositionStatus.getOwnPosition().getLongitude(),
                                driverPositionStatus.getDistance()));

            }
        });

        return driverSearchResponse;
    }
}

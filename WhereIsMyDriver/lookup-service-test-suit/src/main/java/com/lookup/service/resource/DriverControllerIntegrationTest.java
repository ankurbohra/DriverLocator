package com.lookup.service.resource;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lookup.service.client.DriverSearchResponse;
import com.lookup.service.client.Position;
import com.lookup.service.config.TestConfig;
import com.lookup.service.exception.ErrorMessage;
import com.lookup.service.resources.DriverController;
import com.lookup.service.server.AppConfig;
import com.lookup.service.util.DIResourceTestRule;
import com.lookup.service.util.MessageTestUtil;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TestConfig.class})
@ActiveProfiles(profiles = "mockDriverPosition")
public class DriverControllerIntegrationTest {
	private final static String BASE_URL_FOR_DRIVER_UPDATE = "/v1/driver/%s/location";
    private final static String BASE_URL_FOR_GET_DRIVERS = "/v1/drivers";

    private final static Position VALID_SAMPLE_POSITION = new Position(15.12d, -20.323d, 0);
    private final static Position INVALID_LONG_SAMPLE_POSITION = new Position(80.0d, -181d, 13);
    private final static Position INVALID_LAT_SAMPLE_POSITION = new Position(91.0d, -81d, 13);
    private final MessageTestUtil messageTestUtil = new MessageTestUtil();

    @Inject
    DriverController apiController; // The real controller

    @Inject
    @Rule
    public DIResourceTestRule resourceTestRule;

    @BeforeClass
    public static void once() {

    }


    // DRIVER UPDATE API testcases
    @Test
    public void shouldReturn404ForDriverUpdateLocationAPIWhenInvalidDriverIdProvided() {

        final Client client = resourceTestRule.client();
        final String driverURL = String.format(BASE_URL_FOR_DRIVER_UPDATE, -100);
        final Response response = client.target(driverURL).request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(VALID_SAMPLE_POSITION));

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void shouldReturn422ForUpdateLocationAPIWhenInvalidLongitudeRangeIsProvided() {

        final Client client = resourceTestRule.client();
        final String driverURL = String.format(BASE_URL_FOR_DRIVER_UPDATE, 100);
        final Response response = client.target(driverURL).request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(INVALID_LONG_SAMPLE_POSITION));

        assertThat(response.getStatus(), is(422));

        // check for the message in the response
        final ErrorMessage errorMsg = response.readEntity(ErrorMessage.class);
        assertThat(messageTestUtil.checkIfErrorMessageEquals(errorMsg, "longitude"), is(true));
    }

    @Test
    public void shouldReturn422ForUpdateLocationAPIWhenInvalidLatitudeRangeIsProvided() {

        final Client client = resourceTestRule.client();
        final String driverURL = String.format(BASE_URL_FOR_DRIVER_UPDATE, 100);

        final Response response = client.target(driverURL).request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(INVALID_LAT_SAMPLE_POSITION));

        assertThat(response.getStatus(), is(422));

        // check for the message in the response
        final ErrorMessage errorMsg = response.readEntity(ErrorMessage.class);
        assertThat(messageTestUtil.checkIfErrorMessageEquals(errorMsg, "latitude"), is(true));
    }

    // GET DRIVER API usecases
    @Test
    public void shouldReturn400WhenInvalidLongitudeRangeIsProvidedForGetDriverAPIs() {

        final Client client = resourceTestRule.client();
        final Response response = client.target(BASE_URL_FOR_GET_DRIVERS).
                queryParam("latitude", 12.0d).queryParam("longitude", 230.0d).request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus(), is(400));
        // check  the presence of longitude in the response.
        final ErrorMessage errorMsg = response.readEntity(ErrorMessage.class);
        assertThat(messageTestUtil.checkIfErrorMessageEquals(errorMsg, "longitude"), is(true));
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidLatitudeIsProvidedForGetDriverAPI() {
        final Client client = resourceTestRule.client();
        final Response response = client.target(BASE_URL_FOR_GET_DRIVERS).
                queryParam("latitude", 112.0d).queryParam("longitude", 130.0d).request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus(), is(400));
        // check for the message in the response
        final ErrorMessage errorMsg = response.readEntity(ErrorMessage.class);
        assertThat(messageTestUtil.checkIfErrorMessageEquals(errorMsg, "latitude"), is(true));
    }

    @Test
    public void shouldReturn400WhenLatitudeLongitudeAreMissingForGetDriverAPI() throws JsonProcessingException {
        final Client client = resourceTestRule.client();
        final Response response = client.target(BASE_URL_FOR_GET_DRIVERS).
                request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void shouldThrow400WhenRadiusIsnotBetweenValidRangeOf100_5000ForGetDriverAPI() {

        final Client client = resourceTestRule.client();
        final Response response = client.target(BASE_URL_FOR_GET_DRIVERS).
                queryParam("latitude", 10.0d).queryParam("longitude", 10.0d).
                queryParam("radius", 6000).
                request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void shouldThrow400WhenLimitIsNotBetween1And100ForGetDriverAPI() {

        final Client client = resourceTestRule.client();
        final Response response = client.target(BASE_URL_FOR_GET_DRIVERS).
                queryParam("latitude", 10.0d).queryParam("longitude", 10.0d).
                queryParam("radius", 600).queryParam("limit", 1000).
                request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus(), is(400));
    }

    // Integration test, involving Posting the driver positions and getting the drivers too

    @Test
    public void shouldUpdateDriverLocationsAndReturnTheResultViaGetDriversAPI() {

        final Client client = resourceTestRule.client();
        final int DRIVER_COUNT = 100;

        for (int i = 1; i <= DRIVER_COUNT; ++i) {

            final String driverURL = String.format(BASE_URL_FOR_DRIVER_UPDATE, Integer.toString(i));
            final Response response = client.target(driverURL).request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.json(VALID_SAMPLE_POSITION));

            assertThat(response.getStatus(), is(200));
        }

        final Response driverGetResponse = client.target(BASE_URL_FOR_GET_DRIVERS).
                queryParam("latitude", VALID_SAMPLE_POSITION.getLatitude()).queryParam("longitude", VALID_SAMPLE_POSITION.getLongitude()).
                queryParam("radius", 600).queryParam("limit", 100).
                request(MediaType.APPLICATION_JSON_TYPE).get();

        assertThat(driverGetResponse.getStatus(), is(200));

        final DriverSearchResponse driverSearchResponse = driverGetResponse.readEntity(DriverSearchResponse.class);

        assertThat(driverSearchResponse.getDriverStatusList(), is(notNullValue()));
        assertThat(driverSearchResponse.getDriverStatusList().size(), is(DRIVER_COUNT));
    }

}

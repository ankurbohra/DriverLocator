package com.lookup.service.config;

import javax.inject.Named;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lookup.service.core.Driver;
import com.lookup.service.core.Position;
import com.lookup.service.services.DriverLocationUpdate;
import com.lookup.service.services.DriverLookupService;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(value = {"com.lookup.service.util"})
public class TestConfig {
	@Profile("mockDriverPosition")
    @Bean(name = "MockedDriverLookupService")
    public DriverLookupService getMockedDriverLookupService() {
        return mock(DriverLookupService.class);
    }

    @Named("MockedgeoDriverLocationUpdateService")
    @Bean
    @Profile("mockDriverPosition")
    public DriverLocationUpdate getDrivers() {
        final DriverLocationUpdate mock = mock(DriverLocationUpdate.class);
        doThrow(new NullPointerException()).when(mock).upsert((Driver)any(), (Position)any());
        return mock;
    }

}

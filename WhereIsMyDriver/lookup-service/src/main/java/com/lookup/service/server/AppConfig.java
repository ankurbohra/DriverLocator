package com.lookup.service.server;

import javax.inject.Named;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lookup.service.services.DriverLocationUpdate;
import com.lookup.service.services.impl.DriverLocationUpdateServiceImpl;

@Configuration
@ComponentScan(value = { "com.lookup.service" })
public class AppConfig {

	@Named("driverLocationUpdate")
	@Bean
	@Profile("!mock")
	public DriverLocationUpdate getGeoDataStore() {
		return new DriverLocationUpdateServiceImpl();
	}
}

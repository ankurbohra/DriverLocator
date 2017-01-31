package com.lookup.service.server;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WhereIsMyDriverMain extends Application<Configuration> {

	@Override
	public void run(Configuration configuration, Environment environment)
			throws Exception {

	}

	public void initialize(Bootstrap<Configuration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(new ServiceInitBundle());
	}

	public static void main(String[] args) throws Exception {
		new WhereIsMyDriverMain().run(args);
	}
}
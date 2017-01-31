package com.lookup.service.server;

import com.codahale.metrics.SharedMetricRegistries;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.Path;
import javax.ws.rs.ext.ExceptionMapper;

public class ServiceInitBundle implements ConfiguredBundle<Configuration> {

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {

        // wire up the JMX metrics, MUST for monitoring the app performance
        SharedMetricRegistries.add("WhereIsMyDriverMetrics", environment.metrics());

        // Spring wiring up
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Adding all created resources
        context.getBeansWithAnnotation(Path.class).forEach((name, res) -> environment.jersey().register(res));
        //TODO: Health Check
        // Adding health checks
        
        // add shutdown hook -- this is done to ensure that jetty stops after spring
        environment.lifecycle().manage(new ContextManagement(context));

        context.getBeansOfType(ExceptionMapper.class).forEach((name, res) -> environment.jersey().register(res));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
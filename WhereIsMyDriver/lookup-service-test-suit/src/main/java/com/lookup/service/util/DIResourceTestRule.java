package com.lookup.service.util;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;
import java.util.Map;
@Named
public class DIResourceTestRule {
	
	private ResourceTestRule resourceTestRule;

    @Inject
    public DIResourceTestRule(ApplicationContext applicationContext, ExceptionMapper exceptionMappers) {

        final Map<String, Object> resources = applicationContext.getBeansWithAnnotation(Path.class);
        final ResourceTestRule.Builder builder = new ResourceTestRule.Builder();
        resources.values().forEach(builder::addResource);
        exceptionMappers.forEach(builder::addProvider);
        this.resourceTestRule = builder.build();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return resourceTestRule.apply(base, description);
    }

    public Client client() {
        return this.resourceTestRule.client();
    }

}

package com.lookup.service.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

import io.dropwizard.lifecycle.Managed;

public class ContextManagement implements Managed {

    private static final Logger logger = LoggerFactory.getLogger(ContextManagement.class);
    private final AbstractApplicationContext context;

    public ContextManagement(AbstractApplicationContext context) {
        this.context = context;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        logger.warn("Now stopping spring context");
        context.close();
        logger.warn("Spring context stopped");
    }
}
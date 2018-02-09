package com.emcloud.uaa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;

import java.util.Set;

public class RefreshEventListener implements ApplicationListener<EnvironmentChangeEvent> {
    Logger logger = LoggerFactory.getLogger( RefreshEventListener.class );
    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        logger.info(  "refresh trigger" );
        Set<String> keys = event.getKeys();
        for ( String key :keys ) {
            logger.info( "key: " + key );
        }
    }
}

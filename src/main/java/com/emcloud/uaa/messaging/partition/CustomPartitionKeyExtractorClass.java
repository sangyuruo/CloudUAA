package com.emcloud.uaa.messaging.partition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;

public class CustomPartitionKeyExtractorClass implements PartitionKeyExtractorStrategy {
    private final Logger log = LoggerFactory.getLogger(CustomPartitionKeyExtractorClass.class);
    @Override
    public Object extractKey(Message<?> message) {
        String msg = (String) message.getPayload();
        log.info("msg hashcode is " + msg.hashCode() );
        return msg.hashCode();
    }
}

package com.emcloud.uaa.messaging.partition;

import com.emcloud.uaa.messaging.MessageCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;

public class CustomPartitionKeyExtractorClass implements PartitionKeyExtractorStrategy {
    private final Logger log = LoggerFactory.getLogger(CustomPartitionKeyExtractorClass.class);
    @Override
    public Object extractKey(Message<?> message) {
        Integer msgIndex = (Integer) message.getHeaders().get(MessageCenter.X_MSG_HEADER);
        log.info("msg index is " + msgIndex );
        return msgIndex;
    }
}

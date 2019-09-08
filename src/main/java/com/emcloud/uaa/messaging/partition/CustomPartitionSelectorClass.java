package com.emcloud.uaa.messaging.partition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;

public class CustomPartitionSelectorClass implements PartitionSelectorStrategy {
    private final Logger log = LoggerFactory.getLogger(CustomPartitionSelectorClass.class);
    @Override
    public int selectPartition(Object o, int i) {
        log.info("object is {} , i is {} " , o , i );
        return o.hashCode() % i;
    }
}

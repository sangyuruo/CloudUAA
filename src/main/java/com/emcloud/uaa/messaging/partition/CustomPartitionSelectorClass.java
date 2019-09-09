package com.emcloud.uaa.messaging.partition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;

public class CustomPartitionSelectorClass implements PartitionSelectorStrategy {
    private final Logger log = LoggerFactory.getLogger(CustomPartitionSelectorClass.class);
    @Override
    /**
     * o: partition key
     * i: partition count
     */
    public int selectPartition(Object key, int partitionCount) {
        log.info("object is {} , i is {} " , key , partitionCount );
        int partitionIndex = key.hashCode() % partitionCount;
        log.info("partitionIndex is {} " ,  partitionIndex );
        return partitionIndex;
    }
}

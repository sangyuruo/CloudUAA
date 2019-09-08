package com.emcloud.uaa.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;

public class ConsumerService {
    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @StreamListener(ConsumerChannel.CHANNEL1)
    public void consume(String msg) {
        log.info("Received message channel1: {}.", msg);
    }

    @StreamListener(ConsumerChannel.CHANNEL2)
    public void consume2(String msg) {
        log.info("Received message channel2 : {}.", msg);
    }
}

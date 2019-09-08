package com.emcloud.uaa.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@EnableBinding
@Service
public class ProducerChannel {
    @Autowired
    private MessageCenter messageCenter;
    private final Logger log = LoggerFactory.getLogger(ConsumerChannel.class);

    public void output(Message<String> message) {
        messageCenter.output().send(message);
        log.info("send msg: " + message.getPayload());
    }
}

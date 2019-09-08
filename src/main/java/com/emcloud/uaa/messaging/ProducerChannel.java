package com.emcloud.uaa.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerChannel {
    String CHANNEL = "output";

    @Output
    MessageChannel output();
}

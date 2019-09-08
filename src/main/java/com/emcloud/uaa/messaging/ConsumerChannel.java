package com.emcloud.uaa.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerChannel {
    String CHANNEL1 = "input1";
    String CHANNEL2 = "input2";

    @Input
    SubscribableChannel input1();

    @Input
    SubscribableChannel input2();
}

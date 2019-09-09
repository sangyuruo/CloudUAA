package com.emcloud.uaa.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MessageCenter {
    String X_MSG_HEADER = "x-msg";
    String INPUT1 = "input1";
    String INPUT2 = "input2";
    String INPUT3 = "input3";
    String INPUT4 = "input4";
    String INPUT5 = "input5";
    String OUTPUT = "output";

    @Input(MessageCenter.INPUT1)
    SubscribableChannel input1();

    @Input(MessageCenter.INPUT2)
    SubscribableChannel input2();

    @Input(MessageCenter.INPUT3)
    SubscribableChannel input3();

    @Input(MessageCenter.INPUT4)
    SubscribableChannel input4();

    @Input(MessageCenter.INPUT5)
    SubscribableChannel input5();

    @Output(MessageCenter.OUTPUT)
    MessageChannel output();
}

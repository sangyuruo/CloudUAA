package com.emcloud.uaa.config;

import com.emcloud.uaa.messaging.ConsumerChannel;
import com.emcloud.uaa.messaging.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(value = {Source.class, ProducerChannel.class, ConsumerChannel.class})
public class MessagingConfiguration {
}

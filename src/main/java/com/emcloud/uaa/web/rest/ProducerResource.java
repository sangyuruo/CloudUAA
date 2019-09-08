package com.emcloud.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.messaging.ProducerChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  测试 kafka 消息发送
 */
@RestController
@RequestMapping("/api")
public class ProducerResource {
    private MessageChannel channel;
    public ProducerResource(ProducerChannel channel) {
        this.channel = channel.output();
    }

    @GetMapping("/demo/kafka.send/{count}")
    @Timed
    public void produce(@PathVariable int count) {
        while(count > 0) {
            channel.send(MessageBuilder.withPayload(("Hello world!: " + count)).build());
            count--;
        }
    }
}

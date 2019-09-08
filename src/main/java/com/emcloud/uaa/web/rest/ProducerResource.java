package com.emcloud.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.uaa.messaging.ProducerChannel;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * 测试 kafka 消息发送
 */
@RestController
@RequestMapping("/api")
public class ProducerResource {
    @Autowired
    ProducerChannel producerChannel;

    @GetMapping("/demo/kafka.send/{count}")
    @Timed
    public void produce(@PathVariable int count) {
        String time = DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date());
        while (count > 0) {
            String msg = time + " Hello world!: " + count;
            producerChannel.output(MessageBuilder.withPayload(msg).build());
            count--;
        }
    }
}

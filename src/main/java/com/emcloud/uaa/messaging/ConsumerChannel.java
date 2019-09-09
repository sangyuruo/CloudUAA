package com.emcloud.uaa.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@EnableBinding(MessageCenter.class)
@Service
public class ConsumerChannel {
    private final Logger log = LoggerFactory.getLogger(ConsumerChannel.class);
    String MSG_FORMAT = "Received message channel {}: {}.";

    @StreamListener(MessageCenter.INPUT1)
    public void consume(Message<String> msg) {
        log.info(MSG_FORMAT, 1,  msg.getPayload());
        printKafkaInfo(1, msg);
        commit(msg);
    }

    @StreamListener(MessageCenter.INPUT2)
    public void consume2(Message<String> msg) {
        log.info(MSG_FORMAT, 2,  msg.getPayload());
        printKafkaInfo(2, msg);
        commit(msg);
    }

    @StreamListener(MessageCenter.INPUT3)
    public void consume3(Message<String> msg) {
        log.info(MSG_FORMAT, 3,  msg.getPayload());
        printKafkaInfo(3, msg);
        commit(msg);
    }

    @StreamListener(MessageCenter.INPUT4)
    public void consume4(Message<String> msg) {
        log.info(MSG_FORMAT, 4,  msg.getPayload());
        printKafkaInfo(4, msg);
        commit(msg);
    }

    @StreamListener(MessageCenter.INPUT5)
    public void consume5(Message<String> msg) {
        log.info(MSG_FORMAT, 5,  msg.getPayload());
        printKafkaInfo(5, msg);
        commit(msg);
    }

    private void printKafkaInfo(int channelId , Message<String> msg){
        Integer partitionId = msg.getHeaders().get(KafkaHeaders.PARTITION_ID, Integer.class);
        log.info("current[{}] partition id is {}" , channelId , partitionId);
        Integer recvPartitionId = msg.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class);
        log.info("current[{}] recv partition id is {}" ,channelId ,  recvPartitionId);
        Object offset = msg.getHeaders().get(KafkaHeaders.OFFSET);
        log.info("current[{}] offset is {}" , channelId , offset);
        Object msgKey = msg.getHeaders().get(KafkaHeaders.MESSAGE_KEY);
        log.info("current[{}] msg key is {}" , channelId , msgKey);
        Object recvMsgKey = msg.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
        log.info("current[{}] recv msg key is {}" , channelId , recvMsgKey);
    }

    private void commit(Message<String> msg){
        Acknowledgment acknowledgment = msg.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            System.out.println("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }
}

package com.emcloud.uaa.config;

import com.emcloud.uaa.messaging.partition.CustomPartitionKeyExtractorClass;
import com.emcloud.uaa.messaging.partition.CustomPartitionSelectorClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableBinding(value = {Source.class, ProducerChannel.class, ConsumerChannel.class})
@Configuration
public class MessagingConfiguration {
    @Bean
    public CustomPartitionKeyExtractorClass customPartitionKeyExtractor() {
        return new CustomPartitionKeyExtractorClass();
    }

    @Bean
    public CustomPartitionSelectorClass customPartitionSelectorClass() {
        return new CustomPartitionSelectorClass();
    }
}

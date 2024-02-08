package com.kp2s.sakapfet.kafka.configuration;

import com.kp2s.sakapfet.message.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

public class ReactiveKafkaConsumerConfig {

    @Bean
    public ReceiverOptions<String, MessageDTO> kafkaReceiverOptions(@Value(value = "${FAKE_CONSUMER_DTO_TOPIC}") String topic, KafkaProperties kafkaProperties) {
        //ReceiverOptions<String, MessageDTO> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        ReceiverOptions<String, MessageDTO> basicReceiverOptions = ReceiverOptions.create();
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

/*    @Bean
    public ReactiveKafkaConsumerTemplate<String, MessageDTO> reactiveKafkaConsumerTemplate(ReceiverOptions<String, MessageDTO> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, MessageDTO>(kafkaReceiverOptions);
    }*/


}

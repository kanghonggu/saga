package com.practice.transfer.configuration;

import com.practice.transfer.dto.TransferRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguraion {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String producerServer;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String consumerServer;


    @Bean
    public ProducerFactory<String, TransferRequest> producerFactory() {

        Map<String,Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerServer);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(configs);
    }

    @Bean
    public KafkaTemplate<String, TransferRequest> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, TransferRequest> stockChangeConsumer() {

        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerServer);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-01");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(TransferRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransferRequest> stockChangeListener() {
        ConcurrentKafkaListenerContainerFactory<String, TransferRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stockChangeConsumer());
        return factory;
    }

    @Bean
    public StringJsonMessageConverter jsonMessageConverter() {
        return new StringJsonMessageConverter();
    }
}

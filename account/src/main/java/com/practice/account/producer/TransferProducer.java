package com.practice.account.producer;

import com.practice.account.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferProducer {
    private final KafkaTemplate kafkaTemplate;

    public void transfer(TransferRequest transferRequest){
        kafkaTemplate.send("transfer", transferRequest);
    }
}

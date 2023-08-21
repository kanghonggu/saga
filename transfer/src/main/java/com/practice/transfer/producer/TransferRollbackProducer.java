package com.practice.transfer.producer;

import com.practice.transfer.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferRollbackProducer {

    private final KafkaTemplate kafkaTemplate;

    public void rollbackCreatedOrder(TransferRequest transferRequest){
        kafkaTemplate.send("transfer-rollback", transferRequest);
    }
}

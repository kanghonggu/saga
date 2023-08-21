package com.practice.transfer.consumer;

import com.practice.transfer.dto.TransferRequest;
import com.practice.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferConsumer {

    private final TransferService transferService;

    @KafkaListener(topics = "transfer", groupId = "group-01")
    public void transfer(TransferRequest transferRequest){
        try {
            transferService.transfer(transferRequest);
        }catch (Exception e){
            transferService.transferRollback(transferRequest);
        }
    }
}

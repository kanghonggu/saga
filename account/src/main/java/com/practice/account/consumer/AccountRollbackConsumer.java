package com.practice.account.consumer;


import com.practice.account.dto.TransferRequest;
import com.practice.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRollbackConsumer {

    private final AccountService accountService;

    @KafkaListener(topics = "transfer-rollback", groupId = "group-01")
    public void rollbackOrder(TransferRequest transferRequest){
        accountService.rollbackAccount(transferRequest);
    }
}

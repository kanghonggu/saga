package com.practice.transfer.service;

import com.practice.transfer.domain.AccountHistory;
import com.practice.transfer.dto.TransferRequest;
import com.practice.transfer.producer.TransferRollbackProducer;
import com.practice.transfer.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * The type Transfer service.
 */
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountHistoryRepository accountHistoryRepository;
    private final TransferRollbackProducer transferRollbackProducer;

    public void transfer(TransferRequest transferRequest){

        accountHistoryRepository.save(AccountHistory.builder()
                .accountId(transferRequest.getAccountId())
                .transferAmt(transferRequest.getAmt())
                .build()
        );

    }

    public void transferRollback(TransferRequest transferRequest){
        transferRollbackProducer.rollbackCreatedOrder(transferRequest);
    }
}

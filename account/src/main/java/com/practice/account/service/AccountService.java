package com.practice.account.service;

import com.practice.account.domain.Account;
import com.practice.account.dto.AccountDto;
import com.practice.account.dto.TransferRequest;
import com.practice.account.mapper.AccountMapper;
import com.practice.account.producer.TransferProducer;
import com.practice.account.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * The type Account service.
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransferProducer transferProducer;
    private final AccountMapper accountMapper;
    /**
     * 계좌 만들기
     *
     * @return the long
     */
    public Long makeAccount(){
        return accountRepository.save(
                Account.builder()
                        .amt(BigDecimal.ZERO)
                        .build()
        ).getAccountId();
    }

    /**
     * 계좌이체 메인 서비스
     *
     * @param accountId the account id
     * @param amt       the amt
     */
    public void transferAccount(Long accountId, BigDecimal amt){
        Account account = getAccountAmt(accountId);

        if(amt.add(account.getAmt()).compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("잔액이 부족합니다.");
        }

        transferProducer.transfer(
                TransferRequest.builder()
                        .accountId(accountId)
                        .amt(amt)
                .build());

        updateAccount(accountMapper.toDto(account), amt);

    }

    /**
     * 계좌 정보 불러오기
     *
     * @param accountId the account id
     * @return the account
     */
    public Account getAccountAmt(Long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * 잔액 업데이트
     *
     * @param accountDto the account
     * @param amt     the amt
     */
    public void updateAccount(AccountDto accountDto, BigDecimal amt){


        accountDto.updateAmt(amt);
        accountRepository.save(accountMapper.toEntity(accountDto));
    }

    public void rollbackAccount(TransferRequest transferRequest){
        updateAccount(
                  accountMapper.toDto(getAccountAmt(transferRequest.getAccountId()))
                , transferRequest.getAmt().multiply(BigDecimal.valueOf(-1))
        );
    }

}

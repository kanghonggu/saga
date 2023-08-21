package com.practice.account.mapper;

import com.practice.account.domain.Account;
import com.practice.account.dto.AccountDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class AccountMapper {
    public AccountDto toDto(Account account) {
        if(account == null){
            return null;
        }

        return AccountDto
                .builder()
                .accountId(account.getAccountId())
                .amt(account.getAmt())
                .createdDt(account.getCreatedDt())
                .modifiedDt(account.getModifiedDt())
                .build();
    }

    public Account toEntity(AccountDto accountDto) {
        if(accountDto == null){
            return null;
        }

        return Account
                .builder()
                .accountId(accountDto.getAccountId())
                .amt(accountDto.getAmt())
                .createdDt(accountDto.getCreatedDt())
                .modifiedDt(accountDto.getModifiedDt())
                .build();
    }

}

package com.practice.account.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private Long accountId;
    private BigDecimal amt;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;

    public void updateAmt(BigDecimal amt){
        this.amt = amt.add(this.amt);
    }
}

package com.practice.account.controller;

import com.practice.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final AccountService accountService;

    @GetMapping("/make")
    public String makeAccount(){
        String result = "SUCCESS";

        try {
            result = result + accountService.makeAccount().toString();
        }catch (Exception e){
            log.info(e.getMessage());
            result = "FAIL";
        }
        return result;
    }


    @GetMapping("/transfer")
    public String transfer(){
        String result = "SUCCESS";

        try {
            accountService.transferAccount(1L, BigDecimal.TEN);
        }catch (Exception e){
            log.info(e.getMessage());
            result = "FAIL";
        }
        return result;
    }



}

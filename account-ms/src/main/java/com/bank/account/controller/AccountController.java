package com.bank.account.controller;

import com.bank.account.model.entity.AccountStatementEntity;
import com.bank.account.service.AccountManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Api(
        value = "Account statements service",
        tags = "Account statements service"
)
@Slf4j
@Validated
@RestController
@RequestMapping
public class AccountController {

    private final AccountManager msAccountManager;

    @Autowired
    public AccountController(AccountManager msAccountManager) {
        this.msAccountManager = msAccountManager;
    }


    @ApiOperation(
            value = "get statements by account id",
            notes = "get statements by account id"
    )
    @GetMapping(path = "/account/{accountId}/statement", produces = "application/json")
    public ResponseEntity<List<AccountStatementEntity>> get(
            @PathVariable String accountId,
            @ApiParam(example = "2020-09-23") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dateFrom,
            @ApiParam(example = "2020-09-23") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) Double amountFrom,
            @RequestParam(required = false) Double amountTo
    ) {
        return new ResponseEntity<>(msAccountManager.getAccountStatements(accountId, dateFrom, dateTo, amountFrom, amountTo), HttpStatus.OK);
    }

}


package com.bank.account.controller;

import com.bank.account.exception.UnauthorizedRequestException;
import com.bank.account.model.request.RetrieveStatementsRequest;
import com.bank.account.model.response.AccountStatementResponse;
import com.bank.account.service.AccountManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.util.Objects.isNull;

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
    public ResponseEntity<AccountStatementResponse> get(
            @PathVariable String accountId,
            @ApiParam(example = "2012-09-23") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dateFrom,
            @ApiParam(example = "2020-09-23") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) Double amountFrom,
            @RequestParam(required = false) Double amountTo
    ) {
        RetrieveStatementsRequest request;
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (!isNull(auth)) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                request = new RetrieveStatementsRequest(accountId, dateFrom, dateTo, amountFrom, amountTo);
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                if (!isNull(dateFrom) || !isNull(dateTo) || !isNull(amountFrom) || !isNull(amountTo)) {
                    throw new UnauthorizedRequestException("current user is not allowed to pass any amount or date filters");
                } else {
                    request = new RetrieveStatementsRequest(accountId);
                }
            } else {
                throw new UnauthorizedRequestException("current user is not authorized");
            }
        } else {
            throw new UnauthorizedRequestException("authorization should be provided with the request");
        }

        return new ResponseEntity<>(msAccountManager.getAccountStatements(request), HttpStatus.OK);
    }
}


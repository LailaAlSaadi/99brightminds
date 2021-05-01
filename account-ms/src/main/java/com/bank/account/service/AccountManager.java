package com.bank.account.service;

import com.bank.account.model.entity.AccountStatementEntity;

import java.time.LocalDate;
import java.util.List;

public interface AccountManager {

    List<AccountStatementEntity> getAccountStatements(String accountId, LocalDate dateFrom, LocalDate dateTo, Double amountFrom, Double amountTo);

}

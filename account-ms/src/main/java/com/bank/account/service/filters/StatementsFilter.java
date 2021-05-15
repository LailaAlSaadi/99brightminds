package com.bank.account.service.filters;

import com.bank.account.model.entity.AccountStatementEntity;

import java.util.List;

public interface StatementsFilter {

    List<AccountStatementEntity> filter(List<AccountStatementEntity> statements);
}

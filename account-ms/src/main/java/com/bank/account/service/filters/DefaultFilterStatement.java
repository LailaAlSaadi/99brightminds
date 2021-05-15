package com.bank.account.service.filters;

import com.bank.account.model.entity.AccountStatementEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultFilterStatement implements StatementsFilter {

    @Override
    public List<AccountStatementEntity> filter(List<AccountStatementEntity> statements) {
        return statements.stream()
                .filter(statement -> statement.getDateField()
                        .isAfter(LocalDate.now().minusMonths(3)))
                .collect(Collectors.toList());
    }
}

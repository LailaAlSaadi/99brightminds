package com.bank.account.service.statement;

import com.bank.account.model.entity.AccountStatementEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateFilterStatement implements StatementsFilter {

    private final LocalDate from;
    private final LocalDate to;

    public DateFilterStatement(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<AccountStatementEntity> filter(List<AccountStatementEntity> statements) {
        return statements.stream()
                .filter(statement -> statement.getDateField()
                        .isAfter(from) && statement.getDateField().isBefore(to))
                .collect(Collectors.toList());
    }
}

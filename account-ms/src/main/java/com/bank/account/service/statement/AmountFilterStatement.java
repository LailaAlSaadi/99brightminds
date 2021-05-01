package com.bank.account.service.statement;

import com.bank.account.model.entity.AccountStatementEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilterStatement implements StatementsFilter {

    private final Double from;
    private final Double to;

    public AmountFilterStatement(Double from, Double to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<AccountStatementEntity> filter(List<AccountStatementEntity> statements) {
        return statements.stream()
                .filter(statement -> statement.getAmount() > this.from
                        && statement.getAmount() < this.to)
                .collect(Collectors.toList());
    }
}

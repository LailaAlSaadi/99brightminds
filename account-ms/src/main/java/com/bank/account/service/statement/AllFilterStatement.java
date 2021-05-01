package com.bank.account.service.statement;

import com.bank.account.model.entity.AccountStatementEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AllFilterStatement implements StatementsFilter {

    private final Double amountFrom;
    private final Double amountTo;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public AllFilterStatement(Double amountFrom, Double amountTo, LocalDate dateFrom, LocalDate dateTo) {
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public List<AccountStatementEntity> filter(List<AccountStatementEntity> statements) {
        return statements.stream()
                .filter(statement -> statement.getAmount() > this.amountFrom
                        && statement.getAmount() < this.amountTo &&
                        statement.getDateField().isAfter(this.dateFrom) &&
                        statement.getDateField().isBefore(this.dateTo))
                .collect(Collectors.toList());
    }
}

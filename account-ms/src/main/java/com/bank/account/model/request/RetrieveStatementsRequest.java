package com.bank.account.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveStatementsRequest {

    private String accountId;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private Double amountFrom;

    private Double amountTo;

    public RetrieveStatementsRequest(String accountId) {
        this.accountId = accountId;
    }
}

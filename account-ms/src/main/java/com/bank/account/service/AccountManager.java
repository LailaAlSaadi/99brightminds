package com.bank.account.service;

import com.bank.account.model.request.RetrieveStatementsRequest;
import com.bank.account.model.response.AccountStatementResponse;

public interface AccountManager {

    AccountStatementResponse getAccountStatements(RetrieveStatementsRequest request);

}

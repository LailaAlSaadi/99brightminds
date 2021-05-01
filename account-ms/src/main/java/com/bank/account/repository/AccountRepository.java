package com.bank.account.repository;


import com.bank.account.model.entity.AccountStatementEntity;

import java.util.List;

public interface AccountRepository {

    List<AccountStatementEntity> find(String accountId);


}

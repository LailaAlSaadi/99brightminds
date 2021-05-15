package com.bank.account.model.response;

import com.bank.account.model.entity.AccountInfoEntity;
import com.bank.account.model.entity.AccountStatementEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountStatementResponse {

    private AccountInfoEntity accountInfo;
    List<AccountStatementEntity> statements;
}

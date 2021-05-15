package com.bank.account.service;

import com.bank.account.exception.InvalidAccountIdException;
import com.bank.account.exception.InvalidFromToAmounts;
import com.bank.account.exception.InvalidFromToDates;
import com.bank.account.model.entity.AccountInfoEntity;
import com.bank.account.model.entity.AccountStatementEntity;
import com.bank.account.model.request.RetrieveStatementsRequest;
import com.bank.account.model.response.AccountStatementResponse;
import com.bank.account.repository.AccountRepository;
import com.bank.account.service.filters.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Slf4j
@Repository
public class MsAccountManager implements AccountManager {

    private final AccountRepository msAccountRepository;

    @Autowired
    public MsAccountManager(AccountRepository msAccountRepository) {
        this.msAccountRepository = msAccountRepository;
    }

    public AccountInfoEntity getAccountInfo(String accountId) {
        return msAccountRepository.getAccount(accountId);
    }

    public List<AccountStatementEntity> getAccountInfo(String accountId, StatementsFilter statementsFilter) {
        return statementsFilter.filter(msAccountRepository.getStatements(accountId));
    }

    @Override
        public AccountStatementResponse getAccountStatements(RetrieveStatementsRequest request) {
        validateAccountId(request.getAccountId());
        boolean hasDates = validateDates(request.getDateFrom(), request.getDateTo());
        boolean hasAmount = validateAmount(request.getAmountFrom(), request.getAmountTo());


        List<AccountStatementEntity> result = null;
        if (hasDates) {
            if (hasAmount) {
                result = getAccountInfo(request.getAccountId(), new AllFilterStatement(request.getAmountFrom(), request.getAmountTo(), request.getDateFrom(), request.getDateTo()));
            } else {
                result = getAccountInfo(request.getAccountId(), new DateFilterStatement(request.getDateFrom(), request.getDateTo()));
            }
        }
        if (!hasDates) {
            if (hasAmount) {
                result = getAccountInfo(request.getAccountId(), new AmountFilterStatement(request.getAmountFrom(), request.getAmountTo()));
            } else {
                result = getAccountInfo(request.getAccountId(), new DefaultFilterStatement());
            }
        }
        return new AccountStatementResponse(getAccountInfo(request.getAccountId()),result);
    }

    private void validateAccountId(String accountId) {
        if(!isNumeric(accountId)){
            throw new InvalidAccountIdException();
        }
    }

    private boolean validateAmount(Double amountFrom, Double amountTo) {
        if (isNull(amountFrom) != isNull(amountTo)) {
            throw new InvalidFromToAmounts("amountFrom,amountTo must be provided both");
        }
        if (!isNull(amountFrom)) {
            if (amountFrom > amountTo) {
                throw new InvalidFromToDates("amountFrom must be less than amountTo");
            }
            return true;
        }
        return false;
    }

    private boolean validateDates(LocalDate dateFrom, LocalDate dateTo) {
        if (isNull(dateFrom) != isNull(dateTo)) {
            throw new InvalidFromToDates("dateFrom and dateTo must be provided both");
        }
        if (!isNull(dateFrom)) {
            if (dateFrom.isAfter(dateTo)) {
                throw new InvalidFromToDates("dateFrom must be before dateTo");
            }
            if (dateFrom.isAfter(LocalDate.now().plusDays(1))) {
                throw new InvalidFromToDates("dateFrom must be in the past");
            }
            if (dateTo.isAfter(LocalDate.now().plusDays(1))) {
                throw new InvalidFromToDates("dateTo must be in the past");
            }
            return true;
        }
        return false;
    }
}

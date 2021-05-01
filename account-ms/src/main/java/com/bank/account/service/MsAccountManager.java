package com.bank.account.service;

import com.bank.account.exception.InvalidFromToAmounts;
import com.bank.account.exception.InvalidFromToDates;
import com.bank.account.model.entity.AccountStatementEntity;
import com.bank.account.repository.AccountRepository;
import com.bank.account.service.statement.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Repository
public class MsAccountManager implements AccountManager {

    private final AccountRepository msAccountRepository;

    @Autowired
    public MsAccountManager(AccountRepository msAccountRepository) {
        this.msAccountRepository = msAccountRepository;
    }

    public List<AccountStatementEntity> getFilteredStatements(String accountId, StatementsFilter statementsFilter) {
        return statementsFilter.filter(msAccountRepository.find(accountId));
    }

    @Override
    public List<AccountStatementEntity> getAccountStatements(String accountId, LocalDate dateFrom, LocalDate dateTo, Double amountFrom, Double amountTo) {
        boolean hasDates = validateDates(dateFrom, dateTo);
        boolean hasAmount = validateAmount(amountFrom, amountTo);

        List<AccountStatementEntity> result = null;
        if (hasDates) {
            if (hasAmount) {
                result = getFilteredStatements(accountId, new AllFilterStatement(amountFrom, amountTo, dateFrom, dateTo));
            } else {
                result = getFilteredStatements(accountId, new DateFilterStatement(dateFrom, dateTo));
            }
        }
        if (!hasDates) {
            if (hasAmount) {
                result = getFilteredStatements(accountId, new AmountFilterStatement(amountFrom, amountTo));
            } else {
                result = getFilteredStatements(accountId, new DefaultFilterStatement());
            }
        }
        return result;
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

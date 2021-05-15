package com.bank.account.service;

import com.bank.account.exception.InvalidAccountIdException;
import com.bank.account.exception.InvalidFromToAmounts;
import com.bank.account.exception.InvalidFromToDates;
import com.bank.account.model.entity.AccountStatementEntity;
import com.bank.account.model.request.RetrieveStatementsRequest;
import com.bank.account.model.response.AccountStatementResponse;
import com.bank.account.repository.MsAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class MsAccountManagerTest {

    @InjectMocks
    private final MsAccountManager msAccountManager;

    @Mock
    private MsAccountRepository msAccountRepository;

    MsAccountManagerTest() {
        MockitoAnnotations.initMocks(this);
        ArrayList<AccountStatementEntity> statementsList = new ArrayList<>();
        statementsList.add(new AccountStatementEntity(1, "1", LocalDate.parse("2020-05-06"), 1.32));
        statementsList.add(new AccountStatementEntity(2, "1", LocalDate.parse("2021-04-06"), 12.32));
        statementsList.add(new AccountStatementEntity(3, "1", LocalDate.parse("2021-03-06"), 12.32));
        when(msAccountRepository.getStatements(anyString())).thenReturn(statementsList);
        msAccountManager = new MsAccountManager(msAccountRepository);
    }


    @Test
    void givenNonNumericAccountId_whenGetStatements_thenException() {
        RetrieveStatementsRequest request = new RetrieveStatementsRequest("a");
        assertThrows(InvalidAccountIdException.class, () ->
                msAccountManager.getAccountStatements(request));
    }

    @Test
    void givenValidAccountId_whenGetStatementsOnlyDateFromProvided_thenException() {
        LocalDate start = LocalDate.parse("2020-05-06");
        RetrieveStatementsRequest request = new RetrieveStatementsRequest(
                "1",
                start, null,
                null, null);
        assertThrows(InvalidFromToDates.class, () ->
                msAccountManager.getAccountStatements(request));
    }


    @Test
    void givenValidAccountId_whenGetStatementsOnlyAmountFromProvided_thenException() {
        RetrieveStatementsRequest request = new RetrieveStatementsRequest(
                "1",
                null, null,
                999.9, null);
        assertThrows(InvalidFromToAmounts.class, () ->
                msAccountManager.getAccountStatements(request));
    }

    @Test
    void givenNumericAccountId_whenGetDefaultStatements_thenSuccess() {
        AccountStatementResponse accountStatements = msAccountManager.getAccountStatements(new RetrieveStatementsRequest("1"));
        assertEquals(2, accountStatements.getStatements().size());
    }


    @Test
    void givenValidAccountId_whenGetStatementsBetweenDates_thenSuccess() {
        LocalDate start = LocalDate.parse("2012-03-06");
        LocalDate end = LocalDate.parse("2021-04-15");
        AccountStatementResponse accountStatements = msAccountManager.getAccountStatements(
                new RetrieveStatementsRequest(
                "1",
                start, end,
                null, null));
        assertEquals(3, accountStatements.getStatements().size());
    }


}
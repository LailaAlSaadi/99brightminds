package com.bank.account.repository;

import com.bank.account.exception.DatabaseQueryException;
import com.bank.account.model.entity.AccountStatementEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MsAccountRepository implements AccountRepository {

    public static final String STATEMENT = "statement";
    public static final String AMOUNT = "amount";
    public static final String ACCOUNT_ID = "account_id";
    public static final String DATEFIELD = "datefield";
    public static final String ID = "id";
    private final MsConnection msConnection;

    @Autowired
    public MsAccountRepository(MsConnection msConnection) {
        this.msConnection = msConnection;
    }

    public List<AccountStatementEntity> find(String accountId) {
        try (ResultSet rs =
                     msConnection
                             .executeQuery("SELECT * FROM " + STATEMENT + " WHERE " + ACCOUNT_ID + " = " + accountId)) {

            List<AccountStatementEntity> accountStatementEntities = new ArrayList<>();
            while (rs.next()) {
                AccountStatementEntity accountStatementEntity = AccountStatementEntity.builder()
                        .amount(rs.getDouble(AMOUNT))
                        .accountId(rs.getInt(ACCOUNT_ID))
                        .dateField(LocalDate.parse(rs.getString(DATEFIELD), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .id(rs.getInt(ID)).build();
                accountStatementEntities.add(accountStatementEntity);
            }
            return accountStatementEntities;
        } catch (SQLException e) {
            log.error("Database Query Exception " + e.getMessage());
            throw new DatabaseQueryException();
        }
    }
}

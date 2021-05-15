package com.bank.account.repository;

import com.bank.account.exception.DatabaseQueryException;
import com.bank.account.model.entity.AccountInfoEntity;
import com.bank.account.model.entity.AccountStatementEntity;
import com.bank.account.model.enums.AccountType;
import com.bank.account.util.HashingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MsAccountRepository implements AccountRepository {
    public static final String ID = "id";

    public static final String STATEMENT_TABLE = "statement";
    public static final String AMOUNT = "amount";
    public static final String ACCOUNT_ID = "account_id";
    public static final String DATEFIELD = "datefield";

    public static final String ACCOUNT_TABLE = "account";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT_NUMBER = "account_number";

    private final MsConnection msConnection;

    @Autowired
    public MsAccountRepository(MsConnection msConnection) {
        this.msConnection = msConnection;
    }

    public List<AccountStatementEntity> getStatements(String accountId) {

        String query = "SELECT * FROM " + STATEMENT_TABLE + " WHERE " + ACCOUNT_ID + " = ?";
        try (PreparedStatement pstmt = msConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, accountId);
            return getStatements(pstmt);
        } catch (SQLException e) {
            throw new DatabaseQueryException();
        }
    }

    private List<AccountStatementEntity> getStatements(PreparedStatement pstmt) {
        try (ResultSet rs =
                     pstmt.executeQuery()) {
            List<AccountStatementEntity> accountStatementEntities = new ArrayList<>();
            while (rs.next()) {
                accountStatementEntities.add(AccountStatementEntity.builder()
                        .amount(rs.getDouble(AMOUNT))
                        .accountId(HashingUtils.digest(String.valueOf(rs.getInt(ACCOUNT_ID)).getBytes(StandardCharsets.UTF_8)))
                        .dateField(LocalDate.parse(rs.getString(DATEFIELD), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .id(rs.getInt(ID)).build());
            }
            return accountStatementEntities;
        } catch (SQLException e) {
            log.error("Database Query Exception " + e.getMessage());
            throw new DatabaseQueryException();
        }
    }


    public AccountInfoEntity getAccount(String accountId) {

        String query = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + ID + " = ?";
        try (PreparedStatement pstmt = msConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, accountId);
            return getAccountInfoEntity(pstmt);
        } catch (SQLException e) {
            throw new DatabaseQueryException();
        }
    }

    private AccountInfoEntity getAccountInfoEntity(PreparedStatement pstmt) {
        try (ResultSet rs =
                     pstmt.executeQuery()) {
            rs.next();
            return AccountInfoEntity.builder()
                    .id(rs.getInt(ID))
                    .accountType(AccountType.valueOf(rs.getString(ACCOUNT_TYPE).toUpperCase()))
                    .accountNumber(rs.getString(ACCOUNT_NUMBER))
                    .build();
        } catch (SQLException e) {
            log.error("Database Query Exception " + e.getMessage());
            throw new DatabaseQueryException();
        }
    }

}

//
//package com.bank.account.repository;
//
//import com.bank.account.model.entity.BankStatement;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@Slf4j
//class MSDatabaseBankRepositoryTest {
//
//    public static final String STATEMENT = "statement";
//    public static final String AMOUNT = "amount";
//    public static final String ACCOUNT_ID = "account_id";
//    public static final String DATEFIELD = "datefield";
//    public static final String ID = "id";
//
//    @InjectMocks
//    private final MSDatabaseBankRepository msDatabaseBankRepository;
//
//    @Mock
//    private MSDatabaseConnection msDatabaseConnection;
//
//    @Mock
//    private ResultSet rs;
//
//    public MSDatabaseBankRepositoryTest() throws SQLException {
//        when(rs.next()).thenReturn(true);
//        when(rs.first()).thenReturn(true);
//        when(rs.getDouble(AMOUNT)).thenReturn(1234.51);
//        when(rs.getInt(ACCOUNT_ID)).thenReturn(2);
//        when(rs.getString(DATEFIELD)).thenReturn("22.04.2021");
//        when(rs.getInt(ID)).thenReturn(2);
//
//        when(msDatabaseConnection.executeQuery(any())).thenReturn(rs);
//        msDatabaseBankRepository = new MSDatabaseBankRepository(msDatabaseConnection);
//    }
//
//    @Test
//    public void givenValidAccountId_WhenGetStatements_thenSuccess(){
//        List<BankStatement> statement = msDatabaseBankRepository.findBy("1");
//        statement.forEach(r->log.info(r.toString()));
//    }
//
//
//
//
//}
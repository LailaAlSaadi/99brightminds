package com.bank.account.repository;

import com.bank.account.configuration.properties.AccessDatabaseProperties;
import com.bank.account.exception.DatabaseConnectionException;
import com.esotericsoftware.minlog.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@Component
public class MsConnection {

    public static final String UCANACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    private final Connection connection;

    @Autowired
    public MsConnection(AccessDatabaseProperties databaseProperties) {
        try {
            Class.forName(UCANACCESS_DRIVER);
        } catch (ClassNotFoundException ex) {
            Log.error("Unable to load the class. Terminating the program");
        }
        try  {
            connection = DriverManager.getConnection(databaseProperties.getUrl());
        } catch (Exception ex) {
            Log.error("Error: " + ex.getMessage());
            throw new DatabaseConnectionException();
        }
    }

    public Connection getConnection() {
            return connection;
    }
}

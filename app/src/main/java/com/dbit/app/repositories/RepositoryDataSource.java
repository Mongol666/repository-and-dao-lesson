package com.dbit.app.repositories;

import com.dbit.app.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Slf4j
public class RepositoryDataSource implements DataSource {

    private final String DRIVER;
    private final String USER;
    private final String PASSWORD;
    private final String URL;

    private static volatile RepositoryDataSource instance;

    public RepositoryDataSource(String DRIVER, String USER, String PASSWORD, String URL) {
        this.DRIVER = DRIVER;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.URL = URL;

        try {
            Class.forName(this.DRIVER);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }

    public static RepositoryDataSource getInstance(String DRIVER, String USER, String PASSWORD, String URL) {
        if (instance == null) {
            synchronized (RepositoryDataSource.class) {
                if (instance == null) {
                    instance = new RepositoryDataSource(DRIVER, USER, PASSWORD, URL);
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }
}

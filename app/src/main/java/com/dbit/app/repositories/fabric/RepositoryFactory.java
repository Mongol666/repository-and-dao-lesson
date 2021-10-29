package com.dbit.app.repositories.fabric;

import com.dbit.app.repositories.EmployeeRepository;
import com.dbit.app.repositories.EmployeeRepositoryInMemory;
import com.dbit.app.repositories.EmployeeRepositoryInPostgres;
import com.dbit.app.repositories.RepositoryDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class RepositoryFactory {

    private static final RepositoryType TYPE;

    private static RepositoryDataSource dataSource;

    static {
        Properties appProperties = new Properties();
        try {
            appProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        TYPE = RepositoryType.getTypeByString(appProperties.getProperty("repository.type"));
        if (TYPE == RepositoryType.POSTGRES) {
            dataSource = RepositoryDataSource.getInstance(
                    appProperties.getProperty("postgres.driver"),
                    appProperties.getProperty("postgres.user"),
                    appProperties.getProperty("postgres.password"),
                    appProperties.getProperty("postgres.url")
            );

        }
    }

    private RepositoryFactory() {
    }

    public static EmployeeRepository getEmployeeRepository() {
        switch (TYPE) {
            case POSTGRES:
                return EmployeeRepositoryInPostgres.getInstance(dataSource);
            case MEMORY:
            default:
                return EmployeeRepositoryInMemory.getInstance();
        }
    }
}

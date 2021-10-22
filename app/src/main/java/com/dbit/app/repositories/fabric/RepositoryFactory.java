package com.dbit.app.repositories.fabric;

import com.dbit.app.repositories.EmployeeRepository;
import com.dbit.app.repositories.EmployeeRepositoryInMemory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class RepositoryFactory {

    private static final RepositoryType TYPE;

    static {
        Properties appProperties = new Properties();
        try {
            appProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        TYPE = RepositoryType.getTypeByString(appProperties.getProperty("repository.type"));
    }

    private RepositoryFactory() {
    }

    public static EmployeeRepository getEmployeeRepository() {
        switch (TYPE) {
            case MEMORY:
            default:
                return EmployeeRepositoryInMemory.getInstance();
        }
    }
}

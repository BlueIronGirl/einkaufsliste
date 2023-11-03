package de.shoppinglist.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class DatasourceConfigurationTest {

    @Autowired
    private DatasourceConfiguration datasourceConfiguration;

    @Test
    void getAllConfigurationProperties() {
        assertEquals("jdbc:mariadb://localhost:3306/shopping-list", datasourceConfiguration.getUrl());
        assertEquals("root", datasourceConfiguration.getUsername());
        assertEquals("test123", datasourceConfiguration.getPassword());
        assertEquals("org.mariadb.jdbc.Driver", datasourceConfiguration.getDriverClassName());
        assertEquals("jdbc:mariadb://localhost:3306/shopping-list", datasourceConfiguration.getDatasourceUrl());
    }
}
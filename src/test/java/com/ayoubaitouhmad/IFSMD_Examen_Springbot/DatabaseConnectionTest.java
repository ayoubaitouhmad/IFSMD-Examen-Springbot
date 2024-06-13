package com.ayoubaitouhmad.IFSMD_Examen_Springbot;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws Exception {
        assertNotNull(dataSource.getConnection());
        System.out.println("Database connected successfully.");
    }
}

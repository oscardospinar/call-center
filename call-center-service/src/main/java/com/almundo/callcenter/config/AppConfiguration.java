package com.almundo.callcenter.config;

import com.almundo.callcenter.exception.CallCenterException;
import com.almundo.callcenter.exception.ConfigurationExeption;
import com.almundo.callcenter.repository.EmployeeRepository;
import com.almundo.callcenter.repository.IEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Configuration class loaded by spring.
 */
@Configuration
@PropertySource("application.properties")
public class AppConfiguration {

    /**
     * The jooq dsl context bean.
     * @param url The url of the database.
     * @param user The user of the database.
     * @param password The password to authenticate the user in th database.
     * @param driver The driver used by the connection in jooq.
     * @return A dsl context configured with a connection.
     */
    @Bean
    public DSLContext dsl (@Value("${db.url}") String url,
                           @Value("${db.username}") String user,
                           @Value("${db.password}") String password,
                           @Value("${db.driverClassName}") String driver) {
        try {
            Class.forName(driver);
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("driver-class-name", driver);
            Connection con = DriverManager.getConnection(url, properties);
            return DSL.using(con, SQLDialect.POSTGRES_9_3);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConfigurationExeption("Error building the dsl context ", e);
        }
    }
}

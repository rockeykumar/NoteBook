package com.raj.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;


/**
 * This class is used for Connection Configuration of Oracle Database
 */
@Factory
public class JdbcConfig {

    private static final Logger logger = LoggerFactory.getLogger(JdbcConfig.class);

    private String className = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521/orclpdb1";
    private String username = "system";
    private String password = "admin123";
    private String initialSize = "1";
    private String maxActive = "20";
    private String minIdle = "200";

    @Singleton
    @Named("getDataSource")
    public DataSource getDataSource() {

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(null);
        config.setDriverClassName(className);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setAutoCommit(false);
        config.setConnectionTimeout(1000);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.setMinimumIdle(Integer.parseInt(minIdle));
        config.setMaximumPoolSize(Integer.parseInt(maxActive));
        config.setConnectionTestQuery("select * from dual");

        HikariDataSource ds = null;
        try {
            ds = new HikariDataSource(config);
        } catch (Exception ex) {
            logger.error("Exception occurred : ", ex);
        }

        return ds;
    }

    @Singleton
    @Named("jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());

        return jdbcTemplate;
    }
}
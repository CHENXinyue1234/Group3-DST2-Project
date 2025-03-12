package cn.edu.zju.dbutils;

import cn.edu.zju.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;

public class dbutil {

    private static final Logger log = LoggerFactory.getLogger(dbutil.class);

    public static Connection getConnection() {
        Connection connection = null;
        AppConfig config = AppConfig.getInstance();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            log.info("JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            log.info("", e);
        }
        try {
            connection = DriverManager.getConnection(config.getJdbcUrl(),
                    config.getJdbcUsername(),
                    config.getJdbcPassword());
            log.info("Database connection established successfully.");
        } catch (SQLException e) {
            log.info("", e);
        }
        return connection;
    }

    public static void execSQL(Consumer<Connection> consumer) {
        Connection connection = null;
        try {
            connection = getConnection();
            if (connection != null) {
                consumer.accept(connection);
            } else {
                log.error("Connection is null, cannot execute SQL.");
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    log.info("Database connection closed successfully.");
                } catch (SQLException e) {
                    log.error("Failed to close database connection.", e);
                }
            }
        }
    }
}
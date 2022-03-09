package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static ru.netology.data.DataHelper.getPropertyOrDefValue;

public class DbHelper {
    private final QueryRunner runner = new QueryRunner();
    private Properties prop = prop();
    private final Connection conn = getConnect();


    private Properties prop() {
        Properties properties = new Properties();
        try (InputStream is = DbHelper.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }


    @SneakyThrows
    private Connection getConnect() {
        String key = "db.url";
        String defValue = prop.getProperty("spring.datasource.url");
        return DriverManager.getConnection(
                getPropertyOrDefValue(key, defValue),
                prop.getProperty("spring.datasource.username"),
                prop.getProperty("spring.datasource.password")
        );
    }

    @SneakyThrows
    public String getPaymentStatus() {
        val status = "SELECT status FROM payment_entity ORDER BY created DESC";
        return runner.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public Integer getPaymentAmount() {
        val amount = "SELECT amount FROM payment_entity ORDER BY created DESC";
        return runner.query(conn, amount, new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditRequestStatus() {
        val status = "SELECT status FROM credit_request_entity ORDER BY created DESC";
        return runner.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditId() {
        val id = "SELECT credit_id FROM order_entity ORDER BY created DESC";
        return runner.query(conn, id, new ScalarHandler<>());
    }
}

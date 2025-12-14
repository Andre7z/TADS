package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);

    private static final String URL = "jdbc:postgresql://localhost:5432/diario";
    private static final String USER = "postgres";
    private static final String PASS = "2006";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            logger.info("Conexão com PostgreSQL aberta");
            return conn;
        } catch (SQLException e) {
            logger.error("Erro ao conectar ao PostgreSQL: " + e.getMessage());
            return null;
        }
    }
}

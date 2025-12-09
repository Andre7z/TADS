package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/notas";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "2006";
    private static final Logger logger = Logger.getLogger(Conexao.class.getName());

    public static Connection getConnection() {
        try {
            Connection c = DriverManager.getConnection(URL, USUARIO, SENHA);
            logger.info("Conexão estabelecida com sucesso.");
            return c;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao conectar ao banco", e);
            throw new RuntimeException(e);
        }
    }
}

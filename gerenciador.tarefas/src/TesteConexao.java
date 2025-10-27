import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        try (Connection con = Conexao.conectar()) {
            System.out.println("Conexão OK!");
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

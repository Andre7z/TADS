package dao;

import java.sql.*;
import model.Pessoa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class PessoaDAO {
    private static final Logger logger = LogManager.getLogger(PessoaDAO.class);
    private Connection conn;

    public PessoaDAO(Connection conn) {
        this.conn = conn;
    }

    public int salvar(Pessoa p) {
        String sql = "INSERT INTO pessoa (nome, endereco, telefone, email) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEndereco());
            ps.setString(3, p.getTelefone());
            ps.setString(4, p.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                logger.info("Pessoa salva com id=" + id);
                return id;
            }
        } catch (SQLException e) {
            logger.error("Erro ao salvar pessoa: " + e.getMessage());
        }
        return 0;
    }

    public boolean alterar(Pessoa p) {
        String sql = "UPDATE pessoa SET nome=?, endereco=?, telefone=?, email=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEndereco());
            ps.setString(3, p.getTelefone());
            ps.setString(4, p.getEmail());
            ps.setInt(5, p.getId());
            int linhas = ps.executeUpdate();
            logger.info("Pessoa alterada, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao alterar pessoa: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Pessoa excluída, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao excluir pessoa: " + e.getMessage());
            return false;
        }
    }

    public Pessoa pesquisar(int id) {
        String sql = "SELECT * FROM pessoa WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pessoa p = new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"));
                logger.info("Pessoa encontrada id=" + id);
                return p;
            }
        } catch (SQLException e) {
            logger.error("Erro ao pesquisar pessoa: " + e.getMessage());
        }
        return null;
    }
}

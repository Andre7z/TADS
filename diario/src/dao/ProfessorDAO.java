package dao;

import java.sql.*;
import java.util.logging.Logger;
import model.Professor;

public class ProfessorDAO {
    private static final Logger logger = Logger.getLogger(ProfessorDAO.class.getName());
    private Connection conn;

    public ProfessorDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Professor prof) {
        String sql = "INSERT INTO professor (id_pessoa, matricula) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prof.getId());
            ps.setString(2, prof.getMatricula());
            int linhas = ps.executeUpdate();
            logger.info("Professor salvo, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao salvar professor: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Professor prof) {
        String sql = "UPDATE professor SET matricula=? WHERE id_pessoa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prof.getMatricula());
            ps.setInt(2, prof.getId());
            int linhas = ps.executeUpdate();
            logger.info("Professor alterado, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao alterar professor: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idPessoa) {
        String sql = "DELETE FROM professor WHERE id_pessoa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPessoa);
            int linhas = ps.executeUpdate();
            logger.info("Professor excluído, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir professor: " + e.getMessage());
            return false;
        }
    }

    public Professor pesquisar(int idPessoa) {
        String sql = "SELECT p.*, pr.matricula FROM professor pr " +
                     "JOIN pessoa p ON p.id = pr.id_pessoa WHERE pr.id_pessoa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPessoa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Professor prof = new Professor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("matricula")
                );
                logger.info("Professor encontrado id=" + idPessoa);
                return prof;
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar professor: " + e.getMessage());
        }
        return null;
    }
}

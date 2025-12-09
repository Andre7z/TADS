package dao;

import java.sql.*;
import java.util.logging.Logger;
import model.Disciplina;

public class DisciplinaDAO {
    private static final Logger logger = Logger.getLogger(DisciplinaDAO.class.getName());
    private Connection conn;

    public DisciplinaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Disciplina d) {
        String sql = "INSERT INTO disciplina (nome_disciplina) VALUES (?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNomeDisciplina());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                d.setId(rs.getInt("id"));
                logger.info("Disciplina salva id=" + d.getId());
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Erro ao salvar disciplina: " + e.getMessage());
        }
        return false;
    }

    public boolean alterar(Disciplina d) {
        String sql = "UPDATE disciplina SET nome_disciplina=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNomeDisciplina());
            ps.setInt(2, d.getId());
            int linhas = ps.executeUpdate();
            logger.info("Disciplina alterada, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao alterar disciplina: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM disciplina WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Disciplina excluída, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir disciplina: " + e.getMessage());
            return false;
        }
    }

    public Disciplina pesquisar(int id) {
        String sql = "SELECT * FROM disciplina WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Disciplina d = new Disciplina(
                    rs.getInt("id"),
                    rs.getString("nome_disciplina")
                );
                logger.info("Disciplina encontrada id=" + id);
                return d;
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar disciplina: " + e.getMessage());
        }
        return null;
    }
}

package dao;

import java.sql.*;
import java.util.logging.Logger;
import model.Turma;

public class TurmaDAO {
    private static final Logger logger = Logger.getLogger(TurmaDAO.class.getName());
    private Connection conn;

    public TurmaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Turma t) {
        String sql = "INSERT INTO turma (nome_turma, id_disciplina, id_professor, id_periodo) " +
                "VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());
            ps.setInt(2, t.getIdDisciplina());
            ps.setInt(3, t.getIdProfessor());
            ps.setInt(4, t.getIdPeriodo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                t.setId(rs.getInt("id"));
                logger.info("Turma salva id=" + t.getId());
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Erro ao salvar turma: " + e.getMessage());
        }
        return false;
    }

    public boolean alterar(Turma t) {
        String sql = "UPDATE turma SET nome_turma=?, id_disciplina=?, id_professor=?, id_periodo=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());
            ps.setInt(2, t.getIdDisciplina());
            ps.setInt(3, t.getIdProfessor());
            ps.setInt(4, t.getIdPeriodo());
            ps.setInt(5, t.getId());
            int linhas = ps.executeUpdate();
            logger.info("Turma alterada, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao alterar turma: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM turma WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Turma excluída, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir turma: " + e.getMessage());
            return false;
        }
    }

    public Turma pesquisar(int id) {
        String sql = "SELECT * FROM turma WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Turma t = new Turma(
                        rs.getInt("id"),
                        rs.getString("nome_turma"),
                        rs.getInt("id_disciplina"),
                        rs.getInt("id_professor"),
                        rs.getInt("id_periodo"));
                logger.info("Turma encontrada id=" + id);
                return t;
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar turma: " + e.getMessage());
        }
        return null;
    }
}

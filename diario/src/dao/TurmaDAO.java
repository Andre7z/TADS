package dao;

import model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
                int idGerado = rs.getInt("id");
                t.setId(idGerado);
                logger.info("Turma salva com id=" + idGerado);
                return true;
            }
            logger.warning("Insert de turma não retornou id");
        } catch (SQLException e) {
            logger.severe("Erro ao salvar turma: " + e.getMessage());
        }
        return false;
    }

    public boolean alterar(Turma t) {
        String sql = "UPDATE turma SET nome_turma=?, id_disciplina=?, id_professor=?, id_periodo=? " +
                     "WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());
            ps.setInt(2, t.getIdDisciplina());
            ps.setInt(3, t.getIdProfessor());
            ps.setInt(4, t.getIdPeriodo());
            ps.setInt(5, t.getId());
            int linhas = ps.executeUpdate();
            logger.info("Turma alterada, linhas afetadas=" + linhas);
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
            logger.info("Turma excluída, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir turma: " + e.getMessage());
            return false;
        }
    }

    public Turma pesquisar(int id) {
        String sql = "SELECT id, nome_turma, id_disciplina, id_professor, id_periodo " +
                     "FROM turma WHERE id=?";
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
            } else {
                logger.info("Turma não encontrada id=" + id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar turma: " + e.getMessage());
        }
        return null;
    }

    public List<Turma> listarTodos() {
        String sql = "SELECT id, nome_turma, id_disciplina, id_professor, id_periodo " +
                     "FROM turma ORDER BY nome_turma";
        List<Turma> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Turma t = new Turma(
                        rs.getInt("id"),
                        rs.getString("nome_turma"),
                        rs.getInt("id_disciplina"),
                        rs.getInt("id_professor"),
                        rs.getInt("id_periodo"));
                lista.add(t);
            }
            logger.info("Lista de turmas carregada. Quantidade=" + lista.size());
        } catch (SQLException e) {
            logger.severe("Erro ao listar turmas: " + e.getMessage());
        }
        return lista;
    }
}

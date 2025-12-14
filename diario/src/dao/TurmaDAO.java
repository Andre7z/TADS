package dao;

import model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class TurmaDAO {

    private static final Logger logger = LogManager.getLogger(TurmaDAO.class);

    private final Connection conn;

    public TurmaDAO(Connection conn) {
        this.conn = conn;
    }

    /* ========== CRUD de Turma ========== */

    public boolean salvar(Turma t) {
        String sql = "INSERT INTO turma (nome_turma, id_disciplina, id_professor, id_periodo) " +
                     "VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());
            ps.setInt(2, t.getIdDisciplina());
            ps.setInt(3, t.getIdProfessor());
            ps.setInt(4, t.getIdPeriodo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idGerado = rs.getInt("id");
                    t.setId(idGerado);
                    logger.info("Turma salva com id={}", idGerado);
                    return true;
                }
            }
            logger.warn("Insert de turma não retornou id");
        } catch (SQLException e) {
            logger.error("Erro ao salvar turma: {}", e.getMessage(), e);
        }
        return false;
    }

    public boolean alterar(Turma t) {
        String sql = "UPDATE turma SET nome_turma = ?, id_disciplina = ?, " +
                     "id_professor = ?, id_periodo = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());
            ps.setInt(2, t.getIdDisciplina());
            ps.setInt(3, t.getIdProfessor());
            ps.setInt(4, t.getIdPeriodo());
            ps.setInt(5, t.getId());

            int linhas = ps.executeUpdate();
            logger.info("Turma alterada, linhas afetadas={}", linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao alterar turma: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM turma WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Turma excluída, linhas afetadas={}", linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao excluir turma: {}", e.getMessage(), e);
            return false;
        }
    }

    public Turma pesquisar(int id) {
        String sql = "SELECT id, nome_turma, id_disciplina, id_professor, id_periodo " +
                     "FROM turma WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Turma t = new Turma(
                            rs.getInt("id"),
                            rs.getString("nome_turma"),
                            rs.getInt("id_disciplina"),
                            rs.getInt("id_professor"),
                            rs.getInt("id_periodo"));
                    logger.info("Turma encontrada id={}", id);
                    return t;
                } else {
                    logger.info("Turma não encontrada id={}", id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao pesquisar turma: {}", e.getMessage(), e);
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
            logger.info("Lista de turmas carregada. Quantidade={}", lista.size());
        } catch (SQLException e) {
            logger.error("Erro ao listar turmas: {}", e.getMessage(), e);
        }
        return lista;
    }

    /* ========== Métodos para combos ========== */

    public HashMap<String, Integer> buscarDisciplinasParaCombo() {
        HashMap<String, Integer> mapa = new HashMap<>();
        String sql = "SELECT id, nome_disciplina FROM disciplina ORDER BY nome_disciplina";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_disciplina");
                String item = id + " - " + nome;
                mapa.put(item, id);
            }
            logger.info("Disciplinas carregadas para combo: {}", mapa.size());
        } catch (SQLException e) {
            logger.error("Erro ao carregar disciplinas para combo: {}", e.getMessage(), e);
        }
        return mapa;
    }

    public HashMap<String, Integer> buscarProfessoresParaCombo() {
        HashMap<String, Integer> mapa = new HashMap<>();
        String sql = "SELECT pe.id, pe.nome FROM professor pr " +
                     "JOIN pessoa pe ON pe.id = pr.id_pessoa " +
                     "ORDER BY pe.nome";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String item = id + " - " + nome;
                mapa.put(item, id);
            }
            logger.info("Professores carregados para combo: {}", mapa.size());
        } catch (SQLException e) {
            logger.error("Erro ao carregar professores para combo: {}", e.getMessage(), e);
        }
        return mapa;
    }

    public HashMap<String, Integer> buscarPeriodosParaCombo() {
        HashMap<String, Integer> mapa = new HashMap<>();
        String sql = "SELECT id, nome_periodo FROM periodo ORDER BY nome_periodo";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_periodo");
                String item = id + " - " + nome;
                mapa.put(item, id);
            }
            logger.info("Períodos carregados para combo: {}", mapa.size());
        } catch (SQLException e) {
            logger.error("Erro ao carregar períodos para combo: {}", e.getMessage(), e);
        }
        return mapa;
    }
}

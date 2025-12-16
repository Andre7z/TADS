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
        String sql = "INSERT INTO turma (nome_turma) VALUES (?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());

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
        String sql = "UPDATE turma SET nome_turma = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeTurma());
            ps.setInt(2, t.getId());

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
        String sql = "SELECT id, nome_turma FROM turma WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Turma t = new Turma(
                            rs.getInt("id"),
                            rs.getString("nome_turma"));
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
        String sql = "SELECT id, nome_turma FROM turma ORDER BY nome_turma";
        List<Turma> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Turma t = new Turma(
                        rs.getInt("id"),
                        rs.getString("nome_turma"));
                lista.add(t);
            }
            logger.info("Lista de turmas carregada. Quantidade={}", lista.size());
        } catch (SQLException e) {
            logger.error("Erro ao listar turmas: {}", e.getMessage(), e);
        }
        return lista;
    }

    /* ========== Método simples para combo (opcional) ========== */

    public HashMap<String, Integer> buscarTurmasParaCombo() {
        HashMap<String, Integer> mapa = new HashMap<>();
        String sql = "SELECT id, nome_turma FROM turma ORDER BY nome_turma";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_turma");
                String item = id + " - " + nome;
                mapa.put(item, id);
            }
            logger.info("Turmas carregadas para combo: {}", mapa.size());
        } catch (SQLException e) {
            logger.error("Erro ao carregar turmas para combo: {}", e.getMessage(), e);
        }
        return mapa;
    }
}

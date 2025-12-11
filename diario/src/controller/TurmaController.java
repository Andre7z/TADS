package controller;

import dao.TurmaDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import model.Turma;

public class TurmaController {

    private static final Logger logger = Logger.getLogger(TurmaController.class.getName());

    private TurmaDAO turmaDAO;
    private Connection conn; // usado para carregar combos

    public TurmaController(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    // construtor alternativo se quiser reaproveitar a mesma Connection dos combos
    public TurmaController(TurmaDAO turmaDAO, Connection conn) {
        this.turmaDAO = turmaDAO;
        this.conn = conn;
    }

    /* ================== CRUD ================== */

    public boolean salvar(Turma t) {
        logger.info("Iniciando salvar Turma");
        boolean ok = turmaDAO.salvar(t);
        logger.info("Resultado salvar Turma=" + ok + " id=" + t.getId());
        return ok;
    }

    public boolean alterar(Turma t) {
        logger.info("Iniciando alterar Turma id=" + t.getId());
        boolean ok = turmaDAO.alterar(t);
        logger.info("Resultado alterar Turma=" + ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Turma id=" + id);
        boolean ok = turmaDAO.excluir(id);
        logger.info("Resultado excluir Turma=" + ok);
        return ok;
    }

    public Turma pesquisar(int id) {
        logger.info("Iniciando pesquisar Turma id=" + id);
        Turma t = turmaDAO.pesquisar(id);
        logger.info("Turma encontrada? " + (t != null));
        return t;
    }

    public List<Turma> listarTodos() {
        logger.info("Iniciando listarTodos Turma");
        List<Turma> lista = turmaDAO.listarTodos();
        logger.info("Total de turmas retornadas=" + lista.size());
        return lista;
    }

    /* ================== Carregamento de combos ================== */

    // Cada método popula o combo e devolve o Map<String, Integer> para a tela

    public HashMap<String, Integer> carregarDisciplinas(JComboBox<String> combo) {
        HashMap<String, Integer> mapa = new HashMap<>();
        combo.removeAllItems();
        combo.addItem("Selecione a disciplina...");

        if (conn == null) {
            logger.warning("Connection null em carregarDisciplinas");
            return mapa;
        }

        String sql = "SELECT id, nome_disciplina FROM disciplina ORDER BY nome_disciplina";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_disciplina");
                String item = id + " - " + nome;
                combo.addItem(item);
                mapa.put(item, id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao carregar disciplinas para combo: " + e.getMessage());
        }
        return mapa;
    }

    public HashMap<String, Integer> carregarProfessores(JComboBox<String> combo) {
        HashMap<String, Integer> mapa = new HashMap<>();
        combo.removeAllItems();
        combo.addItem("Selecione o professor...");

        if (conn == null) {
            logger.warning("Connection null em carregarProfessores");
            return mapa;
        }

        String sql = "SELECT pe.id, pe.nome FROM professor pr " +
                     "JOIN pessoa pe ON pe.id = pr.id_pessoa " +
                     "ORDER BY pe.nome";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String item = id + " - " + nome;
                combo.addItem(item);
                mapa.put(item, id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao carregar professores para combo: " + e.getMessage());
        }
        return mapa;
    }

    public HashMap<String, Integer> carregarPeriodos(JComboBox<String> combo) {
        HashMap<String, Integer> mapa = new HashMap<>();
        combo.removeAllItems();
        combo.addItem("Selecione o período...");

        if (conn == null) {
            logger.warning("Connection null em carregarPeriodos");
            return mapa;
        }

        String sql = "SELECT id, nome_periodo FROM periodo ORDER BY nome_periodo";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_periodo");
                String item = id + " - " + nome;
                combo.addItem(item);
                mapa.put(item, id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao carregar períodos para combo: " + e.getMessage());
        }
        return mapa;
    }
}

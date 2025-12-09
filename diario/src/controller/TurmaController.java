package controller;

import dao.TurmaDAO;
import dao.ConnectionFactory;
import model.Turma;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Logger;

public class TurmaController {

    private static final Logger logger = Logger.getLogger(TurmaController.class.getName());
    private TurmaDAO turmaDAO;

    public TurmaController(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    /* ===== CRUD ===== */

    public boolean salvar(Turma t) {
        return turmaDAO.salvar(t);
    }

    public boolean alterar(Turma t) {
        return turmaDAO.alterar(t);
    }

    public boolean excluir(int id) {
        return turmaDAO.excluir(id);
    }

    public Turma pesquisar(int id) {
        return turmaDAO.pesquisar(id);
    }

    /* ===== Métodos para a View preencher combos ===== */

    public HashMap<String, Integer> carregarDisciplinas(JComboBox<String> combo) {
        HashMap<String, Integer> mapa = new HashMap<>();
        combo.removeAllItems();
        combo.addItem("Selecione a disciplina...");

        String sql = "SELECT id, nome_disciplina FROM disciplina ORDER BY nome_disciplina";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_disciplina");
                String item = id + " - " + nome;
                combo.addItem(item);
                mapa.put(item, id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao carregar disciplinas: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar disciplinas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return mapa;
    }

    public HashMap<String, Integer> carregarProfessores(JComboBox<String> combo) {
        HashMap<String, Integer> mapa = new HashMap<>();
        combo.removeAllItems();
        combo.addItem("Selecione o professor...");

        String sql = "SELECT pe.id, pe.nome FROM professor pr " +
                     "JOIN pessoa pe ON pe.id = pr.id_pessoa ORDER BY pe.nome";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String item = id + " - " + nome;
                combo.addItem(item);
                mapa.put(item, id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao carregar professores: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar professores: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return mapa;
    }

    public HashMap<String, Integer> carregarPeriodos(JComboBox<String> combo) {
        HashMap<String, Integer> mapa = new HashMap<>();
        combo.removeAllItems();
        combo.addItem("Selecione o período...");

        String sql = "SELECT id, nome_periodo FROM periodo ORDER BY nome_periodo";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_periodo");
                String item = id + " - " + nome;
                combo.addItem(item);
                mapa.put(item, id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao carregar períodos: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar períodos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return mapa;
    }
}

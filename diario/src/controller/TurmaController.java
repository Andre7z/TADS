package controller;

import model.Turma;
import dao.TurmaDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class TurmaController {

    private static final Logger logger = LogManager.getLogger(TurmaController.class);

    private final TurmaDAO turmaDAO;

    public TurmaController(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    /* ================== CRUD ================== */

    public boolean salvar(Turma t) {
        logger.info("Iniciando salvar Turma");
        boolean ok = turmaDAO.salvar(t);
        logger.info("Resultado salvar Turma={} id={}", ok, t.getId());
        return ok;
    }

    public boolean alterar(Turma t) {
        logger.info("Iniciando alterar Turma id={}", t.getId());
        boolean ok = turmaDAO.alterar(t);
        logger.info("Resultado alterar Turma={}", ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Turma id={}", id);
        boolean ok = turmaDAO.excluir(id);
        logger.info("Resultado excluir Turma={}", ok);
        return ok;
    }

    public Turma pesquisar(int id) {
        logger.info("Iniciando pesquisar Turma id={}", id);
        Turma t = turmaDAO.pesquisar(id);
        logger.info("Turma encontrada? {}", (t != null));
        return t;
    }

    public List<Turma> listarTodos() {
        logger.info("Iniciando listarTodos Turma");
        List<Turma> lista = turmaDAO.listarTodos();
        logger.info("Total de turmas retornadas={}", lista.size());
        return lista;
    }

    /* ================== Combos ================== */

    // Estes métodos só manipulam o JComboBox; a busca no banco fica em métodos do DAO

    public HashMap<String, Integer> carregarDisciplinas(JComboBox<String> combo) {
        logger.info("Carregando disciplinas para combo");
        combo.removeAllItems();
        combo.addItem("Selecione a disciplina...");

        HashMap<String, Integer> mapa = turmaDAO.buscarDisciplinasParaCombo();
        for (String item : mapa.keySet()) {
            combo.addItem(item);
        }
        logger.info("Total de disciplinas carregadas={}", mapa.size());
        return mapa;
    }

    public HashMap<String, Integer> carregarProfessores(JComboBox<String> combo) {
        logger.info("Carregando professores para combo");
        combo.removeAllItems();
        combo.addItem("Selecione o professor...");

        HashMap<String, Integer> mapa = turmaDAO.buscarProfessoresParaCombo();
        for (String item : mapa.keySet()) {
            combo.addItem(item);
        }
        logger.info("Total de professores carregados={}", mapa.size());
        return mapa;
    }

    public HashMap<String, Integer> carregarPeriodos(JComboBox<String> combo) {
        logger.info("Carregando períodos para combo");
        combo.removeAllItems();
        combo.addItem("Selecione o período...");

        HashMap<String, Integer> mapa = turmaDAO.buscarPeriodosParaCombo();
        for (String item : mapa.keySet()) {
            combo.addItem(item);
        }
        logger.info("Total de períodos carregados={}", mapa.size());
        return mapa;
    }
}

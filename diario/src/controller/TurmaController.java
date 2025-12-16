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

    /* ================== Combo de Turmas ================== */

    public HashMap<String, Integer> carregarTurmas(JComboBox<String> combo) {
        logger.info("Carregando turmas para combo");
        combo.removeAllItems();
        combo.addItem("Selecione a turma...");

        HashMap<String, Integer> mapa = turmaDAO.buscarTurmasParaCombo();
        for (String item : mapa.keySet()) {
            combo.addItem(item);
        }
        logger.info("Total de turmas carregadas={}", mapa.size());
        return mapa;
    }
}

package controller;

import java.util.logging.Logger;
import dao.TurmaDAO;
import model.Turma;

public class TurmaController {
    private static final Logger logger = Logger.getLogger(TurmaController.class.getName());
    private TurmaDAO dao;

    public TurmaController(TurmaDAO dao) {
        this.dao = dao;
    }

    public boolean salvar(Turma t) {
        logger.info("Iniciando salvar Turma");
        boolean ok = dao.salvar(t);
        logger.info("Resultado salvar Turma=" + ok);
        return ok;
    }

    public boolean alterar(Turma t) {
        logger.info("Iniciando alterar Turma id=" + t.getId());
        boolean ok = dao.alterar(t);
        logger.info("Resultado alterar Turma=" + ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Turma id=" + id);
        boolean ok = dao.excluir(id);
        logger.info("Resultado excluir Turma=" + ok);
        return ok;
    }

    public Turma pesquisar(int id) {
        logger.info("Iniciando pesquisar Turma id=" + id);
        Turma t = dao.pesquisar(id);
        logger.info("Turma encontrada? " + (t != null));
        return t;
    }
}

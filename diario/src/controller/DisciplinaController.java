package controller;

import java.util.logging.Logger;
import dao.DisciplinaDAO;
import model.Disciplina;

public class DisciplinaController {
    private static final Logger logger = Logger.getLogger(DisciplinaController.class.getName());
    private DisciplinaDAO dao;

    public DisciplinaController(DisciplinaDAO dao) {
        this.dao = dao;
    }

    public boolean salvar(Disciplina d) {
        logger.info("Iniciando salvar Disciplina");
        boolean ok = dao.salvar(d);
        logger.info("Resultado salvar Disciplina=" + ok);
        return ok;
    }

    public boolean alterar(Disciplina d) {
        logger.info("Iniciando alterar Disciplina id=" + d.getId());
        boolean ok = dao.alterar(d);
        logger.info("Resultado alterar Disciplina=" + ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Disciplina id=" + id);
        boolean ok = dao.excluir(id);
        logger.info("Resultado excluir Disciplina=" + ok);
        return ok;
    }

    public Disciplina pesquisar(int id) {
        logger.info("Iniciando pesquisar Disciplina id=" + id);
        Disciplina d = dao.pesquisar(id);
        logger.info("Disciplina encontrada? " + (d != null));
        return d;
    }
}

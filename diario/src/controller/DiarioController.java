package controller;

import java.util.logging.Logger;
import dao.DiarioDAO;
import model.Diario;

public class DiarioController {
    private static final Logger logger = Logger.getLogger(DiarioController.class.getName());
    private DiarioDAO dao;

    public DiarioController(DiarioDAO dao) {
        this.dao = dao;
    }

    public boolean salvar(Diario d) {
        logger.info("Iniciando salvar Diario");
        boolean ok = dao.salvar(d);
        logger.info("Resultado salvar Diario=" + ok);
        return ok;
    }

    public boolean alterar(Diario d) {
        logger.info("Iniciando alterar Diario id=" + d.getId());
        boolean ok = dao.alterar(d);
        logger.info("Resultado alterar Diario=" + ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Diario id=" + id);
        boolean ok = dao.excluir(id);
        logger.info("Resultado excluir Diario=" + ok);
        return ok;
    }

    public Diario pesquisar(int id) {
        logger.info("Iniciando pesquisar Diario id=" + id);
        Diario d = dao.pesquisar(id);
        logger.info("Diario encontrado? " + (d != null));
        return d;
    }
}

package controller;

import dao.PeriodoDAO;
import model.Periodo;

import java.util.List;
import java.util.logging.Logger;

public class PeriodoController {

    private static final Logger logger = Logger.getLogger(PeriodoController.class.getName());
    private PeriodoDAO dao;

    public PeriodoController(PeriodoDAO dao) {
        this.dao = dao;
    }

    public boolean salvar(Periodo p) {
        logger.info("Iniciando salvar Periodo");
        boolean ok = dao.salvar(p);
        logger.info("Resultado salvar Periodo=" + ok + " id=" + p.getId());
        return ok;
    }

    public boolean alterar(Periodo p) {
        logger.info("Iniciando alterar Periodo id=" + p.getId());
        boolean ok = dao.alterar(p);
        logger.info("Resultado alterar Periodo=" + ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Periodo id=" + id);
        boolean ok = dao.excluir(id);
        logger.info("Resultado excluir Periodo=" + ok);
        return ok;
    }

    public Periodo pesquisar(int id) {
        logger.info("Iniciando pesquisar Periodo id=" + id);
        Periodo p = dao.pesquisar(id);
        logger.info("Periodo encontrado? " + (p != null));
        return p;
    }

    public List<Periodo> listarTodos() {
        logger.info("Iniciando listarTodos Periodo");
        List<Periodo> lista = dao.listarTodos();
        logger.info("Total de periodos retornados=" + lista.size());
        return lista;
    }
}

package controller;

import dao.DisciplinaDAO;
import model.Disciplina;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisciplinaController {

    private static final Logger logger = LogManager.getLogger(DisciplinaController.class);

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

    public List<Disciplina> listarTodos() {
        logger.info("Iniciando listarTodos Disciplina");
        List<Disciplina> lista = dao.listarTodos();
        logger.info("Total de disciplinas retornadas=" + lista.size());
        return lista;
    }

}

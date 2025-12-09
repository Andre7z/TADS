package controller;

import java.util.logging.Logger;
import dao.PessoaDAO;
import model.Pessoa;

public class PessoaController {
    private static final Logger logger = Logger.getLogger(PessoaController.class.getName());
    private PessoaDAO dao;

    public PessoaController(PessoaDAO dao) {
        this.dao = dao;
    }

    public int salvar(Pessoa p) {
        logger.info("Iniciando salvar Pessoa");
        int id = dao.salvar(p);
        logger.info("Resultado salvar Pessoa id=" + id);
        return id;
    }

    public boolean alterar(Pessoa p) {
        logger.info("Iniciando alterar Pessoa id=" + p.getId());
        boolean ok = dao.alterar(p);
        logger.info("Resultado alterar Pessoa=" + ok);
        return ok;
    }

    public boolean excluir(int id) {
        logger.info("Iniciando excluir Pessoa id=" + id);
        boolean ok = dao.excluir(id);
        logger.info("Resultado excluir Pessoa=" + ok);
        return ok;
    }

    public Pessoa pesquisar(int id) {
        logger.info("Iniciando pesquisar Pessoa id=" + id);
        Pessoa p = dao.pesquisar(id);
        logger.info("Pessoa encontrada? " + (p != null));
        return p;
    }
}

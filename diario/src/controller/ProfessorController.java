package controller;

import dao.PessoaDAO;
import dao.ProfessorDAO;
import model.Professor;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ProfessorController {

    private static final Logger logger = LogManager.getLogger(ProfessorController.class);

    private ProfessorDAO professorDAO;
    private PessoaDAO pessoaDAO;

    public ProfessorController(ProfessorDAO professorDAO, PessoaDAO pessoaDAO) {
        this.professorDAO = professorDAO;
        this.pessoaDAO = pessoaDAO;
    }

    public boolean salvar(Professor p) {
        logger.info("Iniciando salvar Professor");

        // salva dados da pessoa e obtém id gerado
        int idPessoa = pessoaDAO.salvar(p);
        if (idPessoa == 0) {
            logger.warn("Falha ao salvar Pessoa para Professor");
            return false;
        }
        p.setId(idPessoa);

        boolean okProf = professorDAO.salvar(p);
        logger.info("Resultado salvar Professor=" + okProf + " id=" + p.getId());
        return okProf;
    }

    public boolean alterar(Professor p) {
        logger.info("Iniciando alterar Professor id=" + p.getId());

        boolean okPessoa = pessoaDAO.alterar(p);
        boolean okProf   = professorDAO.alterar(p);
        boolean ok = okPessoa && okProf;

        logger.info("Resultado alterar Professor=" + ok);
        return ok;
    }

    public boolean excluir(int idPessoa) {
        logger.info("Iniciando excluir Professor idPessoa=" + idPessoa);
        boolean okProf   = professorDAO.excluir(idPessoa);
        boolean okPessoa = pessoaDAO.excluir(idPessoa);
        boolean ok = okProf && okPessoa;
        logger.info("Resultado excluir Professor=" + ok);
        return ok;
    }

    public Professor pesquisar(int idPessoa) {
        logger.info("Iniciando pesquisar Professor idPessoa=" + idPessoa);
        Professor p = professorDAO.pesquisar(idPessoa);
        logger.info("Professor encontrado? " + (p != null));
        return p;
    }

    public List<Professor> listarTodos() {
        logger.info("Iniciando listarTodos Professor");
        List<Professor> lista = professorDAO.listarTodos();
        logger.info("Total de professores retornados=" + lista.size());
        return lista;
    }
}

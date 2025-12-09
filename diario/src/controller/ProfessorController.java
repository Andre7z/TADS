package controller;

import java.util.logging.Logger;
import dao.ProfessorDAO;
import dao.PessoaDAO;
import model.Professor;

public class ProfessorController {
    private static final Logger logger = Logger.getLogger(ProfessorController.class.getName());
    private ProfessorDAO professorDAO;
    private PessoaDAO pessoaDAO;

    public ProfessorController(ProfessorDAO professorDAO, PessoaDAO pessoaDAO) {
        this.professorDAO = professorDAO;
        this.pessoaDAO = pessoaDAO;
    }

    public boolean salvar(Professor prof) {
        logger.info("Iniciando salvar Professor");
        int idPessoa = pessoaDAO.salvar(prof);
        if (idPessoa == 0) {
            logger.warning("Falha ao salvar Pessoa para Professor");
            return false;
        }
        prof.setId(idPessoa);
        boolean ok = professorDAO.salvar(prof);
        logger.info("Resultado salvar Professor=" + ok);
        return ok;
    }

    public boolean alterar(Professor prof) {
        logger.info("Iniciando alterar Professor id=" + prof.getId());
        boolean okPessoa = pessoaDAO.alterar(prof);
        boolean okProf = professorDAO.alterar(prof);
        boolean ok = okPessoa && okProf;
        logger.info("Resultado alterar Professor=" + ok);
        return ok;
    }

    public boolean excluir(int idPessoa) {
        logger.info("Iniciando excluir Professor idPessoa=" + idPessoa);
        boolean okProf = professorDAO.excluir(idPessoa);
        boolean okPessoa = pessoaDAO.excluir(idPessoa);
        boolean ok = okProf && okPessoa;
        logger.info("Resultado excluir Professor=" + ok);
        return ok;
    }

    public Professor pesquisar(int idPessoa) {
        logger.info("Iniciando pesquisar Professor idPessoa=" + idPessoa);
        Professor prof = professorDAO.pesquisar(idPessoa);
        logger.info("Professor encontrado? " + (prof != null));
        return prof;
    }
}

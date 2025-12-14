package controller;

import dao.AlunoDAO;
import dao.PessoaDAO;
import model.Aluno;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlunoController {
    private static final Logger logger = LogManager.getLogger(AlunoController.class);
    private AlunoDAO alunoDAO;
    private PessoaDAO pessoaDAO;

    public AlunoController(AlunoDAO alunoDAO, PessoaDAO pessoaDAO) {
        this.alunoDAO = alunoDAO;
        this.pessoaDAO = pessoaDAO;
    }

    private boolean validarMatricula (String matricula) {
        if (matricula == null)
            return false;
        return matricula.matches("\\d{10}"); // verifica se a matricula tem 10 dígitos numéricos
    }

    public boolean salvar(Aluno aluno) {
        logger.info("Iniciando salvar Aluno");
        if (!validarMatricula(aluno.getMatricula())) {
            logger.warn("Matrícula inválida: " + aluno.getMatricula());
            return false;
        }

        
        int idPessoa = pessoaDAO.salvar(aluno);
        if (idPessoa == 0) {
            logger.warn("Falha ao salvar Pessoa para Aluno");
            return false;
        }
        aluno.setId(idPessoa);
        boolean ok = alunoDAO.salvar(aluno);
        logger.info("Resultado salvar Aluno=" + ok);
        return ok;
    }

    public boolean alterar(Aluno aluno) {
        logger.info("Iniciando alterar Aluno id=" + aluno.getId());
        if (!validarMatricula(aluno.getMatricula())) {
            logger.warn("Matrícula inválida");
            return false;
        }
        boolean okPessoa = pessoaDAO.alterar(aluno);
        boolean okAluno = alunoDAO.alterar(aluno);
        boolean ok = okPessoa && okAluno;
        logger.info("Resultado alterar Aluno=" + ok);
        return ok;
    }

    public boolean excluir(int idPessoa) {
        logger.info("Iniciando excluir Aluno idPessoa=" + idPessoa);
        boolean okAluno = alunoDAO.excluir(idPessoa);
        boolean okPessoa = pessoaDAO.excluir(idPessoa);
        boolean ok = okAluno && okPessoa;
        logger.info("Resultado excluir Aluno=" + ok);
        return ok;
    }

    public Aluno pesquisar(int idPessoa) {
        logger.info("Iniciando pesquisar Aluno idPessoa=" + idPessoa);
        Aluno a = alunoDAO.pesquisar(idPessoa);
        logger.info("Aluno encontrado? " + (a != null));
        return a;
    }

    public List<Aluno> listarTodos() {
    logger.info("Iniciando listarTodos Aluno");
    List<Aluno> lista = alunoDAO.listarTodos();
    logger.info("Total de alunos retornados=" + lista.size());
    return lista;
}

}

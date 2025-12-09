package controller;

import dao.AlunoDAO;
import model.Aluno;
import java.util.List;

public class AlunoController {

    private AlunoDAO alunoDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }

    public void salvar(Aluno aluno) {
        alunoDAO.salvar(aluno);
    }

    public void atualizar(Aluno aluno) {
        alunoDAO.atualizar(aluno);
    }

    public void deletar(int id) {
        alunoDAO.deletar(id);
    }

    public Aluno buscarPorId(int id) {
        return alunoDAO.buscarPorId(id);
    }

    public List<Aluno> listarTodos() {
        return alunoDAO.listarTodos();
    }
}

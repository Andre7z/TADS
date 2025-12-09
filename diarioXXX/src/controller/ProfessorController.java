package controller;

import dao.ProfessorDAO;
import model.Professor;
import java.util.List;

public class ProfessorController {

    private ProfessorDAO professorDAO;

    public ProfessorController() {
        this.professorDAO = new ProfessorDAO();
    }

    public void salvar(Professor professor) {
        professorDAO.salvar(professor);
    }

    public void atualizar(Professor professor) {
        professorDAO.atualizar(professor);
    }

    public void deletar(int id) {
        professorDAO.deletar(id);
    }

    public Professor buscarPorId(int id) {
        return professorDAO.buscarPorId(id);
    }

    public List<Professor> listarTodos() {
        return professorDAO.listarTodos();
    }
}

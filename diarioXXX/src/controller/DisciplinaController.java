package controller;

import dao.DisciplinaDAO;
import model.Disciplina;
import java.util.List;

public class DisciplinaController {

    private DisciplinaDAO disciplinaDAO;

    public DisciplinaController() {
        this.disciplinaDAO = new DisciplinaDAO();
    }

    public void salvar(Disciplina disciplina) {
        disciplinaDAO.salvar(disciplina);
    }

    public void atualizar(Disciplina disciplina) {
        disciplinaDAO.atualizar(disciplina);
    }

    public void deletar(int id) {
        disciplinaDAO.deletar(id);
    }

    public Disciplina buscarPorId(int id) {
        return disciplinaDAO.buscarPorId(id);
    }

    public List<Disciplina> listarTodos() {
        return disciplinaDAO.listarTodos();
    }
}

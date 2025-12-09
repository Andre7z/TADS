package controller;

import dao.TurmaDAO;
import model.Turma;
import java.util.List;

public class TurmaController {

    private TurmaDAO turmaDAO;

    public TurmaController() {
        this.turmaDAO = new TurmaDAO();
    }

    public void salvar(Turma turma) {
        turmaDAO.salvar(turma);
    }

    public void atualizar(Turma turma) {
        turmaDAO.atualizar(turma);
    }

    public void deletar(int id) {
        turmaDAO.deletar(id);
    }

    public Turma buscarPorId(int id) {
        return turmaDAO.buscarPorId(id);
    }

    public List<Turma> listarTodos() {
        return turmaDAO.listarTodos();
    }
}

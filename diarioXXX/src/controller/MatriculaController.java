package controller;

import dao.MatriculaDAO;
import model.Matricula;
import java.util.List;

public class MatriculaController {

    private MatriculaDAO matriculaDAO;

    public MatriculaController() {
        this.matriculaDAO = new MatriculaDAO();
    }

    public void salvar(Matricula matricula) {
        matriculaDAO.salvar(matricula);
    }

    public void atualizar(Matricula matricula) {
        matriculaDAO.atualizar(matricula);
    }

    public void deletar(int id) {
        matriculaDAO.deletar(id);
    }

    public Matricula buscarPorId(int id) {
        return matriculaDAO.buscarPorId(id);
    }

    public List<Matricula> listarTodos() {
        return matriculaDAO.listarTodos();
    }
}

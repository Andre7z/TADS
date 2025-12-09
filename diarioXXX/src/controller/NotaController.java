package controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.NotaDAO;
import model.Nota;

public class NotaController {

    private NotaDAO notaDAO;
    private static final Logger logger = Logger.getLogger(NotaController.class.getName());

    public NotaController(NotaDAO notaDAO) {
        this.notaDAO = notaDAO;
    }

    public boolean inserir(Nota nota) {
        try {
            if (nota.getValor() < 0 || nota.getValor() > 10) {
                logger.warning("Nota inválida.");
                return false;
            }

            return notaDAO.inserir(nota);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao inserir nota", e);
            return false;
        }
    }

    public List<Nota> listarPorDiario(int diarioId) {
        return notaDAO.listarPorDiario(diarioId);
    }
}

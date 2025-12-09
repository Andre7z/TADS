package controller;

import java.util.List;
import java.util.logging.Logger;

import dao.NotaDAO;
import dao.DiarioDAO;
import model.Nota;
import model.Diario;

public class NotaController {

    private static final Logger logger = Logger.getLogger(NotaController.class.getName());

    private NotaDAO notaDAO;
    private DiarioDAO diarioDAO;

    public NotaController(NotaDAO notaDAO, DiarioDAO diarioDAO) {
        this.notaDAO = notaDAO;
        this.diarioDAO = diarioDAO;
    }

    // Recebe um objeto Nota com idDiario e lista de notas.
    // Grava as notas no banco e atualiza o status do diário pela média.
    public boolean salvarNotasEAtualizarStatus(Nota nota) {
        logger.info("Iniciando salvarNotasEAtualizarStatus para diario=" + nota.getIdDiario());

        Diario d = diarioDAO.pesquisar(nota.getIdDiario());
        if (d == null) {
            logger.warning("Diario inexistente para id=" + nota.getIdDiario());
            return false;
        }

        if (nota.getNotas() == null || nota.getNotas().isEmpty()) {
            logger.warning("Nenhuma nota informada");
            return false;
        }
        for (Double v : nota.getNotas()) {
            if (v == null || v < 0 || v > 10) {
                logger.warning("Nota inválida: " + v);
                return false;
            }
        }

        // sobrescreve todas as notas desse diário
        notaDAO.excluirPorDiario(nota.getIdDiario());
        if (!notaDAO.salvar(nota)) {
            logger.warning("Falha ao salvar notas");
            return false;
        }

        // média aritmética de todas as notas gravadas
        List<Double> lista = notaDAO.listarNotasPorDiario(nota.getIdDiario());
        if (lista.isEmpty()) {
            logger.warning("Nenhuma nota encontrada após salvar");
            return false;
        }

        double soma = 0;
        for (Double v : lista) soma += v;
        double media = soma / lista.size();
        boolean aprovado = media >= 6.0;

        d.setStatus(aprovado);
        boolean okDiario = diarioDAO.alterar(d);

        logger.info("Média=" + media +
                    " aprovado=" + aprovado +
                    " diarioAtualizado=" + okDiario);

        return okDiario;
    }
}

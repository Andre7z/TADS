package controller;

import dao.DiarioDAO;
import dao.NotaDAO;
import model.Diario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DiarioController {

    private static final Logger logger = Logger.getLogger(DiarioController.class.getName());

    private final DiarioDAO diarioDAO;
    private final NotaDAO notaDAO;
    private final Connection conn;

    public DiarioController(DiarioDAO diarioDAO, NotaDAO notaDAO, Connection conn) {
        this.diarioDAO = diarioDAO;
        this.notaDAO = notaDAO;
        this.conn = conn;
    }

    public boolean salvar(Diario d) {
        logger.info("Iniciando salvar Diario");
        boolean ok = diarioDAO.salvar(d);
        logger.info("Resultado salvar Diario=" + ok + " id=" + d.getId());
        return ok;
    }

    public boolean alterar(Diario d) {
        logger.info("Iniciando alterar Diario id=" + d.getId());
        boolean ok = diarioDAO.alterar(d);
        logger.info("Resultado alterar Diario=" + ok);
        return ok;
    }

    // Exclui notas primeiro e depois o diário, dentro de transação
    public boolean excluir(int id) {
        logger.info("Iniciando excluir Diario id=" + id);
        try {
            conn.setAutoCommit(false);

            logger.info("Excluindo notas do diario id=" + id);
            boolean okNotas = notaDAO.excluirPorDiario(id);
            logger.info("Resultado excluir notas=" + okNotas);

            logger.info("Excluindo diario id=" + id);
            boolean okDiario = diarioDAO.excluir(id);
            logger.info("Resultado excluir diario=" + okDiario);

            if (okDiario) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Erro em transação excluir Diario: " + e.getMessage());
            try { conn.rollback(); } catch (SQLException ignored) {}
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    public Diario pesquisar(int id) {
        logger.info("Iniciando pesquisar Diario id=" + id);
        Diario d = diarioDAO.pesquisar(id);
        logger.info("Diario encontrado? " + (d != null));
        return d;
    }
}

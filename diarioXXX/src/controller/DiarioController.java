package controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.DiarioDAO;
import dao.NotaDAO;
import model.Diario;
import model.Nota;

public class DiarioController {

    private DiarioDAO diarioDAO;
    private NotaDAO notaDAO;
    private static final Logger logger = Logger.getLogger(DiarioController.class.getName());

    public DiarioController(DiarioDAO diarioDAO, NotaDAO notaDAO) {
        this.diarioDAO = diarioDAO;
        this.notaDAO = notaDAO;
    }

    // ----------------------------------------
    //       INSERIR DIÁRIO
    // ----------------------------------------
    public boolean inserir(Diario diario, List<Nota> notas) {
        try {
            // validações de regra de negócio
            if (diario.getAluno() == null ||
                diario.getDisciplina() == null ||
                diario.getPeriodo() == null ||
                diario.getTurma() == null) {

                logger.warning("Erro: diário precisa de aluno, disciplina, período e turma.");
                return false;
            }

            int idGerado = diarioDAO.inserir(diario);

            if (idGerado <= 0) {
                logger.warning("Erro ao criar diário, ID inválido retornado.");
                return false;
            }

            // cadastrar notas
            for (Nota n : notas) {
                if (n.getValor() < 0 || n.getValor() > 10) {
                    logger.warning("Nota fora do intervalo permitido (0 a 10).");
                    return false;
                }
                n.setDiarioId(idGerado);
                notaDAO.inserir(n);
            }

            logger.info("Diário criado com sucesso. ID=" + idGerado);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao salvar diário", e);
            return false;
        }
    }

    // ----------------------------------------
    //    ATUALIZAR DIÁRIO + NOTAS
    // ----------------------------------------
    public boolean atualizar(Diario diario, List<Nota> notas) {
        try {
            boolean ok = diarioDAO.atualizar(diario);

            if (!ok) {
                logger.warning("Falha ao atualizar diário.");
                return false;
            }

            // apagar notas antigas
            notaDAO.excluirPorDiario(diario.getId());

            // inserir novas
            for (Nota n : notas) {
                if (n.getValor() < 0 || n.getValor() > 10) {
                    logger.warning("Nota fora do intervalo permitido (0 a 10).");
                    return false;
                }
                n.setDiarioId(diario.getId());
                notaDAO.inserir(n);
            }

            logger.info("Diário atualizado com sucesso.");
            return true;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar diário", e);
            return false;
        }
    }

    // ----------------------------------------
    //        EXCLUIR
    // ----------------------------------------
    public boolean excluir(int id) {
        try {
            notaDAO.excluirPorDiario(id);
            boolean ok = diarioDAO.excluir(id);

            if (ok) logger.info("Diário excluído. ID=" + id);
            else logger.warning("Erro ao excluir diário.");

            return ok;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao excluir diário", e);
            return false;
        }
    }

    // ----------------------------------------
    //        BUSCAR
    // ----------------------------------------
    public Diario buscar(int id) {
        try {
            Diario d = diarioDAO.buscar(id);
            if (d != null) {
                List<Nota> notas = notaDAO.listarPorDiario(id);
                d.setNotas(notas);
            }
            return d;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar diário", e);
            return null;
        }
    }

    // ----------------------------------------
    //        LISTAR TODOS
    // ----------------------------------------
    public List<Diario> listar() {
        try {
            List<Diario> diarios = diarioDAO.listar();

            for (Diario d : diarios) {
                d.setNotas(notaDAO.listarPorDiario(d.getId()));
            }

            return diarios;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao listar diários", e);
            return null;
        }
    }
}

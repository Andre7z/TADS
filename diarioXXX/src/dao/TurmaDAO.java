package dao;

import model.Turma;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class TurmaDAO {

    private static final Logger logger = Logger.getLogger(TurmaDAO.class.getName());
    private Connection conn;

    public TurmaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inserir(Turma turma) {
        String sql = "INSERT INTO turma (nome) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.executeUpdate();

            logger.info("Turma inserida com sucesso.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir turma.", e);
            return false;
        }
    }

    public boolean atualizar(Turma turma) {
        String sql = "UPDATE turma SET nome=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getId());

            stmt.executeUpdate();
            logger.info("Turma atualizada.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar turma.", e);
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM turma WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Turma deletada.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar turma.", e);
            return false;
        }
    }

    public Turma buscarPorId(int id) {
        String sql = "SELECT * FROM turma WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Turma(
                    rs.getInt("id"),
                    rs.getString("nome")
                );
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao buscar turma.", e);
        }

        return null;
    }

    public List<Turma> listarTodas() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM turma ORDER BY id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(
                    new Turma(rs.getInt("id"), rs.getString("nome"))
                );
            }

            logger.info("Turmas carregadas.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar turmas.", e);
        }

        return lista;
    }
}

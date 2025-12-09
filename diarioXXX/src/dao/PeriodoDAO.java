package dao;

import model.Periodo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class PeriodoDAO {

    private static final Logger logger = Logger.getLogger(PeriodoDAO.class.getName());
    private Connection conn;

    public PeriodoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inserir(Periodo p) {
        String sql = "INSERT INTO periodo (nome) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.executeUpdate();

            logger.info("Período inserido.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir período.", e);
            return false;
        }
    }

    public boolean atualizar(Periodo p) {
        String sql = "UPDATE periodo SET nome=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getId());

            stmt.executeUpdate();
            logger.info("Período atualizado.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar período.", e);
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM periodo WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Período deletado.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar período.", e);
            return false;
        }
    }

    public Periodo buscarPorId(int id) {
        String sql = "SELECT * FROM periodo WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Periodo(
                    rs.getInt("id"),
                    rs.getString("nome")
                );
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao buscar período.", e);
        }

        return null;
    }

    public List<Periodo> listarTodos() {
        List<Periodo> lista = new ArrayList<>();
        String sql = "SELECT * FROM periodo ORDER BY id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Periodo(
                    rs.getInt("id"),
                    rs.getString("nome")
                ));
            }

            logger.info("Períodos carregados.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar períodos.", e);
        }

        return lista;
    }
}

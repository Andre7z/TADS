package dao;

import model.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class DisciplinaDAO {

    private static final Logger logger = Logger.getLogger(DisciplinaDAO.class.getName());
    private Connection conn;

    public DisciplinaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inserir(Disciplina d) {
        String sql = "INSERT INTO disciplina (nome, carga_horaria) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getCargaHoraria());

            stmt.executeUpdate();
            logger.info("Disciplina inserida.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir disciplina.", e);
            return false;
        }
    }

    public boolean atualizar(Disciplina d) {
        String sql = "UPDATE disciplina SET nome=?, carga_horaria=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getCargaHoraria());
            stmt.setInt(3, d.getId());

            stmt.executeUpdate();
            logger.info("Disciplina atualizada.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar disciplina.", e);
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM disciplina WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Disciplina removida.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar disciplina.", e);
            return false;
        }
    }

    public Disciplina buscarPorId(int id) {
        String sql = "SELECT * FROM disciplina WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Disciplina(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("carga_horaria")
                );
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao buscar disciplina.", e);
        }

        return null;
    }

    public List<Disciplina> listarTodas() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplina ORDER BY id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Disciplina(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("carga_horaria")
                ));
            }

            logger.info("Disciplinas carregadas.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar disciplinas.", e);
        }

        return lista;
    }
}

package dao;

import model.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ProfessorDAO {

    private static final Logger logger = Logger.getLogger(ProfessorDAO.class.getName());
    private Connection conn;

    public ProfessorDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean inserir(Professor professor) {
        String sql = "INSERT INTO professor (nome, endereco, telefone, email, matricula) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEndereco());
            stmt.setString(3, professor.getTelefone());
            stmt.setString(4, professor.getEmail());
            stmt.setString(5, professor.getMatricula());

            stmt.executeUpdate();
            logger.info("Professor inserido com sucesso.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir professor.", e);
            return false;
        }
    }

    public boolean atualizar(Professor professor) {
        String sql = "UPDATE professor SET nome=?, endereco=?, telefone=?, email=?, matricula=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEndereco());
            stmt.setString(3, professor.getTelefone());
            stmt.setString(4, professor.getEmail());
            stmt.setString(5, professor.getMatricula());
            stmt.setInt(6, professor.getId());

            stmt.executeUpdate();
            logger.info("Professor atualizado com sucesso.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar professor.", e);
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM professor WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Professor apagado.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar professor.", e);
            return false;
        }
    }

    public Professor buscarPorId(int id) {
        String sql = "SELECT * FROM professor WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Professor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),

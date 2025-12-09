package dao;

import model.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class AlunoDAO {

    private static final Logger logger = Logger.getLogger(AlunoDAO.class.getName());
    private Connection conn;

    public AlunoDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public boolean inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, endereco, telefone, email, matricula, nome_pai, nome_mae) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEndereco());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setInt(5, aluno.getMatricula());
            stmt.setString(6, aluno.getNome_pai());
            stmt.setString(7, aluno.getNome_mae());

            stmt.executeUpdate();
            logger.info("Aluno inserido com sucesso.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir aluno.", e);
            return false;
        }
    }

    // UPDATE
    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET nome=?, endereco=?, telefone=?, email=?, matricula=?, nome_pai=?, nome_mae=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEndereco());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setInt(5, aluno.getMatricula());
            stmt.setString(6, aluno.getNome_pai());
            stmt.setString(7, aluno.getNome_mae());
            stmt.setInt(8, aluno.getId());

            stmt.executeUpdate();
            logger.info("Aluno atualizado com sucesso.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar aluno.", e);
            return false;
        }
    }

    // DELETE
    public boolean deletar(int id) {
        String sql = "DELETE FROM aluno WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            logger.info("Aluno deletado com sucesso.");
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar aluno.", e);
            return false;
        }
    }

    // SELECT ONE
    public Aluno buscarPorId(int id) {
        String sql = "SELECT * FROM aluno WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getInt("matricula"),
                    rs.getString("nome_pai"),
                    rs.getString("nome_mae")
                );
                logger.info("Aluno encontrado.");
                return aluno;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao buscar aluno.", e);
        }

        return null;
    }

    // SELECT ALL
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getInt("matricula"),
                    rs.getString("nome_pai"),
                    rs.getString("nome_mae")
                );

                lista.add(aluno);
            }

            logger.info("Lista de alunos carregada.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar alunos.", e);
        }

        return lista;
    }
}

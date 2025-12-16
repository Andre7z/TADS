package dao;

import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AlunoDAO {

    private static final Logger logger = LogManager.getLogger(AlunoDAO.class);

    /* ========= CRUD BÁSICO ========= */

    public boolean salvar(Aluno a) {
        String sql = "INSERT INTO aluno (id_pessoa, matricula, nome_pai, nome_mae) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getId());
            ps.setString(2, a.getMatricula());
            ps.setString(3, a.getNomePai());
            ps.setString(4, a.getNomeMae());

            int linhas = ps.executeUpdate();
            logger.info("Aluno salvo, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao salvar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Aluno a) {
        String sql = "UPDATE aluno SET matricula=?, nome_pai=?, nome_mae=? " +
                "WHERE id_pessoa=?";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getNomePai());
            ps.setString(3, a.getNomeMae());
            ps.setInt(4, a.getId());

            int linhas = ps.executeUpdate();
            logger.info("Aluno alterado, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao alterar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idPessoa) {
        String sql = "DELETE FROM aluno WHERE id_pessoa=?";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPessoa);
            int linhas = ps.executeUpdate();
            logger.info("Aluno excluído, linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }

    // Pesquisa um aluno específico (join com pessoa para trazer os dados completos)
    public Aluno pesquisar(int idPessoa) {
        String sql = "SELECT pe.id, pe.nome, pe.endereco, pe.telefone, pe.email, " +
                "a.matricula, a.nome_pai, a.nome_mae " +
                "FROM aluno a JOIN pessoa pe ON pe.id = a.id_pessoa " +
                "WHERE pe.id=?";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPessoa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Aluno a = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("nome_pai"),
                        rs.getString("nome_mae"));
                logger.info("Aluno encontrado id=" + idPessoa);
                return a;
            } else {
                logger.info("Aluno não encontrado id=" + idPessoa);
            }
        } catch (SQLException e) {
            logger.error("Erro ao pesquisar aluno: " + e.getMessage());
        }
        return null;
    }

    /* ========= LISTAGEM PARA COMBOS / TELAS ========= */

    // Lista todos os alunos (usado, por exemplo, para carregar combo "id - nome")
    public List<Aluno> listarTodos() {
        String sql = "SELECT pe.id, pe.nome, pe.endereco, pe.telefone, pe.email, " +
                "a.matricula, a.nome_pai, a.nome_mae " +
                "FROM aluno a JOIN pessoa pe ON pe.id = a.id_pessoa " +
                "ORDER BY pe.nome";
        List<Aluno> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aluno a = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("nome_pai"),
                        rs.getString("nome_mae"));
                lista.add(a);
            }
            logger.info("Lista de alunos carregada. Quantidade=" + lista.size());
        } catch (SQLException e) {
            logger.error("Erro ao listar alunos: " + e.getMessage());
        }

        return lista;
    }
}

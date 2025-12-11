package dao;

import model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProfessorDAO {

    private static final Logger logger = Logger.getLogger(ProfessorDAO.class.getName());
    private Connection conn;

    public ProfessorDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Professor p) {
        String sql = "INSERT INTO professor (id_pessoa, matricula) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getId());            // id da tabela pessoa
            ps.setString(2, p.getMatricula());
            int linhas = ps.executeUpdate();
            logger.info("Professor salvo, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao salvar professor: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Professor p) {
        String sql = "UPDATE professor SET matricula=? WHERE id_pessoa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getMatricula());
            ps.setInt(2, p.getId());
            int linhas = ps.executeUpdate();
            logger.info("Professor alterado, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao alterar professor: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idPessoa) {
        String sql = "DELETE FROM professor WHERE id_pessoa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPessoa);
            int linhas = ps.executeUpdate();
            logger.info("Professor excluído, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir professor: " + e.getMessage());
            return false;
        }
    }

    public Professor pesquisar(int idPessoa) {
        String sql = "SELECT pe.id, pe.nome, pe.endereco, pe.telefone, pe.email, " +
                     "pr.matricula " +
                     "FROM professor pr JOIN pessoa pe ON pe.id = pr.id_pessoa " +
                     "WHERE pe.id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPessoa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Professor p = new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("matricula"));
                logger.info("Professor encontrado idPessoa=" + idPessoa);
                return p;
            } else {
                logger.info("Professor não encontrado idPessoa=" + idPessoa);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar professor: " + e.getMessage());
        }
        return null;
    }

    public List<Professor> listarTodos() {
        String sql = "SELECT pe.id, pe.nome, pe.endereco, pe.telefone, pe.email, " +
                     "pr.matricula " +
                     "FROM professor pr JOIN pessoa pe ON pe.id = pr.id_pessoa " +
                     "ORDER BY pe.nome";
        List<Professor> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Professor p = new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("matricula"));
                lista.add(p);
            }
            logger.info("Lista de professores carregada. Quantidade=" + lista.size());
        } catch (SQLException e) {
            logger.severe("Erro ao listar professores: " + e.getMessage());
        }
        return lista;
    }
}

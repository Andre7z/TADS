package dao;

import model.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisciplinaDAO {

    private static final Logger logger = LogManager.getLogger(DisciplinaDAO.class);
    private Connection conn;

    public DisciplinaDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERT
    public boolean salvar(Disciplina d) {
        String sql = "INSERT INTO disciplina (nome_disciplina, id_professor) "
                   + "VALUES (?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNomeDisciplina());
            ps.setInt(2, d.getIdProfessor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id");
                d.setId(idGerado);
                logger.info("Disciplina salva com id=" + idGerado);
                return true;
            }
            logger.warn("Insert de disciplina não retornou id");
        } catch (SQLException e) {
            logger.error("Erro ao salvar disciplina: " + e.getMessage(), e);
        }
        return false;
    }

    // UPDATE
    public boolean alterar(Disciplina d) {
        String sql = "UPDATE disciplina "
                   + "SET nome_disciplina = ?, id_professor = ? "
                   + "WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNomeDisciplina());
            ps.setInt(2, d.getIdProfessor());
            ps.setInt(3, d.getId());
            int linhas = ps.executeUpdate();
            logger.info("Disciplina alterada, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao alterar disciplina: " + e.getMessage(), e);
            return false;
        }
    }

    // DELETE
    public boolean excluir(int id) {
        String sql = "DELETE FROM disciplina WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Disciplina excluída, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao excluir disciplina: " + e.getMessage(), e);
            return false;
        }
    }

    // SELECT por ID
    public Disciplina pesquisar(int id) {
        String sql = "SELECT id, nome_disciplina, id_professor "
                   + "FROM disciplina WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Disciplina d = new Disciplina(
                        rs.getInt("id"),
                        rs.getString("nome_disciplina"),
                        rs.getInt("id_professor")
                    );
                    logger.info("Disciplina encontrada id=" + id);
                    return d;
                } else {
                    logger.info("Disciplina não encontrada id=" + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao pesquisar disciplina: " + e.getMessage(), e);
        }
        return null;
    }

    // SELECT * ORDER BY nome
    public List<Disciplina> listarTodos() {
        String sql = "SELECT id, nome_disciplina, id_professor "
                   + "FROM disciplina ORDER BY nome_disciplina";
        List<Disciplina> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Disciplina d = new Disciplina(
                    rs.getInt("id"),
                    rs.getString("nome_disciplina"),
                    rs.getInt("id_professor")
                );
                lista.add(d);
            }
            logger.info("Lista de disciplinas carregada. Quantidade=" + lista.size());
        } catch (SQLException e) {
            logger.error("Erro ao listar disciplinas: " + e.getMessage(), e);
        }
        return lista;
    }
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.Diario;

public class DiarioDAO {

    private static final Logger logger = Logger.getLogger(DiarioDAO.class.getName());
    private Connection conn;

    public DiarioDAO(Connection conn) {
        this.conn = conn;
    }

    // salvar(): insere e retorna o id gerado no objeto
    public boolean salvar(Diario d) {
        String sql = "INSERT INTO diario (id_disciplina, id_periodo, id_turma, id_aluno, status) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getIdDisciplina());
            ps.setInt(2, d.getIdPeriodo());
            ps.setInt(3, d.getIdTurma());
            ps.setInt(4, d.getIdAluno());
            ps.setBoolean(5, d.isStatus());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id");
                d.setId(idGerado);
                logger.info("Diario salvo com id=" + idGerado);
                return true;
            }
            logger.warning("Insert de diario não retornou id");
        } catch (SQLException e) {
            logger.severe("Erro ao salvar diario: " + e.getMessage());
        }
        return false;
    }

    // alterar(): atualiza todos os campos
    public boolean alterar(Diario d) {
        String sql = "UPDATE diario SET id_disciplina=?, id_periodo=?, id_turma=?, " +
                "id_aluno=?, status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getIdDisciplina());
            ps.setInt(2, d.getIdPeriodo());
            ps.setInt(3, d.getIdTurma());
            ps.setInt(4, d.getIdAluno());
            ps.setBoolean(5, d.isStatus());
            ps.setInt(6, d.getId());
            int linhas = ps.executeUpdate();
            logger.info("Diario alterado, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao alterar diario: " + e.getMessage());
            return false;
        }
    }

    // excluir(): remove pelo id
    public boolean excluir(int id) {
        String sql = "DELETE FROM diario WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Diario excluído, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir diario: " + e.getMessage());
            return false;
        }
    }

    // pesquisar(): busca pelo id
    public Diario pesquisar(int id) {
        String sql = "SELECT * FROM diario WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Diario d = new Diario(
                        rs.getInt("id"),
                        rs.getInt("id_disciplina"),
                        rs.getInt("id_periodo"),
                        rs.getInt("id_turma"),
                        rs.getInt("id_aluno"),
                        rs.getBoolean("status"));
                logger.info("Diario encontrado id=" + id);
                return d;
            } else {
                logger.info("Diario não encontrado id=" + id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar diario: " + e.getMessage());
        }
        return null;
    }
    public List<Diario> listarTodos() {
    String sql = "SELECT * FROM diario ORDER BY id";
    List<Diario> lista = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Diario d = new Diario(
                rs.getInt("id"),
                rs.getInt("id_disciplina"),
                rs.getInt("id_periodo"),
                rs.getInt("id_turma"),
                rs.getInt("id_aluno"),
                rs.getBoolean("status")
            );
            lista.add(d);
        }
        logger.info("Lista de diarios carregada. Quantidade=" + lista.size());
    } catch (SQLException e) {
        logger.severe("Erro ao listar diarios: " + e.getMessage());
    }
    return lista;
}
}

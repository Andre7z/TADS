package dao;

import model.Diario;
import model.Disciplina;
import model.Periodo;
import model.Turma;
import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiarioDAO {

    private static final Logger logger = LogManager.getLogger(DiarioDAO.class);
    private final Connection conn;

    public DiarioDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERT
    public boolean salvar(Diario d) {
        String sql = "INSERT INTO diario (id_disciplina, id_periodo, id_turma, id_aluno, status) "
                   + "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getDisciplina().getId());
            ps.setInt(2, d.getPeriodo().getId());
            ps.setInt(3, d.getTurma().getId());
            ps.setInt(4, d.getAluno().getId());
            ps.setBoolean(5, d.isStatus());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idGerado = rs.getInt("id");
                    d.setId(idGerado);
                    logger.info("Diario salvo com id={}", idGerado);
                    return true;
                }
            }
            logger.warn("Insert de diario não retornou id");
        } catch (SQLException e) {
            logger.error("Erro ao salvar diario: {}", e.getMessage(), e);
        }
        return false;
    }

    // UPDATE
    public boolean alterar(Diario d) {
        String sql = "UPDATE diario SET id_disciplina = ?, id_periodo = ?, id_turma = ?, "
                   + "id_aluno = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getDisciplina().getId());
            ps.setInt(2, d.getPeriodo().getId());
            ps.setInt(3, d.getTurma().getId());
            ps.setInt(4, d.getAluno().getId());
            ps.setBoolean(5, d.isStatus());
            ps.setInt(6, d.getId());

            int linhas = ps.executeUpdate();
            logger.info("Diario alterado, linhas afetadas={}", linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao alterar diario: {}", e.getMessage(), e);
            return false;
        }
    }

    // DELETE
    public boolean excluir(int id) {
        String sql = "DELETE FROM diario WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Diario excluído, linhas afetadas={}", linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao excluir diario: {}", e.getMessage(), e);
            return false;
        }
    }

    // SELECT por ID
    public Diario pesquisar(int id) {
        String sql = "SELECT id, id_disciplina, id_periodo, id_turma, id_aluno, status "
                   + "FROM diario WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Disciplina disc = new Disciplina();
                    disc.setId(rs.getInt("id_disciplina"));

                    Periodo per = new Periodo();
                    per.setId(rs.getInt("id_periodo"));

                    Turma tur = new Turma();
                    tur.setId(rs.getInt("id_turma"));

                    Aluno alu = new Aluno();
                    alu.setId(rs.getInt("id_aluno"));

                    Diario d = new Diario(
                        rs.getInt("id"),
                        disc, per, tur, alu,
                        rs.getBoolean("status")
                    );
                    logger.info("Diario encontrado id={}", id);
                    return d;
                } else {
                    logger.info("Diario não encontrado id={}", id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao pesquisar diario: {}", e.getMessage(), e);
        }
        return null;
    }

    // SELECT *
    public List<Diario> listarTodos() {
        String sql = "SELECT id, id_disciplina, id_periodo, id_turma, id_aluno, status "
                   + "FROM diario ORDER BY id";
        List<Diario> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Disciplina disc = new Disciplina();
                disc.setId(rs.getInt("id_disciplina"));

                Periodo per = new Periodo();
                per.setId(rs.getInt("id_periodo"));

                Turma tur = new Turma();
                tur.setId(rs.getInt("id_turma"));

                Aluno alu = new Aluno();
                alu.setId(rs.getInt("id_aluno"));

                Diario d = new Diario(
                    rs.getInt("id"),
                    disc, per, tur, alu,
                    rs.getBoolean("status")
                );
                lista.add(d);
            }
            logger.info("Lista de diarios carregada. Quantidade={}", lista.size());
        } catch (SQLException e) {
            logger.error("Erro ao listar diarios: {}", e.getMessage(), e);
        }
        return lista;
    }
}

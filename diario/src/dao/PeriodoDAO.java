package dao;

import model.Periodo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PeriodoDAO {

    private static final Logger logger = Logger.getLogger(PeriodoDAO.class.getName());
    private Connection conn;

    public PeriodoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Periodo p) {
        String sql = "INSERT INTO periodo (nome_periodo) VALUES (?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNomePeriodo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id");
                p.setId(idGerado);
                logger.info("Periodo salvo com id=" + idGerado);
                return true;
            }
            logger.warning("Insert de periodo não retornou id");
        } catch (SQLException e) {
            logger.severe("Erro ao salvar periodo: " + e.getMessage());
        }
        return false;
    }

    public boolean alterar(Periodo p) {
        String sql = "UPDATE periodo SET nome_periodo=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNomePeriodo());
            ps.setInt(2, p.getId());
            int linhas = ps.executeUpdate();
            logger.info("Periodo alterado, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao alterar periodo: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM periodo WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            logger.info("Periodo excluído, linhas afetadas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.severe("Erro ao excluir periodo: " + e.getMessage());
            return false;
        }
    }

    public Periodo pesquisar(int id) {
        String sql = "SELECT id, nome_periodo FROM periodo WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Periodo p = new Periodo(
                        rs.getInt("id"),
                        rs.getString("nome_periodo"));
                logger.info("Periodo encontrado id=" + id);
                return p;
            } else {
                logger.info("Periodo não encontrado id=" + id);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao pesquisar periodo: " + e.getMessage());
        }
        return null;
    }

    public List<Periodo> listarTodos() {
        String sql = "SELECT id, nome_periodo FROM periodo ORDER BY nome_periodo";
        List<Periodo> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Periodo p = new Periodo(
                        rs.getInt("id"),
                        rs.getString("nome_periodo"));
                lista.add(p);
            }
            logger.info("Lista de periodos carregada. Quantidade=" + lista.size());
        } catch (SQLException e) {
            logger.severe("Erro ao listar periodos: " + e.getMessage());
        }
        return lista;
    }
}

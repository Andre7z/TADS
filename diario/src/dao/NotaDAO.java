package dao;

import model.Nota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotaDAO {
    private static final Logger logger = LogManager.getLogger(NotaDAO.class);
    private Connection conn;

    public NotaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Nota nota) {
        String sql = "INSERT INTO nota (id_diario, valor) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Double valor : nota.getNotas()) {
                ps.setInt(1, nota.getIdDiario());
                ps.setBigDecimal(2, java.math.BigDecimal.valueOf(valor));
                ps.addBatch();
            }
            int[] resultados = ps.executeBatch();
            logger.info("Notas salvas, quantidade=" + resultados.length);
            return resultados.length > 0;
        } catch (SQLException e) {
            logger.error("Erro ao salvar notas: " + e.getMessage());
            return false;
        }
    }

    public List<Double> listarNotasPorDiario(int idDiario) {
        String sql = "SELECT valor FROM nota WHERE id_diario=?";
        List<Double> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDiario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getDouble("valor"));
            }
            logger.info("Notas carregadas para diario=" + idDiario + " qtd=" + lista.size());
        } catch (SQLException e) {
            logger.error("Erro ao listar notas: " + e.getMessage());
        }
        return lista;
    }

    public boolean excluirPorDiario(int idDiario) {
        String sql = "DELETE FROM nota WHERE id_diario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDiario);
            int linhas = ps.executeUpdate();
            logger.info("Notas excluídas para diario=" + idDiario + " linhas=" + linhas);
            return linhas > 0;
        } catch (SQLException e) {
            logger.error("Erro ao excluir notas: " + e.getMessage());
            return false;
        }
    }
}

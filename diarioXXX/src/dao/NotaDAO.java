package dao;

import model.Nota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    private Connection conn;

    public NotaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Nota n) throws SQLException {
        String sql = "INSERT INTO nota (valor, diario_id) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, n.getValor());
        ps.setInt(2, n.getDiarioId());
        ps.executeUpdate();
    }

    public List<Nota> listarPorDiario(int diarioId) throws SQLException {
        List<Nota> lista = new ArrayList<>();

        String sql = "SELECT * FROM nota WHERE diario_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, diarioId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Nota n = new Nota();
            n.setId(rs.getInt("id"));
            n.setValor(rs.getDouble("valor"));
            n.setDiarioId(diarioId);
            lista.add(n);
        }

        return lista;
    }
}

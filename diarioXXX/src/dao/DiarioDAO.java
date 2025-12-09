package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiarioDAO {

    private Connection conn;

    public DiarioDAO(Connection conn) {
        this.conn = conn;
    }

    /** INSERT Diário */
    public int inserir(Diario d) throws SQLException {
        String sql = "INSERT INTO diario (status, aluno_id, disciplina_id, periodo_id, turma_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBoolean(1, d.isStatus());
        ps.setInt(2, d.getAluno().getId());
        ps.setInt(3, d.getDisciplina().getId());
        ps.setInt(4, d.getPeriodo().getId());
        ps.setInt(5, d.getTurma().getId());

        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt("id");
        
        return -1;
    }

    /** LISTAR todos */
    public List<Diario> listar() throws SQLException {
        List<Diario> lista = new ArrayList<>();

        String sql = "SELECT * FROM diario";
        ResultSet rs = conn.prepareStatement(sql).executeQuery();

        while (rs.next()) {
            Diario d = new Diario();
            d.setId(rs.getInt("id"));
            d.setStatus(rs.getBoolean("status"));

            // preencher objetos relacionados SE você quiser (opcional)
            
            lista.add(d);
        }

        return lista;
    }

    /** Buscar por ID (carrega notas incluindo médias) */
    public Diario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM diario WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) return null;

        Diario d = new Diario();
        d.setId(rs.getInt("id"));
        d.setStatus(rs.getBoolean("status"));

        d.getNotas().addAll(new NotaDAO(conn).listarPorDiario(d.getId()));

        return d;
    }
}

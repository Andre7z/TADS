package dao;

import model.Pessoa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa(nome, endereco, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEndereco());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome=?, endereco=?, telefone=?, email=? WHERE id=?";
        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEndereco());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.setInt(5, pessoa.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM pessoa WHERE id=?";
        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM pessoa WHERE id=?";
        Pessoa pessoa = null;

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pessoa = new Pessoa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoa;
    }

    public List<Pessoa> listarTodos() {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> lista = new ArrayList<>();

        try (Connection con = Conexao.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                lista.add(pessoa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

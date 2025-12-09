package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.*;
import dao.Conexao;

public class DisciplinaForm extends JFrame {
    private static final Logger logger = Logger.getLogger(DisciplinaForm.class.getName());
    private JTextField txtId, txtNome, txtCarga;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public DisciplinaForm() {
        setTitle("Cadastro de Disciplina");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(510, 360);
        setLocationRelativeTo(null);
        inicializar();
        btnSalvar.addActionListener(_ -> salvar());
        btnAlterar.addActionListener(_ -> alterar());
        btnExcluir.addActionListener(_ -> excluir());
        btnPesquisar.addActionListener(_ -> pesquisar());
        btnLimpar.addActionListener(_ -> limpar());
        btnSair.addActionListener(_ -> dispose());
        setVisible(true);
    }

    private void inicializar() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int linha = 0;
        gbc.gridx = 0;
        gbc.gridy = linha;
        p.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(20);
        p.add(txtId, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        p.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        p.add(txtNome, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        p.add(new JLabel("Carga Horária:"), gbc);
        gbc.gridx = 1;
        txtCarga = new JTextField(20);
        p.add(txtCarga, gbc);

        JPanel botoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");
        botoes.add(btnSalvar);
        botoes.add(btnAlterar);
        botoes.add(btnExcluir);
        botoes.add(btnPesquisar);
        botoes.add(btnLimpar);
        botoes.add(btnSair);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        gbc.gridwidth = 2;
        p.add(botoes, gbc);
        add(p);
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome obrigatório");
            return;
        }
        String sql = "INSERT INTO disciplina(nome, carga_horaria) VALUES(?,?)";
        try (Connection c = Conexao.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, txtNome.getText().trim());
            ps.setInt(2, Integer.parseInt(txtCarga.getText().trim()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                txtId.setText(String.valueOf(rs.getInt(1)));
            logger.info("Disciplina salva");
            JOptionPane.showMessageDialog(this, "Salvo");
            limpar();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro salvar disciplina", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        String sql = "UPDATE disciplina SET nome=?, carga_horaria=? WHERE id=?";
        try (Connection c = Conexao.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText().trim());
            ps.setInt(2, Integer.parseInt(txtCarga.getText().trim()));
            ps.setInt(3, Integer.parseInt(txtId.getText().trim()));
            ps.executeUpdate();
            logger.info("Disciplina alterada");
            JOptionPane.showMessageDialog(this, "Alterado");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro alterar disciplina", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        String sql = "DELETE FROM disciplina WHERE id=?";
        try (Connection c = Conexao.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtId.getText().trim()));
            ps.executeUpdate();
            logger.info("Disciplina excluída");
            JOptionPane.showMessageDialog(this, "Excluído");
            limpar();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro excluir disciplina", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void pesquisar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        String sql = "SELECT * FROM disciplina WHERE id=?";
        try (Connection c = Conexao.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtId.getText().trim()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNome.setText(rs.getString("nome"));
                txtCarga.setText(String.valueOf(rs.getInt("carga_horaria")));
                logger.info("Disciplina carregada");
            } else
                JOptionPane.showMessageDialog(this, "Não encontrado");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro pesquisar disciplina", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void limpar() {
        txtId.setText("");
        txtNome.setText("");
        txtCarga.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DisciplinaForm::new);
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.*;
import dao.Conexao;

public class ProfessorForm extends JFrame {
    private static final Logger logger = Logger.getLogger(ProfessorForm.class.getName());
    private JTextField txtId, txtNome, txtEndereco, txtTelefone, txtEmail, txtMatricula;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public ProfessorForm() {
        setTitle("Cadastro de Professor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 380);
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
        p.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        txtEndereco = new JTextField(20);
        p.add(txtEndereco, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        p.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        p.add(txtTelefone, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        p.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        p.add(txtEmail, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        p.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(20);
        p.add(txtMatricula, gbc);

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
        String sqlPessoa = "INSERT INTO pessoa(nome,endereco,telefone,email) VALUES(?,?,?,?)";
        String sqlProfessor = "INSERT INTO professor(id,matricula) VALUES(?,?)";
        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, txtNome.getText().trim());
                ps.setString(2, txtEndereco.getText().trim());
                ps.setString(3, txtTelefone.getText().trim());
                ps.setString(4, txtEmail.getText().trim());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                int id = 0;
                if (rs.next())
                    id = rs.getInt(1);
                try (PreparedStatement ps2 = c.prepareStatement(sqlProfessor)) {
                    ps2.setInt(1, id);
                    ps2.setString(2, txtMatricula.getText().trim());
                    ps2.executeUpdate();
                }
                c.commit();
                txtId.setText(String.valueOf(id));
                logger.info("Professor salvo id=" + id);
                JOptionPane.showMessageDialog(this, "Professor salvo");
                limpar();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro salvar professor", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        String sqlP = "UPDATE pessoa SET nome=?,endereco=?,telefone=?,email=? WHERE id=?";
        String sqlProf = "UPDATE professor SET matricula=? WHERE id=?";
        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sqlP)) {
                ps.setString(1, txtNome.getText().trim());
                ps.setString(2, txtEndereco.getText().trim());
                ps.setString(3, txtTelefone.getText().trim());
                ps.setString(4, txtEmail.getText().trim());
                ps.setInt(5, Integer.parseInt(txtId.getText().trim()));
                ps.executeUpdate();
            }
            try (PreparedStatement ps2 = c.prepareStatement(sqlProf)) {
                ps2.setString(1, txtMatricula.getText().trim());
                ps2.setInt(2, Integer.parseInt(txtId.getText().trim()));
                ps2.executeUpdate();
            }
            c.commit();
            logger.info("Professor alterado id=" + txtId.getText());
            JOptionPane.showMessageDialog(this, "Alterado");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro alterar professor", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        try (Connection c = Conexao.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("DELETE FROM professor WHERE id=?")) {
                ps.setInt(1, Integer.parseInt(txtId.getText().trim()));
                ps.executeUpdate();
            }
            try (PreparedStatement ps2 = c.prepareStatement("DELETE FROM pessoa WHERE id=?")) {
                ps2.setInt(1, Integer.parseInt(txtId.getText().trim()));
                ps2.executeUpdate();
            }
            logger.info("Professor excluído id=" + txtId.getText());
            JOptionPane.showMessageDialog(this, "Excluído");
            limpar();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro excluir professor", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void pesquisar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        String sql = "SELECT p.*, pr.matricula FROM pessoa p JOIN professor pr ON p.id=pr.id WHERE p.id=?";
        try (Connection c = Conexao.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txtId.getText().trim()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNome.setText(rs.getString("nome"));
                txtEndereco.setText(rs.getString("endereco"));
                txtTelefone.setText(rs.getString("telefone"));
                txtEmail.setText(rs.getString("email"));
                txtMatricula.setText(rs.getString("matricula"));
                logger.info("Professor carregado id=" + txtId.getText());
            } else
                JOptionPane.showMessageDialog(this, "Não encontrado");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro pesquisar professor", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void limpar() {
        txtId.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfessorForm::new);
    }
}

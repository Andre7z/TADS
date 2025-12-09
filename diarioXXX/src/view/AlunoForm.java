package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.*;

import dao.Conexao;

public class AlunoForm extends JFrame {

    private static final Logger logger = Logger.getLogger(AlunoForm.class.getName());

    private JTextField txtId, txtNome, txtEndereco, txtTelefone, txtEmail;
    private JTextField txtMatricula, txtNomePai, txtNomeMae;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public AlunoForm() {
        setTitle("Cadastro de Alunos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);

        inicializarComponentes();

        btnSalvar.addActionListener(_ -> salvarAluno());
        btnAlterar.addActionListener(_ -> alterarAluno());
        btnExcluir.addActionListener(_ -> excluirAluno());
        btnPesquisar.addActionListener(_ -> pesquisarAluno());
        btnLimpar.addActionListener(_ -> limparCampos());
        btnSair.addActionListener(_ -> dispose());

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int linha = 0;

        // ID
        gbc.gridx = 0;
        gbc.gridy = linha;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(20);
        painel.add(txtId, gbc);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painel.add(txtNome, gbc);

        // Endereço
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        txtEndereco = new JTextField(20);
        painel.add(txtEndereco, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        painel.add(txtTelefone, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        painel.add(txtEmail, gbc);

        // Matrícula
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(20);
        painel.add(txtMatricula, gbc);

        // Nome Pai
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Nome do Pai:"), gbc);
        gbc.gridx = 1;
        txtNomePai = new JTextField(20);
        painel.add(txtNomePai, gbc);

        // Nome Mãe
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Nome da Mãe:"), gbc);
        gbc.gridx = 1;
        txtNomeMae = new JTextField(20);
        painel.add(txtNomeMae, gbc);

        // Botões
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
        painel.add(botoes, gbc);

        add(painel);
    }

    // ===========================
    // CRUD
    // ===========================

    private void salvarAluno() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do aluno!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);

            // 1) Salva na tabela pessoa
            String sqlPessoa = "INSERT INTO pessoa (nome, endereco, telefone, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            stmtPessoa.setString(1, txtNome.getText());
            stmtPessoa.setString(2, txtEndereco.getText());
            stmtPessoa.setString(3, txtTelefone.getText());
            stmtPessoa.setString(4, txtEmail.getText());
            stmtPessoa.executeUpdate();

            ResultSet rs = stmtPessoa.getGeneratedKeys();
            int idGerado = 0;
            if (rs.next())
                idGerado = rs.getInt(1);

            txtId.setText(String.valueOf(idGerado));

            // 2) Salva na tabela aluno
            String sqlAluno = "INSERT INTO aluno (id, matricula, nome_pai, nome_mae) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtAluno = con.prepareStatement(sqlAluno);
            stmtAluno.setInt(1, idGerado);
            stmtAluno.setInt(2, Integer.parseInt(txtMatricula.getText()));
            stmtAluno.setString(3, txtNomePai.getText());
            stmtAluno.setString(4, txtNomeMae.getText());
            stmtAluno.executeUpdate();

            con.commit();

            JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
            limparCampos();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao salvar aluno", e);
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarAluno() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do aluno!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);

            // Atualiza pessoa
            String sqlPessoa = "UPDATE pessoa SET nome=?, endereco=?, telefone=?, email=? WHERE id=?";
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, txtNome.getText());
            stmtPessoa.setString(2, txtEndereco.getText());
            stmtPessoa.setString(3, txtTelefone.getText());
            stmtPessoa.setString(4, txtEmail.getText());
            stmtPessoa.setInt(5, Integer.parseInt(txtId.getText()));
            stmtPessoa.executeUpdate();

            // Atualiza aluno
            String sqlAluno = "UPDATE aluno SET matricula=?, nome_pai=?, nome_mae=? WHERE id=?";
            PreparedStatement stmtAluno = con.prepareStatement(sqlAluno);
            stmtAluno.setInt(1, Integer.parseInt(txtMatricula.getText()));
            stmtAluno.setString(2, txtNomePai.getText());
            stmtAluno.setString(3, txtNomeMae.getText());
            stmtAluno.setInt(4, Integer.parseInt(txtId.getText()));
            stmtAluno.executeUpdate();

            con.commit();

            JOptionPane.showMessageDialog(this, "Aluno alterado com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar aluno", e);
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + e.getMessage());
        }
    }

    private void excluirAluno() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        try (Connection con = Conexao.getConnection()) {

            // 1) Remove aluno
            PreparedStatement stmtAluno = con.prepareStatement("DELETE FROM aluno WHERE id=?");
            stmtAluno.setInt(1, id);
            stmtAluno.executeUpdate();

            // 2) Remove pessoa
            PreparedStatement stmtPessoa = con.prepareStatement("DELETE FROM pessoa WHERE id=?");
            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!");
            limparCampos();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir aluno", e);
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    private void pesquisarAluno() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.getConnection()) {

            String sql = """
                        SELECT p.*, a.matricula, a.nome_pai, a.nome_mae
                        FROM pessoa p
                        JOIN aluno a ON p.id = a.id
                        WHERE p.id = ?
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtNome.setText(rs.getString("nome"));
                txtEndereco.setText(rs.getString("endereco"));
                txtTelefone.setText(rs.getString("telefone"));
                txtEmail.setText(rs.getString("email"));
                txtMatricula.setText(String.valueOf(rs.getInt("matricula")));
                txtNomePai.setText(rs.getString("nome_pai"));
                txtNomeMae.setText(rs.getString("nome_mae"));
            } else {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao pesquisar aluno", e);
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtNomePai.setText("");
        txtNomeMae.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AlunoForm::new);
    }
}

package view;
import javax.swing.*;

import dao.Conexao;

import java.awt.*;
import java.sql.*;

public class FormPrioridade extends JFrame {
    private JTextField txtId;
    private JTextField txtDescricao;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public FormPrioridade() {
        setTitle("Cadastro de Prioridade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        inicializarComponentes();

        btnSalvar.addActionListener(_ -> salvarPrioridade());
        btnAlterar.addActionListener(_ -> alterarPrioridade());
        btnExcluir.addActionListener(_ -> excluirPrioridade());
        btnPesquisar.addActionListener(_ -> pesquisarPrioridade());
        btnLimpar.addActionListener(_ -> limparCampos());
        btnSair.addActionListener(_ -> dispose());

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campo ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtId = new JTextField(15);
        painelPrincipal.add(txtId, gbc);

        // Campo Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtDescricao = new JTextField(15);
        painelPrincipal.add(txtDescricao, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);

        add(painelPrincipal);
    }

    private void salvarPrioridade() {
        if (txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "INSERT INTO prioridade (descricao) VALUES (?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, txtDescricao.getText().trim());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                txtId.setText(String.valueOf(rs.getInt(1)));
            }

            JOptionPane.showMessageDialog(this, "Prioridade salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void alterarPrioridade() {
        if (txtId.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha ID e descrição!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "UPDATE prioridade SET descricao = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, txtDescricao.getText().trim());
            stmt.setInt(2, Integer.parseInt(txtId.getText().trim()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                JOptionPane.showMessageDialog(this, "Prioridade alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "ID não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void excluirPrioridade() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "DELETE FROM prioridade WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText().trim()));

            int x = stmt.executeUpdate();
            if (x > 0) {
                JOptionPane.showMessageDialog(this, "Prioridade excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "ID não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void pesquisarPrioridade() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para pesquisa!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "SELECT * FROM prioridade WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText().trim()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtDescricao.setText(rs.getString("descricao"));
            } else {
                JOptionPane.showMessageDialog(this, "Prioridade não encontrada!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtDescricao.setText("");
        txtId.requestFocus();
    }
     public static void main(String[] args) {
        SwingUtilities.invokeLater(FormPrioridade::new);
    }
}

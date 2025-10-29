import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FormResponsavel extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public FormResponsavel() {
        setTitle("Cadastro de Responsável");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(500, 350);
        setLocationRelativeTo(null);

        inicializarComponentes();

        btnSalvar.addActionListener(e -> salvarResponsavel());
        btnAlterar.addActionListener(e -> alterarResponsavel());
        btnExcluir.addActionListener(e -> excluirResponsavel());
        btnPesquisar.addActionListener(e -> pesquisarResponsavel());
        btnLimpar.addActionListener(e -> limparCampos());
        btnSair.addActionListener(e -> dispose());

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

        // Campo Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Responsável:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNome = new JTextField(15);
        painelPrincipal.add(txtNome, gbc);

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

    private void salvarResponsavel() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "INSERT INTO responsavel (nome) VALUES (?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, txtNome.getText().trim());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                txtId.setText(String.valueOf(rs.getInt(1)));
            }

            JOptionPane.showMessageDialog(this, "Responsável salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void alterarResponsavel() {
        if (txtId.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha ID e nome!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "UPDATE responsavel SET nome = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, txtNome.getText().trim());
            stmt.setInt(2, Integer.parseInt(txtId.getText().trim()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                JOptionPane.showMessageDialog(this, "Responsável alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "ID não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void excluirResponsavel() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "DELETE FROM responsavel WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText().trim()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                JOptionPane.showMessageDialog(this, "Responsável excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "ID não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void pesquisarResponsavel() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para pesquisa!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "SELECT * FROM responsavel WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(txtId.getText().trim()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtNome.setText(rs.getString("nome"));
            } else {
                JOptionPane.showMessageDialog(this, "Responsável não encontrado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormResponsavel::new);
    }
}

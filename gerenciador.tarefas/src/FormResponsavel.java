import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormResponsavel extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;

    public FormResponsavel() {
        setTitle("Cadastro de Responsável");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        inicializarComponentes();

        btnSalvar.addActionListener(e -> salvarResponsavel());
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
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnSalvar);
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
        if (txtId.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "O campo ID deve conter apenas números!",
                    "ID inválido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Salvar no banco de dados
        try (Connection con = Conexao.conectar()) {
            String sql = "INSERT INTO tResponsavel (id, nome) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, txtNome.getText().trim());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Dados salvos com sucesso no banco!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar no banco: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormResponsavel());
    }
}

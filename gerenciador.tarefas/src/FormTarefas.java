import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormTarefas extends JFrame {

    private JTextField txtId;
    private JFormattedTextField txtDataTarefa;
    private JTextArea txtDescricao;
    private JTextArea txtObservacao;
    private JComboBox<String> cmbResponsavel;
    private JComboBox<String> cmbPrioridade;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;

    public FormTarefas() {
        setTitle("Cadastro de Tarefas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarCombos();

        btnSalvar.addActionListener(e -> salvarTarefa());
        btnLimpar.addActionListener(e -> limparCampos());
        btnSair.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtId = new JTextField(15);
        painelPrincipal.add(txtId, gbc);

        // Data
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Data da Tarefa (dd/mm/aaaa):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataTarefa = new JFormattedTextField("##/##/####");
        txtDataTarefa.setColumns(15);
        painelPrincipal.add(txtDataTarefa, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelPrincipal.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        txtDescricao = new JTextArea(3, 15);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        painelPrincipal.add(new JScrollPane(txtDescricao), gbc);

        // Observação
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        painelPrincipal.add(new JLabel("Observação:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        txtObservacao = new JTextArea(3, 15);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);
        painelPrincipal.add(new JScrollPane(txtObservacao), gbc);

        // Combo Responsável
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Responsável:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbResponsavel = new JComboBox<>();
        painelPrincipal.add(cmbResponsavel, gbc);

        // Combo Prioridade
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Prioridade:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbPrioridade = new JComboBox<>();
        painelPrincipal.add(cmbPrioridade, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);

        add(painelPrincipal);
    }

    private void carregarCombos() {
        try (Connection con = Conexao.conectar()) {
            // Responsáveis
            cmbResponsavel.addItem("Selecione um responsável...");
            String sqlResp = "SELECT nome FROM tResponsavel ORDER BY nome";
            PreparedStatement stmtResp = con.prepareStatement(sqlResp);
            ResultSet rsResp = stmtResp.executeQuery();
            while (rsResp.next()) {
                cmbResponsavel.addItem(rsResp.getString("nome"));
            }

            // Prioridades
            cmbPrioridade.addItem("Selecione a prioridade...");
            String sqlPrior = "SELECT descricao FROM tPrioridade ORDER BY descricao";
            PreparedStatement stmtPrior = con.prepareStatement(sqlPrior);
            ResultSet rsPrior = stmtPrior.executeQuery();
            while (rsPrior.next()) {
                cmbPrioridade.addItem(rsPrior.getString("descricao"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarTarefa() {
        if (txtId.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos obrigatórios!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "O campo ID deve ser um número!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cmbResponsavel.getSelectedIndex() == 0 || cmbPrioridade.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione responsável e prioridade!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = Conexao.conectar()) {
            String sql = "INSERT INTO tListaTarefas (id, titulo, descricao, id_responsavel, id_prioridade) VALUES (?, ?, ?, " +
                    "(SELECT id FROM tResponsavel WHERE nome = ?), " +
                    "(SELECT id FROM tPrioridade WHERE descricao = ?))";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, txtDescricao.getText().trim());
            stmt.setString(3, txtObservacao.getText().trim());
            stmt.setString(4, cmbResponsavel.getSelectedItem().toString());
            stmt.setString(5, cmbPrioridade.getSelectedItem().toString());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Tarefa salva com sucesso!", "Sucesso",
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
        txtDescricao.setText("");
        txtObservacao.setText("");
        txtDataTarefa.setText("");
        cmbResponsavel.setSelectedIndex(0);
        cmbPrioridade.setSelectedIndex(0);
        txtId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormTarefas());
    }
}

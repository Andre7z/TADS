import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class FormTarefas extends JFrame {

    private JTextField txtId;
    private JFormattedTextField txtDataTarefa;
    private JTextArea txtDescricao;
    private JTextArea txtObservacao;
    private JComboBox<String> cmbResponsavel;
    private JComboBox<String> cmbPrioridade;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    private HashMap<String, Integer> responsavelMap = new HashMap<>();
    private HashMap<String, Integer> prioridadeMap = new HashMap<>();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FormTarefas() {
        setTitle("Cadastro de Tarefas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarResponsaveis();
        carregarPrioridades();

        btnSalvar.addActionListener(e -> salvarTarefa());
        btnAlterar.addActionListener(e -> alterarTarefa());
        btnExcluir.addActionListener(e -> excluirTarefa());
        btnPesquisar.addActionListener(e -> pesquisarTarefa());
        btnLimpar.addActionListener(e -> limparCampos());
        btnSair.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        painel.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painel.add(new JLabel("Data da Tarefa (DD/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        txtDataTarefa = new JFormattedTextField();
        txtDataTarefa.setColumns(10);
        painel.add(txtDataTarefa, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        painel.add(new JScrollPane(txtDescricao), gbc);

        gbc.gridx = 0; gbc.gridy++;
        painel.add(new JLabel("Observação:"), gbc);
        gbc.gridx = 1;
        txtObservacao = new JTextArea(3, 20);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);
        painel.add(new JScrollPane(txtObservacao), gbc);

        gbc.gridx = 0; gbc.gridy++;
        painel.add(new JLabel("Responsável:"), gbc);
        gbc.gridx = 1;
        cmbResponsavel = new JComboBox<>();
        painel.add(cmbResponsavel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painel.add(new JLabel("Prioridade:"), gbc);
        gbc.gridx = 1;
        cmbPrioridade = new JComboBox<>();
        painel.add(cmbPrioridade, gbc);

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

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        painel.add(painelBotoes, gbc);

        add(painel);
    }
private void carregarResponsaveis() {
    try (Connection con = Conexao.conectar()) {
        String sql = "SELECT id, nome FROM responsavel ORDER BY id";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        cmbResponsavel.addItem("Selecione um responsável...");
        responsavelMap.clear();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String item = id + " - " + nome;
            cmbResponsavel.addItem(item);
            responsavelMap.put(item, id);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar responsáveis: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

private void carregarPrioridades() {
    try (Connection con = Conexao.conectar()) {
        String sql = "SELECT id, descricao FROM prioridade ORDER BY id";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        cmbPrioridade.addItem("Selecione a prioridade...");
        prioridadeMap.clear();

        while (rs.next()) {
            int id = rs.getInt("id");
            String desc = rs.getString("descricao");
            String item = id + " - " + desc;
            cmbPrioridade.addItem(item);
            prioridadeMap.put(item, id);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar prioridades: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty()
                || cmbResponsavel.getSelectedIndex() == 0 || cmbPrioridade.getSelectedIndex() == 0
                || txtDataTarefa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtId.getText().trim());
            LocalDate.parse(txtDataTarefa.getText().trim(), formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID ou Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void salvarTarefa() {
        if (!validarCampos()) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            LocalDate data = LocalDate.parse(txtDataTarefa.getText().trim(), formatter);
            int idResp = responsavelMap.get(cmbResponsavel.getSelectedItem().toString());
            int idPrior = prioridadeMap.get(cmbPrioridade.getSelectedItem().toString());

            try (Connection con = Conexao.conectar()) {
                String sql = "INSERT INTO lista_tarefas (id, titulo, descricao, id_responsavel, id_prioridade, data_tarefa) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.setString(2, txtDescricao.getText().trim());
                stmt.setString(3, txtObservacao.getText().trim());
                stmt.setInt(4, idResp);
                stmt.setInt(5, idPrior);
                stmt.setDate(6, Date.valueOf(data));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Tarefa salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarTarefa() {
        if (!validarCampos()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            LocalDate data = LocalDate.parse(txtDataTarefa.getText().trim(), formatter);
            int idResp = responsavelMap.get(cmbResponsavel.getSelectedItem().toString());
            int idPrior = prioridadeMap.get(cmbPrioridade.getSelectedItem().toString());

            try (Connection con = Conexao.conectar()) {
                String sql = "UPDATE lista_tarefas SET titulo=?, descricao=?, id_responsavel=?, id_prioridade=?, data_tarefa=? WHERE id=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, txtDescricao.getText().trim());
                stmt.setString(2, txtObservacao.getText().trim());
                stmt.setInt(3, idResp);
                stmt.setInt(4, idPrior);
                stmt.setDate(5, Date.valueOf(data));
                stmt.setInt(6, id);
                int rows = stmt.executeUpdate();
                if (rows > 0)
                    JOptionPane.showMessageDialog(this, "Tarefa alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "ID não encontrado!", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirTarefa() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para excluir!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir a tarefa?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection con = Conexao.conectar()) {
            String sql = "DELETE FROM lista_tarefas WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(strId));
            int rows = stmt.executeUpdate();
            if (rows > 0)
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "ID não encontrado!", "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

private void pesquisarTarefa() {
    String strId = txtId.getText().trim();
    if (strId.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Informe o ID para pesquisar!", "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (Connection con = Conexao.conectar()) {
        String sql = "SELECT t.*, r.id AS id_responsavel, r.nome AS responsavel, " +
                     "p.id AS id_prioridade, p.descricao AS prioridade " +
                     "FROM lista_tarefas t " +
                     "JOIN responsavel r ON t.id_responsavel = r.id " +
                     "JOIN prioridade p ON t.id_prioridade = p.id " +
                     "WHERE t.id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(strId));
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            txtDescricao.setText(rs.getString("titulo"));
            txtObservacao.setText(rs.getString("descricao"));
            txtDataTarefa.setText(rs.getDate("data_tarefa").toLocalDate().format(formatter));

            // Selecionar o responsável correspondente (por ID)
            int idResp = rs.getInt("id_responsavel");
            for (int i = 1; i < cmbResponsavel.getItemCount(); i++) {
                String item = cmbResponsavel.getItemAt(i);
                if (item.startsWith(idResp + " -")) {
                    cmbResponsavel.setSelectedIndex(i);
                    break;
                }
            }

            // Selecionar a prioridade correspondente (por ID)
            int idPrior = rs.getInt("id_prioridade");
            for (int i = 1; i < cmbPrioridade.getItemCount(); i++) {
                String item = cmbPrioridade.getItemAt(i);
                if (item.startsWith(idPrior + " -")) {
                    cmbPrioridade.setSelectedIndex(i);
                    break;
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "ID não encontrado!", "Atenção", JOptionPane.WARNING_MESSAGE);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao pesquisar tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        SwingUtilities.invokeLater(FormTarefas::new);
    }
}

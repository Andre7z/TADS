package view;

import controller.TurmaController;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaTurma extends JFrame {

    private JTextField txtId;
    private JTextField txtNomeTurma;

    private JComboBox<String> cmbIdsTurma;

    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;

    private TurmaController turmaController;

    public TelaTurma(TurmaController turmaController) {
        this.turmaController = turmaController;

        setTitle("Cadastro de Turma");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 260);
        setLocationRelativeTo(null);

        inicializarComponentes();
        adicionarEventos();
        carregarIdsTurma();

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(5, 5, 5, 5);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("ID Turma (gerado pelo sistema):"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        painel.add(txtId, gbc);

        // Combo IDs Turma
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Selecionar Turma salva:"), gbc);
        gbc.gridx = 1;
        cmbIdsTurma = new JComboBox<>();
        painel.add(cmbIdsTurma, gbc);

        // Nome Turma
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Nome da Turma:"), gbc);
        gbc.gridx = 1;
        txtNomeTurma = new JTextField(20);
        painel.add(txtNomeTurma, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar  = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar  = new JButton("Limpar");
        btnSair    = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        painel.add(painelBotoes, gbc);

        add(painel);
    }

    private void adicionarEventos() {
        btnSalvar.addActionListener(_ -> salvarTurma());
        btnAlterar.addActionListener(_ -> alterarTurma());
        btnExcluir.addActionListener(_ -> excluirTurma());
        btnLimpar.addActionListener(_ -> limparCampos());
        btnSair.addActionListener(_ -> dispose());

        cmbIdsTurma.addActionListener(_ -> selecionarIdDoCombo());
    }

    /* ================== Métodos de carregamento ================== */

    private void carregarIdsTurma() {
        cmbIdsTurma.removeAllItems();
        cmbIdsTurma.addItem("Selecione uma turma...");

        List<Turma> lista = turmaController.listarTodos();
        if (lista != null) {
            for (Turma t : lista) {
                String item = t.getId() + " - " + t.getNomeTurma();
                cmbIdsTurma.addItem(item);
            }
        }
    }

    /* ================== Validação e montagem ================== */

    private boolean validarCampos() {
        if (txtNomeTurma.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha o nome da turma.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Turma montarTurmaDaTela() {
        int id = txtId.getText().trim().isEmpty()
                ? 0
                : Integer.parseInt(txtId.getText().trim());
        String nome = txtNomeTurma.getText().trim();
        return new Turma(id, nome);
    }

    private void preencherTelaComTurma(Turma t) {
        txtId.setText(String.valueOf(t.getId()));
        txtNomeTurma.setText(t.getNomeTurma());
    }

    /* ================== CRUD ================== */

    private void salvarTurma() {
        if (!validarCampos())
            return;
        Turma t = montarTurmaDaTela();
        boolean ok = turmaController.salvar(t);
        if (ok && t.getId() != 0) {
            txtId.setText(String.valueOf(t.getId()));
            carregarIdsTurma();
        }
        JOptionPane.showMessageDialog(this,
                ok ? "Turma salva com sucesso!" : "Erro ao salvar turma.",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void alterarTurma() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma turma no combo antes de alterar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos())
            return;

        Turma t = montarTurmaDaTela();
        boolean ok = turmaController.alterar(t);
        JOptionPane.showMessageDialog(this,
                ok ? "Turma alterada com sucesso!" : "Erro ao alterar turma.",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (ok) carregarIdsTurma();
    }

    private void excluirTurma() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma turma no combo antes de excluir.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir a turma?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION)
            return;

        int id = Integer.parseInt(strId);
        boolean ok = turmaController.excluir(id);
        JOptionPane.showMessageDialog(this,
                ok ? "Turma excluída com sucesso!" : "Erro ao excluir turma.",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (ok) {
            limparCampos();
            carregarIdsTurma();
        }
    }

    /* ================== Auxiliares ================== */

    private void selecionarIdDoCombo() {
        if (cmbIdsTurma.getSelectedIndex() <= 0) return;

        String item = (String) cmbIdsTurma.getSelectedItem();
        if (item == null || item.isBlank()) return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Turma t = turmaController.pesquisar(id);
            if (t != null) {
                preencherTelaComTurma(t);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Turma não encontrada para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora item inválido
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNomeTurma.setText("");
        if (cmbIdsTurma.getItemCount() > 0)
            cmbIdsTurma.setSelectedIndex(0);
        txtNomeTurma.requestFocus();
    }
}

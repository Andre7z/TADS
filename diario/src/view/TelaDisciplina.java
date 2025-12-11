package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.DisciplinaController;
import model.Disciplina;

public class TelaDisciplina extends JFrame {

    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;
    private JComboBox<String> cmbIds; // combo para selecionar IDs já salvas

    private DisciplinaController controller;

    public TelaDisciplina(DisciplinaController controller) {
        this.controller = controller;
        initComponents();
        carregarIdsDisciplinas(); // carrega IDs ao abrir
    }

    private void initComponents() {
        setTitle("Cadastro de Disciplina");
        setSize(520, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        // ID somente para visualização
        panel.add(new JLabel("ID (gerado pelo sistema):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panel.add(txtId);

        // Combo de IDs existentes
        panel.add(new JLabel("Selecionar Disciplina salva:"));
        cmbIds = new JComboBox<>();
        panel.add(cmbIds);

        panel.add(new JLabel("Nome Disciplina:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        JPanel panelBotoes = new JPanel();
        btnSalvar  = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar  = new JButton("Limpar");
        btnSair    = new JButton("Sair");

        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnAlterar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnLimpar);
        panelBotoes.add(btnSair);

        add(panel, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);

        adicionarEventos();
    }

    private void adicionarEventos() {
        // Salvar
        btnSalvar.addActionListener(e -> {
            Disciplina d = montarDisciplinaDaTela();
            boolean ok = controller.salvar(d);
            if (ok) {
                txtId.setText(String.valueOf(d.getId()));
                JOptionPane.showMessageDialog(this,
                        "Salvo com sucesso. ID gerado: " + d.getId(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarIdsDisciplinas();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao salvar.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Alterar
        btnAlterar.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma disciplina no combo antes de alterar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Disciplina d = montarDisciplinaDaTela();
            boolean ok = controller.alterar(d);
            JOptionPane.showMessageDialog(this,
                    ok ? "Alterado com sucesso" : "Erro ao alterar",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) carregarIdsDisciplinas();
        });

        // Excluir
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma disciplina no combo antes de excluir.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(txtId.getText().trim());
            int conf = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir a disciplina ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (conf != JOptionPane.YES_OPTION) return;

            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this,
                    ok ? "Excluído com sucesso" : "Erro ao excluir",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) {
                limparCampos();
                carregarIdsDisciplinas();
            }
        });

        // Limpar
        btnLimpar.addActionListener(e -> limparCampos());

        // Sair
        btnSair.addActionListener(e -> dispose());

        // Seleção no combo de IDs
        cmbIds.addActionListener(e -> selecionarIdDoCombo());
    }

    private Disciplina montarDisciplinaDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        return new Disciplina(id, nome);
    }

    private void preencherTelaComDisciplina(Disciplina d) {
        txtId.setText(String.valueOf(d.getId()));
        txtNome.setText(d.getNomeDisciplina());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        if (cmbIds.getItemCount() > 0) {
            cmbIds.setSelectedIndex(0);
        }
    }

    private void carregarIdsDisciplinas() {
        cmbIds.removeAllItems();
        cmbIds.addItem("Selecione uma disciplina...");

        List<Disciplina> lista = controller.listarTodos();
        if (lista != null) {
            for (Disciplina d : lista) {
                String item = d.getId() + " - " + d.getNomeDisciplina();
                cmbIds.addItem(item);
            }
        }
    }

    private void selecionarIdDoCombo() {
        if (cmbIds.getSelectedIndex() <= 0) return;

        String item = (String) cmbIds.getSelectedItem(); 
        if (item == null || item.isBlank()) return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Disciplina d = controller.pesquisar(id);
            if (d != null) {
                preencherTelaComDisciplina(d);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Disciplina não encontrada para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora item inválido
        }
    }
}

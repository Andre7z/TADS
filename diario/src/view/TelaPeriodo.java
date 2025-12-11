package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.PeriodoController;
import model.Periodo;

public class TelaPeriodo extends JFrame {

    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;
    private JComboBox<String> cmbIds; // combo para selecionar IDs já salvas

    private PeriodoController controller;

    public TelaPeriodo(PeriodoController controller) {
        this.controller = controller;
        initComponents();
        carregarIdsPeriodos(); // carrega IDs ao abrir
    }

    private void initComponents() {
        setTitle("Cadastro de Período");
        setSize(520, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        // ID somente visual
        panel.add(new JLabel("ID (gerado pelo sistema):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panel.add(txtId);

        // Combo de IDs existentes
        panel.add(new JLabel("Selecionar Período salvo:"));
        cmbIds = new JComboBox<>();
        panel.add(cmbIds);

        panel.add(new JLabel("Nome Período:"));
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
            Periodo p = montarPeriodoDaTela();
            boolean ok = controller.salvar(p);
            if (ok) {
                txtId.setText(String.valueOf(p.getId()));
                JOptionPane.showMessageDialog(this,
                        "Salvo com sucesso. ID gerado: " + p.getId(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarIdsPeriodos();
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
                        "Selecione um período no combo antes de alterar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Periodo p = montarPeriodoDaTela();
            boolean ok = controller.alterar(p);
            JOptionPane.showMessageDialog(this,
                    ok ? "Alterado com sucesso" : "Erro ao alterar",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) carregarIdsPeriodos();
        });

        // Excluir
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um período no combo antes de excluir.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(txtId.getText().trim());
            int conf = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o período ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (conf != JOptionPane.YES_OPTION) return;

            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this,
                    ok ? "Excluído com sucesso" : "Erro ao excluir",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) {
                limparCampos();
                carregarIdsPeriodos();
            }
        });

        // Limpar
        btnLimpar.addActionListener(e -> limparCampos());

        // Sair
        btnSair.addActionListener(e -> dispose());

        // Seleção no combo de IDs
        cmbIds.addActionListener(e -> selecionarIdDoCombo());
    }

    private Periodo montarPeriodoDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        return new Periodo(id, nome);
    }

    private void preencherTelaComPeriodo(Periodo p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNomePeriodo());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        if (cmbIds.getItemCount() > 0) {
            cmbIds.setSelectedIndex(0);
        }
    }

    private void carregarIdsPeriodos() {
        cmbIds.removeAllItems();
        cmbIds.addItem("Selecione um período...");

        List<Periodo> lista = controller.listarTodos();
        if (lista != null) {
            for (Periodo p : lista) {
                String item = p.getId() + " - " + p.getNomePeriodo();
                cmbIds.addItem(item);
            }
        }
    }

    private void selecionarIdDoCombo() {
        if (cmbIds.getSelectedIndex() <= 0) return;

        String item = (String) cmbIds.getSelectedItem(); // "3 - 1º Semestre"
        if (item == null || item.isBlank()) return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Periodo p = controller.pesquisar(id);
            if (p != null) {
                preencherTelaComPeriodo(p);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Período não encontrado para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora item inválido
        }
    }
}

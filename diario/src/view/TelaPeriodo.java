package view;

import javax.swing.*;
import java.awt.*;
import controller.PeriodoController;
import model.Periodo;

public class TelaPeriodo extends JFrame {

    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;
    private PeriodoController controller;

    public TelaPeriodo(PeriodoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Período");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nome Período:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        JPanel panelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnAlterar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnPesquisar);

        add(panel, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);

        adicionarEventos();
    }

    private void adicionarEventos() {
        btnSalvar.addActionListener(e -> {
            Periodo p = montarPeriodoDaTela();
            boolean ok = controller.salvar(p);
            JOptionPane.showMessageDialog(this, ok ? "Salvo com sucesso" : "Erro ao salvar");
        });

        btnAlterar.addActionListener(e -> {
            Periodo p = montarPeriodoDaTela();
            boolean ok = controller.alterar(p);
            JOptionPane.showMessageDialog(this, ok ? "Alterado com sucesso" : "Erro ao alterar");
        });

        btnExcluir.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this, ok ? "Excluído com sucesso" : "Erro ao excluir");
        });

        btnPesquisar.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            Periodo p = controller.pesquisar(id);
            if (p != null) {
                preencherTelaComPeriodo(p);
            } else {
                JOptionPane.showMessageDialog(this, "Período não encontrado");
            }
        });
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


}

package view;

import javax.swing.*;
import java.awt.*;
import controller.DisciplinaController;
import model.Disciplina;

public class TelaDisciplina extends JFrame {

    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;
    private DisciplinaController controller;

    public TelaDisciplina(DisciplinaController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Disciplina");
        setSize(500, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("ID (apenas para pesquisa/altera/excluir)::"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nome Disciplina:"));
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
            Disciplina d = montarDisciplinaDaTela();
            boolean ok = controller.salvar(d);
            JOptionPane.showMessageDialog(this, ok ? "Salvo com sucesso" : "Erro ao salvar");
        });

        btnAlterar.addActionListener(e -> {
            Disciplina d = montarDisciplinaDaTela();
            boolean ok = controller.alterar(d);
            JOptionPane.showMessageDialog(this, ok ? "Alterado com sucesso" : "Erro ao alterar");
        });

        btnExcluir.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this, ok ? "Excluído com sucesso" : "Erro ao excluir");
        });

        btnPesquisar.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            Disciplina d = controller.pesquisar(id);
            if (d != null) {
                preencherTelaComDisciplina(d);
            } else {
                JOptionPane.showMessageDialog(this, "Disciplina não encontrada");
            }
        });
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

}

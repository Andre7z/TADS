package view;

import javax.swing.*;
import java.awt.*;
import controller.TurmaController;
import model.Turma;

public class TelaTurma extends JFrame {

    private JTextField txtId, txtNomeTurma, txtIdDisciplina, txtIdProfessor, txtIdPeriodo;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;
    private TurmaController controller;

    public TelaTurma(TurmaController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Turma");
        setSize(450, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nome Turma:"));
        txtNomeTurma = new JTextField();
        panel.add(txtNomeTurma);

        panel.add(new JLabel("ID Disciplina:"));
        txtIdDisciplina = new JTextField();
        panel.add(txtIdDisciplina);

        panel.add(new JLabel("ID Professor (Pessoa):"));
        txtIdProfessor = new JTextField();
        panel.add(txtIdProfessor);

        panel.add(new JLabel("ID Período:"));
        txtIdPeriodo = new JTextField();
        panel.add(txtIdPeriodo);

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
            Turma t = montarTurmaDaTela();
            boolean ok = controller.salvar(t);
            JOptionPane.showMessageDialog(this, ok ? "Salvo com sucesso" : "Erro ao salvar");
        });

        btnAlterar.addActionListener(e -> {
            Turma t = montarTurmaDaTela();
            boolean ok = controller.alterar(t);
            JOptionPane.showMessageDialog(this, ok ? "Alterado com sucesso" : "Erro ao alterar");
        });

        btnExcluir.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this, ok ? "Excluído com sucesso" : "Erro ao excluir");
        });

        btnPesquisar.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            Turma t = controller.pesquisar(id);
            if (t != null) {
                preencherTelaComTurma(t);
            } else {
                JOptionPane.showMessageDialog(this, "Turma não encontrada");
            }
        });
    }

    private Turma montarTurmaDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String nomeTurma = txtNomeTurma.getText();
        int idDisciplina = Integer.parseInt(txtIdDisciplina.getText());
        int idProfessor = Integer.parseInt(txtIdProfessor.getText());
        int idPeriodo = Integer.parseInt(txtIdPeriodo.getText());
        return new Turma(id, nomeTurma, idDisciplina, idProfessor, idPeriodo);
    }

    private void preencherTelaComTurma(Turma t) {
        txtId.setText(String.valueOf(t.getId()));
        txtNomeTurma.setText(t.getNomeTurma());
        txtIdDisciplina.setText(String.valueOf(t.getIdDisciplina()));
        txtIdProfessor.setText(String.valueOf(t.getIdProfessor()));
        txtIdPeriodo.setText(String.valueOf(t.getIdPeriodo()));
    }

}

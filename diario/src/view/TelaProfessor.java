package view;

import javax.swing.*;
import java.awt.*;
import controller.ProfessorController;
import model.Professor;

public class TelaProfessor extends JFrame {

    private JTextField txtId, txtNome, txtEndereco, txtTelefone, txtEmail,
                       txtMatricula;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;
    private ProfessorController controller;

    public TelaProfessor(ProfessorController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Professor");
        setSize(500, 280);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("ID Pessoa:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        panel.add(txtEndereco);

        panel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        panel.add(txtTelefone);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Matrícula:"));
        txtMatricula = new JTextField();
        panel.add(txtMatricula);

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
            Professor p = montarProfessorDaTela();
            boolean ok = controller.salvar(p);
            JOptionPane.showMessageDialog(this, ok ? "Salvo com sucesso" : "Erro ao salvar");
        });

        btnAlterar.addActionListener(e -> {
            Professor p = montarProfessorDaTela();
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
            Professor p = controller.pesquisar(id);
            if (p != null) {
                preencherTelaComProfessor(p);
            } else {
                JOptionPane.showMessageDialog(this, "Professor não encontrado");
            }
        });
    }

    private Professor montarProfessorDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String matricula = txtMatricula.getText();
        return new Professor(id, nome, endereco, telefone, email, matricula);
    }

    private void preencherTelaComProfessor(Professor p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtEndereco.setText(p.getEndereco());
        txtTelefone.setText(p.getTelefone());
        txtEmail.setText(p.getEmail());
        txtMatricula.setText(p.getMatricula());
    }


}

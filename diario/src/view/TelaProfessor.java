package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.ProfessorController;
import model.Professor;

public class TelaProfessor extends JFrame {

    private JTextField txtId, txtNome, txtEndereco, txtTelefone, txtEmail, txtMatricula;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;
    private JComboBox<String> cmbIds; // combo para selecionar IDs já salvas

    private ProfessorController controller;

    public TelaProfessor(ProfessorController controller) {
        this.controller = controller;
        initComponents();
        carregarIdsProfessores(); // carrega IDs ao abrir
    }

    private void initComponents() {
        setTitle("Cadastro de Professor");
        setSize(550, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        // ID somente visual
        panel.add(new JLabel("ID Pessoa (gerado pelo sistema):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panel.add(txtId);

        // Combo de IDs existentes
        panel.add(new JLabel("Selecionar Professor salvo:"));
        cmbIds = new JComboBox<>();
        panel.add(cmbIds);

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
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

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
            Professor p = montarProfessorDaTela();
            boolean ok = controller.salvar(p);
            if (ok) {
                txtId.setText(String.valueOf(p.getId()));
                JOptionPane.showMessageDialog(this,
                        "Salvo com sucesso. ID gerado: " + p.getId(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarIdsProfessores();
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
                        "Selecione um professor no combo antes de alterar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Professor p = montarProfessorDaTela();
            boolean ok = controller.alterar(p);
            JOptionPane.showMessageDialog(this,
                    ok ? "Alterado com sucesso" : "Erro ao alterar",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok)
                carregarIdsProfessores();
        });

        // Excluir
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um professor no combo antes de excluir.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(txtId.getText().trim());
            int conf = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o professor ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (conf != JOptionPane.YES_OPTION)
                return;

            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this,
                    ok ? "Excluído com sucesso" : "Erro ao excluir",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) {
                limparCampos();
                carregarIdsProfessores();
            }
        });

        // Limpar
        btnLimpar.addActionListener(e -> limparCampos());

        // Sair
        btnSair.addActionListener(e -> dispose());

        // Seleção no combo de IDs
        cmbIds.addActionListener(e -> selecionarIdDoCombo());
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

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        if (cmbIds.getItemCount() > 0) {
            cmbIds.setSelectedIndex(0);
        }
    }

    private void carregarIdsProfessores() {
        cmbIds.removeAllItems();
        cmbIds.addItem("Selecione um professor...");

        List<Professor> lista = controller.listarTodos();
        if (lista != null) {
            for (Professor p : lista) {
                String item = p.getId() + " - " + p.getNome();
                cmbIds.addItem(item);
            }
        }
    }

    private void selecionarIdDoCombo() {
        if (cmbIds.getSelectedIndex() <= 0)
            return;

        String item = (String) cmbIds.getSelectedItem(); // "3 - João"
        if (item == null || item.isBlank())
            return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Professor p = controller.pesquisar(id);
            if (p != null) {
                preencherTelaComProfessor(p);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Professor não encontrado para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora item inválido
        }
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import controller.AlunoController;
import model.Aluno;

public class TelaAluno extends JFrame {

    private JTextField txtId, txtNome, txtEndereco, txtTelefone, txtEmail,
            txtMatricula, txtNomePai, txtNomeMae;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar;
    private AlunoController controller;

    public TelaAluno(AlunoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Aluno");
        setSize(500, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("ID Pessoa (apenas para pesquisar/altera/excluir)::"));
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

        panel.add(new JLabel("Matrícula (10 dígitos):"));
        txtMatricula = new JTextField();
        panel.add(txtMatricula);

        panel.add(new JLabel("Nome do Pai:"));
        txtNomePai = new JTextField();
        panel.add(txtNomePai);

        panel.add(new JLabel("Nome da Mãe:"));
        txtNomeMae = new JTextField();
        panel.add(txtNomeMae);

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
            Aluno a = montarAlunoDaTela();
            boolean ok = controller.salvar(a);
            JOptionPane.showMessageDialog(this, ok ? "Salvo com sucesso" : "Erro ao salvar (Lembre: A matrícula deve conter 10 digitos numéricos)");
        });

        btnAlterar.addActionListener(e -> {
            Aluno a = montarAlunoDaTela();
            boolean ok = controller.alterar(a);
            JOptionPane.showMessageDialog(this, ok ? "Alterado com sucesso" : "Erro ao alterar");
        });

        btnExcluir.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this, ok ? "Excluído com sucesso" : "Erro ao excluir");
        });

        btnPesquisar.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            Aluno a = controller.pesquisar(id);
            if (a != null) {
                preencherTelaComAluno(a);
            } else {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado");
            }
        });
    }

    private Aluno montarAlunoDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String matricula = txtMatricula.getText();
        String nomePai = txtNomePai.getText();
        String nomeMae = txtNomeMae.getText();
        return new Aluno(id, nome, endereco, telefone, email, matricula, nomePai, nomeMae);
    }

    private void preencherTelaComAluno(Aluno a) {
        txtId.setText(String.valueOf(a.getId()));
        txtNome.setText(a.getNome());
        txtEndereco.setText(a.getEndereco());
        txtTelefone.setText(a.getTelefone());
        txtEmail.setText(a.getEmail());
        txtMatricula.setText(a.getMatricula());
        txtNomePai.setText(a.getNomePai());
        txtNomeMae.setText(a.getNomeMae());
    }

}

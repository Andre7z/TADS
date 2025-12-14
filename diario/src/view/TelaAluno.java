package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.AlunoController;
import model.Aluno;

public class TelaAluno extends JFrame {

    private JTextField txtId, txtNome, txtEndereco, txtTelefone, txtEmail,
            txtMatricula, txtNomePai, txtNomeMae;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;
    private JComboBox<String> cmbIds; // combo para selecionar IDs já salvas

    private AlunoController controller;

    public TelaAluno(AlunoController controller) {
        this.controller = controller;
        initComponents();
        carregarIdsAlunos(); // carrega as IDs no combo ao abrir a tela
    }

    private void initComponents() {
        setTitle("Cadastro de Aluno");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2));

        // Combo de IDs existentes
        panel.add(new JLabel("Selecionar Aluno salvo:"));
        cmbIds = new JComboBox<>();
        panel.add(cmbIds);

        // ID (somente para visualizar / alterar / excluir)
        panel.add(new JLabel("ID Pessoa (gerado pelo sistema):"));
        txtId = new JTextField();
        txtId.setEditable(false); // usuário não digita ID, só vê
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
        btnSalvar  = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar  = new JButton("Limpar dados");
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
            Aluno a = montarAlunoDaTela(); // id será 0 na inclusão
            boolean ok = controller.salvar(a);
            if (ok) {
                // se o controller/DAO preencheu o id, mostramos aqui
                txtId.setText(String.valueOf(a.getId()));
                JOptionPane.showMessageDialog(this,
                        "Salvo com sucesso. ID gerado: " + a.getId(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarIdsAlunos(); // atualiza combo com o novo registro
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao salvar (Verifique se os campos estão preenchidos corretamente).",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Alterar
        btnAlterar.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um aluno no combo antes de alterar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Aluno a = montarAlunoDaTela();
            boolean ok = controller.alterar(a);
            JOptionPane.showMessageDialog(this,
                    ok ? "Alterado com sucesso" : "Erro ao alterar",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) {
                carregarIdsAlunos();
            }
        });

        // Excluir
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um aluno no combo antes de excluir.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(txtId.getText().trim());
            int conf = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o aluno ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (conf != JOptionPane.YES_OPTION) return;

            boolean ok = controller.excluir(id);
            JOptionPane.showMessageDialog(this,
                    ok ? "Excluído com sucesso" : "Erro ao excluir",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) {
                limparCampos();
                carregarIdsAlunos();
            }
        });

        // Limpar dados
        btnLimpar.addActionListener(e -> limparCampos());

        // Sair
        btnSair.addActionListener(e -> dispose());

        // Seleção no combo de IDs
        cmbIds.addActionListener(e -> selecionarIdDoCombo());
    }

    // Monta o objeto Aluno a partir dos campos
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

    // Limpa os campos da tela
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtNomePai.setText("");
        txtNomeMae.setText("");
        if (cmbIds.getItemCount() > 0) {
            cmbIds.setSelectedIndex(0);
        }
    }

    // Carrega IDs existentes no combo, no formato "id - nome"
    private void carregarIdsAlunos() {
        cmbIds.removeAllItems();
        cmbIds.addItem("Selecione um aluno...");

        List<Aluno> lista = controller.listarTodos();
        if (lista != null) {
            for (Aluno a : lista) {
                String item = a.getId() + " - " + a.getNome();
                cmbIds.addItem(item);
            }
        }
    }

    // Quando o usuário escolhe um item no combo, atualiza o txtId e carrega o aluno
    private void selecionarIdDoCombo() {
        if (cmbIds.getSelectedIndex() <= 0) return; // posição 0 é "Selecione..."

        String item = (String) cmbIds.getSelectedItem(); // ex: "3 - Ana"
        if (item == null || item.isBlank()) return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Aluno a = controller.pesquisar(id);
            if (a != null) {
                preencherTelaComAluno(a);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Aluno não encontrado para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora caso estranho de item mal formatado
        }
    }
}

package view;

import controller.TurmaController;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class TelaTurma extends JFrame {

    private JTextField txtId;
    private JTextField txtNomeTurma;

    private JComboBox<String> cmbDisciplina;
    private JComboBox<String> cmbProfessor;
    private JComboBox<String> cmbPeriodo;

    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    private TurmaController turmaController;

    // mapas para converter "id - nome" -> id
    private HashMap<String, Integer> disciplinaMap = new HashMap<>();
    private HashMap<String, Integer> professorMap = new HashMap<>();
    private HashMap<String, Integer> periodoMap = new HashMap<>();

    public TelaTurma(TurmaController turmaController) {
        this.turmaController = turmaController;

        setTitle("Cadastro de Turma");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        inicializarComponentes();
        adicionarEventos();

        // pede ao controller para carregar os combos
        carregarCombos();

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        painel.add(txtId, gbc);

        // Nome Turma
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Nome da Turma:"), gbc);
        gbc.gridx = 1;
        txtNomeTurma = new JTextField(20);
        painel.add(txtNomeTurma, gbc);

        // Disciplina
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        cmbDisciplina = new JComboBox<>();
        painel.add(cmbDisciplina, gbc);

        // Professor
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Professor:"), gbc);
        gbc.gridx = 1;
        cmbProfessor = new JComboBox<>();
        painel.add(cmbProfessor, gbc);

        // Período
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 1;
        cmbPeriodo = new JComboBox<>();
        painel.add(cmbPeriodo, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);
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
        btnPesquisar.addActionListener(_ -> pesquisarTurma());
        btnLimpar.addActionListener(_ -> limparCampos());
        btnSair.addActionListener(_ -> dispose());
    }

    /* ================== Métodos chamados pelo controller ================== */

    // Tela pede ao controller que traga os dados para os combos
    private void carregarCombos() {
        // cada método do controller devolve um Map<String, Integer> pronto para o combo
        disciplinaMap = turmaController.carregarDisciplinas(cmbDisciplina);
        professorMap = turmaController.carregarProfessores(cmbProfessor);
        periodoMap = turmaController.carregarPeriodos(cmbPeriodo);
    }

    private boolean validarCampos() {
        if (txtNomeTurma.getText().trim().isEmpty()
                || cmbDisciplina.getSelectedIndex() == 0
                || cmbProfessor.getSelectedIndex() == 0
                || cmbPeriodo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Preencha nome da turma e selecione disciplina, professor e período.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Turma montarTurmaDaTela() {
        int id = txtId.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtId.getText().trim());
        String nome = txtNomeTurma.getText().trim();
        int idDisc = disciplinaMap.get(cmbDisciplina.getSelectedItem().toString());
        int idProf = professorMap.get(cmbProfessor.getSelectedItem().toString());
        int idPer = periodoMap.get(cmbPeriodo.getSelectedItem().toString());

        return new Turma(id, nome, idDisc, idProf, idPer);
    }

    private void preencherTelaComTurma(Turma t) {
        txtId.setText(String.valueOf(t.getId()));
        txtNomeTurma.setText(t.getNomeTurma());
        selecionarItemComboPorId(cmbDisciplina, t.getIdDisciplina());
        selecionarItemComboPorId(cmbProfessor, t.getIdProfessor());
        selecionarItemComboPorId(cmbPeriodo, t.getIdPeriodo());
    }

    private void salvarTurma() {
        if (!validarCampos())
            return;
        Turma t = montarTurmaDaTela();
        boolean ok = turmaController.salvar(t);
        if (ok && t.getId() != 0) {
            txtId.setText(String.valueOf(t.getId()));
        }
        JOptionPane.showMessageDialog(this,
                ok ? "Turma salva com sucesso!" : "Erro ao salvar turma.",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void alterarTurma() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para alterar.",
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
    }

    private void excluirTurma() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para excluir.",
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
        if (ok)
            limparCampos();
    }

    private void pesquisarTurma() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para pesquisar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(strId);
        Turma t = turmaController.pesquisar(id);
        if (t != null) {
            preencherTelaComTurma(t);
        } else {
            JOptionPane.showMessageDialog(this, "ID não encontrado.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void selecionarItemComboPorId(JComboBox<String> combo, int id) {
        for (int i = 1; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i);
            if (item.startsWith(id + " -")) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNomeTurma.setText("");
        if (cmbDisciplina.getItemCount() > 0)
            cmbDisciplina.setSelectedIndex(0);
        if (cmbProfessor.getItemCount() > 0)
            cmbProfessor.setSelectedIndex(0);
        if (cmbPeriodo.getItemCount() > 0)
            cmbPeriodo.setSelectedIndex(0);
        txtId.requestFocus();
    }
}

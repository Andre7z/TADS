package view;

import controller.TurmaController;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class TelaTurma extends JFrame {

    private JTextField txtId;
    private JTextField txtNomeTurma;

    private JComboBox<String> cmbIdsTurma;   // NOVO: combo com IDs de turma
    private JComboBox<String> cmbDisciplina;
    private JComboBox<String> cmbProfessor;
    private JComboBox<String> cmbPeriodo;

    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;

    private TurmaController turmaController;

    // mapas para converter "id - nome" -> id
    private HashMap<String, Integer> disciplinaMap = new HashMap<>();
    private HashMap<String, Integer> professorMap = new HashMap<>();
    private HashMap<String, Integer> periodoMap = new HashMap<>();

    public TelaTurma(TurmaController turmaController) {
        this.turmaController = turmaController;

        setTitle("Cadastro de Turma");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 420);
        setLocationRelativeTo(null);

        inicializarComponentes();
        adicionarEventos();

        carregarCombos();      // disciplina, professor, período
        carregarIdsTurma();    // IDs de turmas para o combo

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(5, 5, 5, 5);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("ID Turma (gerado pelo sistema):"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        painel.add(txtId, gbc);

        // Combo IDs Turma
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Selecionar Turma salva:"), gbc);
        gbc.gridx = 1;
        cmbIdsTurma = new JComboBox<>();
        painel.add(cmbIdsTurma, gbc);

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
        btnSalvar  = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnLimpar  = new JButton("Limpar");
        btnSair    = new JButton("Sair");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
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
        btnLimpar.addActionListener(_ -> limparCampos());
        btnSair.addActionListener(_ -> dispose());

        // seleção no combo de IDs
        cmbIdsTurma.addActionListener(_ -> selecionarIdDoCombo());
    }

    /* ================== Métodos de carregamento ================== */

    private void carregarCombos() {
        // cada método do controller devolve um Map<String, Integer> pronto para o combo
        disciplinaMap = turmaController.carregarDisciplinas(cmbDisciplina);
        professorMap  = turmaController.carregarProfessores(cmbProfessor);
        periodoMap    = turmaController.carregarPeriodos(cmbPeriodo);
    }

    private void carregarIdsTurma() {
        cmbIdsTurma.removeAllItems();
        cmbIdsTurma.addItem("Selecione uma turma...");

        List<Turma> lista = turmaController.listarTodos();
        if (lista != null) {
            for (Turma t : lista) {
                String item = t.getId() + " - " + t.getNomeTurma();
                cmbIdsTurma.addItem(item);
            }
        }
    }

    /* ================== Validação e montagem ================== */

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
        int idPer  = periodoMap.get(cmbPeriodo.getSelectedItem().toString());

        return new Turma(id, nome, idDisc, idProf, idPer);
    }

    private void preencherTelaComTurma(Turma t) {
        txtId.setText(String.valueOf(t.getId()));
        txtNomeTurma.setText(t.getNomeTurma());
        selecionarItemComboPorId(cmbDisciplina, t.getIdDisciplina());
        selecionarItemComboPorId(cmbProfessor,  t.getIdProfessor());
        selecionarItemComboPorId(cmbPeriodo,    t.getIdPeriodo());
    }

    /* ================== CRUD ================== */

    private void salvarTurma() {
        if (!validarCampos())
            return;
        Turma t = montarTurmaDaTela();
        boolean ok = turmaController.salvar(t);
        if (ok && t.getId() != 0) {
            txtId.setText(String.valueOf(t.getId()));
            carregarIdsTurma();
        }
        JOptionPane.showMessageDialog(this,
                ok ? "Turma salva com sucesso!" : "Erro ao salvar turma.",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void alterarTurma() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma turma no combo antes de alterar.",
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
        if (ok) carregarIdsTurma();
    }

    private void excluirTurma() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma turma no combo antes de excluir.",
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
        if (ok) {
            limparCampos();
            carregarIdsTurma();
        }
    }

    /* ================== Auxiliares ================== */

    private void selecionarIdDoCombo() {
        if (cmbIdsTurma.getSelectedIndex() <= 0) return;

        String item = (String) cmbIdsTurma.getSelectedItem(); // "3 - Turma A"
        if (item == null || item.isBlank()) return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Turma t = turmaController.pesquisar(id);
            if (t != null) {
                preencherTelaComTurma(t);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Turma não encontrada para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora item inválido
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
        if (cmbIdsTurma.getItemCount() > 0)
            cmbIdsTurma.setSelectedIndex(0);
        if (cmbDisciplina.getItemCount() > 0)
            cmbDisciplina.setSelectedIndex(0);
        if (cmbProfessor.getItemCount() > 0)
            cmbProfessor.setSelectedIndex(0);
        if (cmbPeriodo.getItemCount() > 0)
            cmbPeriodo.setSelectedIndex(0);
        txtNomeTurma.requestFocus();
    }
}

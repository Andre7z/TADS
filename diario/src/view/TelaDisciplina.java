package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

import controller.DisciplinaController;
import controller.ProfessorController;
import model.Disciplina;
import model.Professor;

public class TelaDisciplina extends JFrame {

    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnLimpar, btnSair;
    private JComboBox<String> cmbIds;        // disciplinas já salvas
    private JComboBox<String> cmbProfessor;  // professores

    private HashMap<String, Integer> profMap = new HashMap<>();

    private DisciplinaController disciplinaController;
    private ProfessorController professorController;

    public TelaDisciplina(DisciplinaController disciplinaController,
                          ProfessorController professorController) {
        this.disciplinaController = disciplinaController;
        this.professorController  = professorController;

        initComponents();
        carregarIdsDisciplinas();
        carregarProfessores();
        adicionarEventos();
    }

    private void initComponents() {
        setTitle("Cadastro de Disciplina");
        setSize(600, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        // ID
        panel.add(new JLabel("ID (gerado pelo sistema):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panel.add(txtId);

        // Combo de disciplinas existentes
        panel.add(new JLabel("Selecionar Disciplina salva:"));
        cmbIds = new JComboBox<>();
        panel.add(cmbIds);

        // Nome
        panel.add(new JLabel("Nome Disciplina:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        // Professor
        panel.add(new JLabel("Professor:"));
        cmbProfessor = new JComboBox<>();
        panel.add(cmbProfessor);

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
    }

    private void adicionarEventos() {
        // Salvar
        btnSalvar.addActionListener(e -> {
            Disciplina d = montarDisciplinaDaTela();
            boolean ok = disciplinaController.salvar(d);
            if (ok) {
                txtId.setText(String.valueOf(d.getId()));
                JOptionPane.showMessageDialog(this,
                        "Salvo com sucesso. ID gerado: " + d.getId(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarIdsDisciplinas();
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
                        "Selecione uma disciplina no combo antes de alterar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Disciplina d = montarDisciplinaDaTela();
            boolean ok = disciplinaController.alterar(d);
            JOptionPane.showMessageDialog(this,
                    ok ? "Alterado com sucesso" : "Erro ao alterar",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) carregarIdsDisciplinas();
        });

        // Excluir
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma disciplina no combo antes de excluir.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(txtId.getText().trim());
            int conf = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir a disciplina ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (conf != JOptionPane.YES_OPTION) return;

            boolean ok = disciplinaController.excluir(id);
            JOptionPane.showMessageDialog(this,
                    ok ? "Excluído com sucesso" : "Erro ao excluir",
                    ok ? "Sucesso" : "Erro",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (ok) {
                limparCampos();
                carregarIdsDisciplinas();
            }
        });

        // Limpar
        btnLimpar.addActionListener(e -> limparCampos());

        // Sair
        btnSair.addActionListener(e -> dispose());

        // Seleção no combo de disciplinas
        cmbIds.addActionListener(e -> selecionarIdDoCombo());
    }

    /* ====================== PROFESSOR ====================== */

    private void carregarProfessores() {
        cmbProfessor.removeAllItems();
        cmbProfessor.addItem("Selecione um professor...");
        profMap.clear();

        List<Professor> lista = professorController.listarTodos();
        if (lista != null) {
            for (Professor p : lista) {
                String item = p.getId() + " - " + p.getNome();
                cmbProfessor.addItem(item);
                profMap.put(item, p.getId());
            }
        }
    }

    /* ====================== DISCIPLINA ====================== */

    private Disciplina montarDisciplinaDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String nome = txtNome.getText();

        String itemProf = (String) cmbProfessor.getSelectedItem();
        Professor professor = null;
        if (itemProf != null && profMap.containsKey(itemProf)) {
            int idProfessor = profMap.get(itemProf);
            professor = new Professor();
            professor.setId(idProfessor);
        }

        // construtor: Disciplina(int id, String nome, Professor professor)
        return new Disciplina(id, nome, professor);
    }

    private void preencherTelaComDisciplina(Disciplina d) {
        txtId.setText(String.valueOf(d.getId()));
        txtNome.setText(d.getNomeDisciplina());

        int idProf = (d.getProfessor() != null) ? d.getProfessor().getId() : 0;

        // selecionar professor pelo id
        for (int i = 1; i < cmbProfessor.getItemCount(); i++) {
            String item = cmbProfessor.getItemAt(i);
            if (item.startsWith(idProf + " -")) {
                cmbProfessor.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        if (cmbIds.getItemCount() > 0) cmbIds.setSelectedIndex(0);
        if (cmbProfessor.getItemCount() > 0) cmbProfessor.setSelectedIndex(0);
    }

    private void carregarIdsDisciplinas() {
        cmbIds.removeAllItems();
        cmbIds.addItem("Selecione uma disciplina...");

        List<Disciplina> lista = disciplinaController.listarTodos();
        if (lista != null) {
            for (Disciplina d : lista) {
                String item = d.getId() + " - " + d.getNomeDisciplina();
                cmbIds.addItem(item);
            }
        }
    }

    private void selecionarIdDoCombo() {
        if (cmbIds.getSelectedIndex() <= 0) return;

        String item = (String) cmbIds.getSelectedItem();
        if (item == null || item.isBlank()) return;

        String strId = item.split(" - ")[0].trim();
        try {
            int id = Integer.parseInt(strId);
            txtId.setText(String.valueOf(id));

            Disciplina d = disciplinaController.pesquisar(id);
            if (d != null) {
                preencherTelaComDisciplina(d);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Disciplina não encontrada para ID " + id,
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // ignora item inválido
        }
    }
}

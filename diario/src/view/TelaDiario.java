package view;

import javax.swing.*;

import controller.DiarioController;
import controller.NotaController;
import dao.ConnectionFactory;
import dao.DiarioDAO;
import dao.NotaDAO;
import model.Diario;
import model.Nota;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class TelaDiario extends JFrame {

    private JTextField txtId;
    private JComboBox<String> cmbAluno;
    private JComboBox<String> cmbDisciplina;
    private JComboBox<String> cmbPeriodo;
    private JComboBox<String> cmbTurma;
    private JCheckBox chkAprovado;

    private JTextField txtNota;
    private DefaultListModel<String> modeloNotas;
    private JList<String> listaNotas;
    private JLabel lblMediaSituacao;

    private JButton btnAdicionarNota, btnSalvarNotas;
    private JButton btnEditarNota, btnRemoverNota; // NOVOS
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    private HashMap<String, Integer> alunoMap = new HashMap<>();
    private HashMap<String, Integer> disciplinaMap = new HashMap<>();
    private HashMap<String, Integer> periodoMap = new HashMap<>();
    private HashMap<String, Integer> turmaMap = new HashMap<>();

    private DiarioController diarioController;
    private NotaController notaController;
    private Connection conn;

    private Nota nota; // objeto em memória com as notas digitadas

    public TelaDiario(DiarioController diarioController,
            NotaController notaController, Connection conn) {
        this.diarioController = diarioController;
        this.notaController = notaController;
        this.conn = conn;
        this.nota = new Nota();

        setTitle("Cadastro de Diário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarAlunos();
        carregarDisciplinas();
        carregarPeriodos();
        carregarTurmas();

        adicionarEventos();

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
        painel.add(new JLabel("ID Diário(apenas para pesquisar/altera/excluir)::"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        painel.add(txtId, gbc);

        // Aluno
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        cmbAluno = new JComboBox<>();
        painel.add(cmbAluno, gbc);

        // Disciplina
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        cmbDisciplina = new JComboBox<>();
        painel.add(cmbDisciplina, gbc);

        // Período
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 1;
        cmbPeriodo = new JComboBox<>();
        painel.add(cmbPeriodo, gbc);

        // Turma
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        cmbTurma = new JComboBox<>();
        painel.add(cmbTurma, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Aprovado?"), gbc);
        gbc.gridx = 1;
        chkAprovado = new JCheckBox();
        chkAprovado.setEnabled(false); // calculado pelas notas
        painel.add(chkAprovado, gbc);

        // Painel de Notas
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel painelNotas = new JPanel(new BorderLayout());
        painelNotas.setBorder(BorderFactory.createTitledBorder("Notas do Diário"));

        JPanel painelEntradaNota = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEntradaNota.add(new JLabel("Nota (0 a 10):"));
        txtNota = new JTextField(5);
        painelEntradaNota.add(txtNota);
        btnAdicionarNota = new JButton("Adicionar Nota");
        painelEntradaNota.add(btnAdicionarNota);

        btnEditarNota = new JButton("Editar Nota"); // NOVO
        painelEntradaNota.add(btnEditarNota);
        btnRemoverNota = new JButton("Remover Nota"); // NOVO
        painelEntradaNota.add(btnRemoverNota);

        modeloNotas = new DefaultListModel<>();
        listaNotas = new JList<>(modeloNotas);
        JScrollPane scrollNotas = new JScrollPane(listaNotas);

        lblMediaSituacao = new JLabel("Média: -  |  Situação: -");
        btnSalvarNotas = new JButton("Salvar Notas e Atualizar Status");

        JPanel painelSulNotas = new JPanel(new BorderLayout());
        painelSulNotas.add(lblMediaSituacao, BorderLayout.CENTER);
        painelSulNotas.add(btnSalvarNotas, BorderLayout.EAST);

        painelNotas.add(painelEntradaNota, BorderLayout.NORTH);
        painelNotas.add(scrollNotas, BorderLayout.CENTER);
        painelNotas.add(painelSulNotas, BorderLayout.SOUTH);

        painel.add(painelNotas, gbc);

        // Botões CRUD
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar Diário");
        btnAlterar = new JButton("Alterar Diário");
        btnExcluir = new JButton("Excluir Diário");
        btnPesquisar = new JButton("Pesquisar Diário");
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

    /* ====================== CARREGAR COMBOS ====================== */
    private void carregarDisciplinas() {
        try (Connection c = ConnectionFactory.getConnection()) {
            String sql = "SELECT id, nome_disciplina FROM disciplina ORDER BY nome_disciplina";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            cmbDisciplina.removeAllItems();
            cmbDisciplina.addItem("Selecione a disciplina...");
            disciplinaMap.clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_disciplina");
                String item = id + " - " + nome;
                cmbDisciplina.addItem(item);
                disciplinaMap.put(item, id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar disciplinas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPeriodos() {
        try (Connection c = ConnectionFactory.getConnection()) {
            String sql = "SELECT id, nome_periodo FROM periodo ORDER BY nome_periodo";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            cmbPeriodo.removeAllItems();
            cmbPeriodo.addItem("Selecione o período...");
            periodoMap.clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_periodo");
                String item = id + " - " + nome;
                cmbPeriodo.addItem(item);
                periodoMap.put(item, id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar períodos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTurmas() {
        try (Connection c = ConnectionFactory.getConnection()) {
            String sql = "SELECT id, nome_turma FROM turma ORDER BY nome_turma";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            cmbTurma.removeAllItems();
            cmbTurma.addItem("Selecione a turma...");
            turmaMap.clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome_turma");
                String item = id + " - " + nome;
                cmbTurma.addItem(item);
                turmaMap.put(item, id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar turmas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarAlunos() {
        try (Connection c = ConnectionFactory.getConnection()) {
            String sql = "SELECT pe.id, pe.nome FROM aluno a " +
                    "JOIN pessoa pe ON pe.id = a.id_pessoa ORDER BY pe.nome";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            cmbAluno.removeAllItems();
            cmbAluno.addItem("Selecione um aluno...");
            alunoMap.clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String item = id + " - " + nome;
                cmbAluno.addItem(item);
                alunoMap.put(item, id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar alunos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* ====================== EVENTOS ====================== */

    private void adicionarEventos() {
        btnSalvar.addActionListener(_ -> salvarDiario());
        btnAlterar.addActionListener(_ -> alterarDiario());
        btnExcluir.addActionListener(_ -> excluirDiario());
        btnPesquisar.addActionListener(_ -> pesquisarDiario());
        btnLimpar.addActionListener(_ -> limparCampos());
        btnSair.addActionListener(_ -> dispose());

        btnAdicionarNota.addActionListener(_ -> adicionarNotaNaLista());
        btnSalvarNotas.addActionListener(_ -> salvarNotasEDefinirStatus());
        btnEditarNota.addActionListener(_ -> editarNotaSelecionada());
        btnRemoverNota.addActionListener(_ -> removerNotaSelecionada());
    }

    /* ====================== CRUD DIÁRIO ====================== */

    private boolean validarCamposBasicos() {
        if (txtId.getText().trim().isEmpty()
                || cmbAluno.getSelectedIndex() == 0
                || cmbDisciplina.getSelectedIndex() == 0
                || cmbPeriodo.getSelectedIndex() == 0
                || cmbTurma.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Preencha ID e selecione aluno, disciplina, período e turma.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Diario montarDiarioDaTela() {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText().trim());
        int idAluno = alunoMap.get(cmbAluno.getSelectedItem().toString());
        int idDisc = disciplinaMap.get(cmbDisciplina.getSelectedItem().toString());
        int idPer = periodoMap.get(cmbPeriodo.getSelectedItem().toString());
        int idTur = turmaMap.get(cmbTurma.getSelectedItem().toString());
        boolean status = chkAprovado.isSelected();
        return new Diario(id, idDisc, idPer, idTur, idAluno, status);
    }

    private void preencherTelaComDiario(Diario d) {
        txtId.setText(String.valueOf(d.getId()));
        selecionarItemComboPorId(cmbDisciplina, d.getIdDisciplina());
        selecionarItemComboPorId(cmbPeriodo, d.getIdPeriodo());
        selecionarItemComboPorId(cmbTurma, d.getIdTurma());
        selecionarItemComboPorId(cmbAluno, d.getIdAluno());
        chkAprovado.setSelected(d.isStatus());
    }

    private void salvarDiario() {
        if (!validarCamposBasicos())
            return;
        Diario d = montarDiarioDaTela();
        boolean ok = diarioController.salvar(d);
        if (ok && d.getId() != 0) {
            txtId.setText(String.valueOf(d.getId()));
        }
        JOptionPane.showMessageDialog(this,
                ok ? "Diário salvo com sucesso!" : "Erro ao salvar diário",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void alterarDiario() {
        if (!validarCamposBasicos())
            return;
        Diario d = montarDiarioDaTela();
        boolean ok = diarioController.alterar(d);
        JOptionPane.showMessageDialog(this,
                ok ? "Diário alterado com sucesso!" : "Erro ao alterar diário",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void excluirDiario() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para excluir!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o diário?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        int id = Integer.parseInt(strId);
        boolean ok = diarioController.excluir(id);
        JOptionPane.showMessageDialog(this,
                ok ? "Diário excluído com sucesso!" : "Erro ao excluir diário",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (ok)
            limparCampos();
    }

    private void pesquisarDiario() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para pesquisar!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(strId);
        Diario d = diarioController.pesquisar(id);
        if (d != null) {
            preencherTelaComDiario(d);
            carregarNotasDoDiario(id);
        } else {
            JOptionPane.showMessageDialog(this, "Diário não encontrado!",
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

    /* ====================== NOTAS ====================== */

    private void adicionarNotaNaLista() {
        try {
            double valor = Double.parseDouble(txtNota.getText().trim());
            nota.adicionarNota(valor); // valida 0..10 e atualiza média/aprovado
            modeloNotas.addElement(String.valueOf(valor));
            atualizarMediaLocal();
            txtNota.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Informe uma nota válida entre 0 e 10!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarNotaSelecionada() {
        int index = listaNotas.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma nota na lista para editar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String valorAtual = modeloNotas.getElementAt(index);
        String novoValorStr = JOptionPane.showInputDialog(this,
                "Informe o novo valor da nota (0 a 10):", valorAtual);

        if (novoValorStr == null) {
            return; // cancelado
        }

        try {
            double novoValor = Double.parseDouble(novoValorStr.trim());
            if (novoValor < 0 || novoValor > 10) {
                throw new IllegalArgumentException();
            }

            nota.getNotas().set(index, novoValor);
            modeloNotas.setElementAt(String.valueOf(novoValor), index);

            atualizarMediaLocal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Informe um valor numérico entre 0 e 10.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerNotaSelecionada() {
        int index = listaNotas.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma nota na lista para remover.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int conf = JOptionPane.showConfirmDialog(this,
                "Deseja realmente remover a nota selecionada?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION)
            return;

        nota.getNotas().remove(index);
        modeloNotas.remove(index);

        atualizarMediaLocal();
    }

    private void atualizarMediaLocal() {
        lblMediaSituacao.setText("Média: " + nota.getMedia() +
                "  |  Situação: " + (nota.isAprovado() ? "Aprovado" : "Reprovado"));
        chkAprovado.setSelected(nota.isAprovado());
    }

    private void salvarNotasEDefinirStatus() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Salve ou pesquise o diário antes de lançar notas.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nota.getNotas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos uma nota.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idDiario = Integer.parseInt(strId);
        nota.setIdDiario(idDiario);
        boolean ok = notaController.salvarNotasEAtualizarStatus(nota);
        JOptionPane.showMessageDialog(this,
                ok ? "Notas salvas e status atualizado!" : "Erro ao salvar notas",
                ok ? "Sucesso" : "Erro",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (ok) {
            Diario d = diarioController.pesquisar(idDiario);
            if (d != null)
                chkAprovado.setSelected(d.isStatus());
        }
    }

    private void carregarNotasDoDiario(int idDiario) {
        modeloNotas.clear();
        nota = new Nota();
        try {
            NotaDAO nDAO = new NotaDAO(conn);
            List<Double> lista = nDAO.listarNotasPorDiario(idDiario);
            for (Double v : lista) {
                nota.adicionarNota(v);
                modeloNotas.addElement(String.valueOf(v));
            }
            atualizarMediaLocal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar notas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        cmbAluno.setSelectedIndex(0);
        cmbDisciplina.setSelectedIndex(0);
        cmbPeriodo.setSelectedIndex(0);
        cmbTurma.setSelectedIndex(0);
        chkAprovado.setSelected(false);
        modeloNotas.clear();
        nota = new Nota();
        atualizarMediaLocal();
        txtId.requestFocus();
    }

    /* MAIN opcional de teste isolado */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conn = ConnectionFactory.getConnection();
            DiarioDAO dDAO = new DiarioDAO(conn);
            NotaDAO nDAO = new NotaDAO(conn);

            DiarioController dCtrl = new DiarioController(dDAO, nDAO, conn);
            NotaController nCtrl = new NotaController(nDAO, dDAO);
            new TelaDiario(dCtrl, nCtrl, conn).setVisible(true);

        });
    }
}
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import dao.Conexao;
import model.*;

public class DiarioForm extends JFrame {
    private static final Logger logger = Logger.getLogger(DiarioForm.class.getName());

    private JTextField txtId;
    private JComboBox<Aluno> cbAluno;
    private JComboBox<Disciplina> cbDisciplina;
    private JComboBox<Periodo> cbPeriodo;
    private JComboBox<Turma> cbTurma;

    private JTable tabelaNotas;
    private DefaultTableModel modeloNotas;

    private JButton btnAddNota, btnRemoveNota, btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public DiarioForm() {
        setTitle("Cadastro de Diário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        inicializar();
        carregarCombos();
        setVisible(true);
    }

    private void inicializar() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int linha = 0;
        gbc.gridx = 0;
        gbc.gridy = linha;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(8);
        painel.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        cbAluno = new JComboBox<>();
        painel.add(cbAluno, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        cbDisciplina = new JComboBox<>();
        painel.add(cbDisciplina, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Período:"), gbc);
        gbc.gridx = 1;
        cbPeriodo = new JComboBox<>();
        painel.add(cbPeriodo, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        painel.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        cbTurma = new JComboBox<>();
        painel.add(cbTurma, gbc);

        modeloNotas = new DefaultTableModel(new Object[] { "Nota" }, 0);
        tabelaNotas = new JTable(modeloNotas);
        btnAddNota = new JButton("Adicionar Nota");
        btnRemoveNota = new JButton("Remover Nota");
        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        JPanel painelNotas = new JPanel(new BorderLayout());
        painelNotas.add(new JScrollPane(tabelaNotas), BorderLayout.CENTER);
        JPanel pb = new JPanel();
        pb.add(btnAddNota);
        pb.add(btnRemoveNota);
        painelNotas.add(pb, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = ++linha;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        painel.add(painelNotas, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = ++linha;
        JPanel botoes = new JPanel();
        botoes.add(btnSalvar);
        botoes.add(btnAlterar);
        botoes.add(btnExcluir);
        botoes.add(btnPesquisar);
        botoes.add(btnLimpar);
        botoes.add(btnSair);
        painel.add(botoes, gbc);

        add(painel);

        // actions
        btnAddNota.addActionListener(e -> modeloNotas.addRow(new Object[] { 0.0 }));
        btnRemoveNota.addActionListener(e -> {
            int r = tabelaNotas.getSelectedRow();
            if (r >= 0)
                modeloNotas.removeRow(r);
        });
        btnSalvar.addActionListener(e -> salvar());
        btnPesquisar.addActionListener(e -> pesquisar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());
        btnSair.addActionListener(e -> dispose());
    }

    private void carregarCombos() {
        cbAluno.removeAllItems();
        cbDisciplina.removeAllItems();
        cbPeriodo.removeAllItems();
        cbTurma.removeAllItems();
        try (Connection c = Conexao.getConnection()) {
            ResultSet rs = c
                    .prepareStatement(
                            "SELECT p.id, p.nome, a.matricula FROM pessoa p JOIN aluno a ON p.id=a.id ORDER BY p.nome")
                    .executeQuery();
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setMatricula(rs.getInt("matricula"));
                cbAluno.addItem(a);
            }
            rs = c.prepareStatement("SELECT id,nome FROM disciplina ORDER BY nome").executeQuery();
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome_disciplina(rs.getString("nome"));
                cbDisciplina.addItem(d);
            }
            rs = c.prepareStatement("SELECT * FROM periodo ORDER BY nome").executeQuery();
            while (rs.next()) {
                Periodo p = new Periodo();
                p.setId(rs.getInt("id"));
                p.setNome_periodo(rs.getString("nome"));
                cbPeriodo.addItem(p);
            }
            rs = c.prepareStatement("SELECT * FROM turma ORDER BY nome").executeQuery();
            while (rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setNome_turma(rs.getString("nome"));
                cbTurma.addItem(t);
            }
            logger.info("Combos carregados");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro carregar combos diario", e);
            JOptionPane.showMessageDialog(this, "Erro ao carregar combos: " + e.getMessage());
        }
    }

    private void salvar() {
        // valida
        if (cbAluno.getSelectedItem() == null || cbDisciplina.getSelectedItem() == null
                || cbPeriodo.getSelectedItem() == null || cbTurma.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Preencha aluno, disciplina, período e turma");
            return;
        }
        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            String sqlDiario = "INSERT INTO diario(status, aluno_id, disciplina_id, periodo_id, turma_id) VALUES(?,?,?,?,?) RETURNING id";
            try (PreparedStatement ps = c.prepareStatement(sqlDiario)) {
                ps.setBoolean(1, true);
                ps.setInt(2, ((Aluno) cbAluno.getSelectedItem()).getId());
                ps.setInt(3, ((Disciplina) cbDisciplina.getSelectedItem()).getId());
                ps.setInt(4, ((Periodo) cbPeriodo.getSelectedItem()).getId());
                ps.setInt(5, ((Turma) cbTurma.getSelectedItem()).getId());
                ResultSet rs = ps.executeQuery();
                int diarioId = 0;
                if (rs.next())
                    diarioId = rs.getInt("id");
                // inserir notas
                String sqlNota = "INSERT INTO nota(valor, diario_id) VALUES(?,?)";
                try (PreparedStatement psn = c.prepareStatement(sqlNota)) {
                    for (int i = 0; i < modeloNotas.getRowCount(); i++) {
                        double v = Double.parseDouble(modeloNotas.getValueAt(i, 0).toString());
                        if (v < 0 || v > 10) {
                            JOptionPane.showMessageDialog(this, "Notas devem estar entre 0 e 10");
                            c.rollback();
                            return;
                        }
                        psn.setDouble(1, v);
                        psn.setInt(2, diarioId);
                        psn.addBatch();
                    }
                    psn.executeBatch();
                }
                // calcular média e atualizar diário
                try (PreparedStatement psMedia = c
                        .prepareStatement("SELECT AVG(valor) AS media FROM nota WHERE diario_id=?")) {
                    psMedia.setInt(1, diarioId);
                    ResultSet rsMedia = psMedia.executeQuery();
                    double media = 0;
                    if (rsMedia.next())
                        media = rsMedia.getDouble("media");
                    String resultado = media >= 6.0 ? "Aprovado" : "Reprovado";
                    try (PreparedStatement psUpd = c.prepareStatement(
                            "UPDATE diario SET /*media=?, resultado=? ,*/ /*optional fields if exist*/ WHERE id=?")) {
                        // se você tiver colunas media/resultado em diario, atualize aqui.
                        // psUpd.setDouble(1, media);
                        // psUpd.setString(2, resultado);
                        // psUpd.setInt(3, diarioId);
                        // psUpd.executeUpdate();
                    }
                }
                c.commit();
                txtId.setText(String.valueOf(diarioId));
                logger.info("Diário salvo id=" + diarioId);
                JOptionPane.showMessageDialog(this, "Diário salvo com sucesso");
                limpar();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro salvar diario", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void pesquisar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        int id = Integer.parseInt(txtId.getText().trim());
        String sql = "SELECT * FROM diario WHERE id=?";
        try (Connection c = Conexao.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtId.setText(String.valueOf(rs.getInt("id")));
                int alunoId = rs.getInt("aluno_id");
                int discId = rs.getInt("disciplina_id");
                int perId = rs.getInt("periodo_id");
                int turmaId = rs.getInt("turma_id");
                // selecionar nos combos
                for (int i = 0; i < cbAluno.getItemCount(); i++)
                    if (cbAluno.getItemAt(i).getId() == alunoId)
                        cbAluno.setSelectedIndex(i);
                for (int i = 0; i < cbDisciplina.getItemCount(); i++)
                    if (cbDisciplina.getItemAt(i).getId() == discId)
                        cbDisciplina.setSelectedIndex(i);
                for (int i = 0; i < cbPeriodo.getItemCount(); i++)
                    if (cbPeriodo.getItemAt(i).getId() == perId)
                        cbPeriodo.setSelectedIndex(i);
                for (int i = 0; i < cbTurma.getItemCount(); i++)
                    if (cbTurma.getItemAt(i).getId() == turmaId)
                        cbTurma.setSelectedIndex(i);
                // carregar notas
                modeloNotas.setRowCount(0);
                try (PreparedStatement psn = c
                        .prepareStatement("SELECT valor FROM nota WHERE diario_id=? ORDER BY id")) {
                    psn.setInt(1, id);
                    ResultSet rsn = psn.executeQuery();
                    while (rsn.next())
                        modeloNotas.addRow(new Object[] { rsn.getDouble("valor") });
                }
                logger.info("Diário carregado id=" + id);
            } else
                JOptionPane.showMessageDialog(this, "Não encontrado");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro pesquisar diario", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        int id = Integer.parseInt(txtId.getText().trim());
        try (Connection c = Conexao.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE diario SET aluno_id=?, disciplina_id=?, periodo_id=?, turma_id=? WHERE id=?")) {
                ps.setInt(1, ((Aluno) cbAluno.getSelectedItem()).getId());
                ps.setInt(2, ((Disciplina) cbDisciplina.getSelectedItem()).getId());
                ps.setInt(3, ((Periodo) cbPeriodo.getSelectedItem()).getId());
                ps.setInt(4, ((Turma) cbTurma.getSelectedItem()).getId());
                ps.setInt(5, id);
                ps.executeUpdate();
            }
            try (PreparedStatement pdel = c.prepareStatement("DELETE FROM nota WHERE diario_id=?")) {
                pdel.setInt(1, id);
                pdel.executeUpdate();
            }
            try (PreparedStatement pins = c.prepareStatement("INSERT INTO nota(valor,diario_id) VALUES(?,?)")) {
                for (int i = 0; i < modeloNotas.getRowCount(); i++) {
                    double v = Double.parseDouble(modeloNotas.getValueAt(i, 0).toString());
                    if (v < 0 || v > 10) {
                        JOptionPane.showMessageDialog(this, "Notas 0-10");
                        c.rollback();
                        return;
                    }
                    pins.setDouble(1, v);
                    pins.setInt(2, id);
                    pins.addBatch();
                }
                pins.executeBatch();
            }
            c.commit();
            logger.info("Diário alterado id=" + id);
            JOptionPane.showMessageDialog(this, "Alterado");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro alterar diario", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe ID");
            return;
        }
        int id = Integer.parseInt(txtId.getText().trim());
        try (Connection c = Conexao.getConnection()) {
            try (PreparedStatement psn = c.prepareStatement("DELETE FROM nota WHERE diario_id=?")) {
                psn.setInt(1, id);
                psn.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement("DELETE FROM diario WHERE id=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            logger.info("Diário excluído id=" + id);
            JOptionPane.showMessageDialog(this, "Excluído");
            limpar();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro excluir diario", e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void limpar() {
        txtId.setText("");
        modeloNotas.setRowCount(0);
        if (cbAluno.getItemCount() > 0)
            cbAluno.setSelectedIndex(0);
        if (cbDisciplina.getItemCount() > 0)
            cbDisciplina.setSelectedIndex(0);
        if (cbPeriodo.getItemCount() > 0)
            cbPeriodo.setSelectedIndex(0);
        if (cbTurma.getItemCount() > 0)
            cbTurma.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiarioForm::new);
    }
}

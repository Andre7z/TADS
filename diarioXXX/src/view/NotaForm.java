package view;

import dao.DiarioDAO;
import dao.NotaDAO;
import model.Diario;
import model.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.logging.*;

public class NotaForm extends JFrame {

    private static final Logger logger = Logger.getLogger(NotaForm.class.getName());

    private JTextField txtId, txtValor;
    private JComboBox<Diario> cbDiario;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnSalvar, btnExcluir, btnPesquisar, btnListar, btnLimpar, btnSair;

    private NotaDAO notaDAO = new NotaDAO();
    private DiarioDAO diarioDAO = new DiarioDAO();

    public NotaForm() {
        setTitle("Cadastro de Notas");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarDiarios();
        configurarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int linha = 0;

        // ID
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("ID Nota:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        painel.add(txtId, gbc);

        // Diário
        gbc.gridx = 0; gbc.gridy = ++linha;
        painel.add(new JLabel("Diário:"), gbc);
        gbc.gridx = 1;
        cbDiario = new JComboBox<>();
        painel.add(cbDiario, gbc);

        // Valor
        gbc.gridx = 0; gbc.gridy = ++linha;
        painel.add(new JLabel("Valor (0-10):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(10);
        painel.add(txtValor, gbc);

        // Tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Diário", "Valor"}, 0);
        tabela = new JTable(modelo);

        gbc.gridx = 0; gbc.gridy = ++linha; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 1;
        painel.add(new JScrollPane(tabela), gbc);

        // Botões
        JPanel botoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");
        btnListar = new JButton("Listar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        botoes.add(btnSalvar);
        botoes.add(btnPesquisar);
        botoes.add(btnExcluir);
        botoes.add(btnListar);
        botoes.add(btnLimpar);
        botoes.add(btnSair);

        gbc.gridx = 0; gbc.gridy = ++linha; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        painel.add(botoes, gbc);

        add(painel);
    }

    private void configurarEventos() {

        btnSalvar.addActionListener(_ -> salvar());
        btnExcluir.addActionListener(_ -> excluir());
        btnPesquisar.addActionListener(_ -> pesquisar());
        btnListar.addActionListener(_ -> listar());
        btnLimpar.addActionListener(_ -> limpar());
        btnSair.addActionListener(_ -> dispose());
    }

    private void carregarDiarios() {
        try {
            List<Diario> diarios = diarioDAO.listar();
            for (Diario d : diarios)
                cbDiario.addItem(d);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao carregar diários", e);
            JOptionPane.showMessageDialog(this, "Erro ao carregar diários.");
        }
    }

    private void salvar() {
        try {
            double valor = Double.parseDouble(txtValor.getText());

            if (valor < 0 || valor > 10) {
                JOptionPane.showMessageDialog(this, "Valor deve ser entre 0 e 10.");
                return;
            }

            Nota n = new Nota();
            n.setValor(valor);
            n.setDiario((Diario) cbDiario.getSelectedItem());

            boolean ok = notaDAO.inserir(n);

            if (ok) {
                logger.info("Nota salva com sucesso");
                JOptionPane.showMessageDialog(this, "Nota salva!");
                listar();
                limpar();
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao salvar nota", e);
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = notaDAO.excluir(id);

            if (ok) {
                logger.info("Nota excluída");
                JOptionPane.showMessageDialog(this, "Excluída!");
                listar();
                limpar();
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao excluir nota", e);
            JOptionPane.showMessageDialog(this, "Erro ao excluir.");
        }
    }

    private void pesquisar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o ID!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            Nota n = notaDAO.buscarPorId(id);

            if (n != null) {
                txtValor.setText(String.valueOf(n.getValor()));
                cbDiario.setSelectedItem(n.getDiario());
            } else {
                JOptionPane.showMessageDialog(this, "Nota não encontrada.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao pesquisar nota", e);
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar.");
        }
    }

    private void listar() {
        try {
            modelo.setRowCount(0);

            List<Nota> lista = notaDAO.listar();

            for (Nota n : lista) {
                modelo.addRow(new Object[]{
                        n.getId(),
                        n.getDiario().getId(),
                        n.getValor()
                });
            }

            logger.info("Lista de notas carregada.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao listar notas", e);
        }
    }

    private void limpar() {
        txtId.setText("");
        txtValor.setText("");
        cbDiario.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotaForm::new);
    }
}

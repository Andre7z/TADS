import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormCadastroLivro extends JFrame {

    // Componentes da interface
    private JTextField txtId;
    private JTextField txtNome;
    private JTextArea txtResenha;
    private JTextField txtEdicao;
    private JTextField txtAnoPublicacao;
    private JTextField txtNumeroPaginas;
    private JComboBox<String> cmbAutor;
    private JComboBox<String> cmbEditora;
    private JComboBox<String> cmbTipoCapa;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;

    public FormCadastroLivro() {
        // Configurações da janela
        setTitle("Cadastro de Livro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null); // Centralizar na tela

        // Inicializar componentes
        inicializarComponentes();

        // Tornar a janela visível
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Criar painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        String[] editoras = { "Selecione uma editora...", "Companhia das Letras", "Record", "Globo", "Saraiva" };
        String[] autores = { "Selecione um autor...", "Flávio Vilela", "Professor JP", "João da Silva",
                "José da Silva" };
        String[] tiposCapa = { "Selecione um tipo...", "Dura", "Brochura", "Espiral" };

        // Configurações gerais do GridBagConstraints
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campo ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtId = new JTextField(15);
        painelPrincipal.add(txtId, gbc);

        // Campo Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNome = new JTextField(15);
        painelPrincipal.add(txtNome, gbc);

        // Campo Resenha (TextArea com scroll)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Resenha:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        txtResenha = new JTextArea(3, 15);
        txtResenha.setLineWrap(true);
        txtResenha.setWrapStyleWord(true);
        JScrollPane scrollResenha = new JScrollPane(txtResenha);
        painelPrincipal.add(scrollResenha, gbc);

        // Campo Edição
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        painelPrincipal.add(new JLabel("Edição:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtEdicao = new JTextField(15);
        painelPrincipal.add(txtEdicao, gbc);

        // Campo Ano de Publicação
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Ano de Publicação:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtAnoPublicacao = new JTextField(15);
        painelPrincipal.add(txtAnoPublicacao, gbc);

        // Campo Número de Páginas
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Número de Páginas:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNumeroPaginas = new JTextField(15);
        painelPrincipal.add(txtNumeroPaginas, gbc);

        // ComboBox Autor
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Autor:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cmbAutor = new JComboBox<>(autores);
        painelPrincipal.add(cmbAutor, gbc);

        // ComboBox Editora
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Editora:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbEditora = new JComboBox<>(editoras);
        painelPrincipal.add(cmbEditora, gbc);

        // ComboBox Tipo de Capa
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Tipo de Capa:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cmbTipoCapa = new JComboBox<>(tiposCapa);
        painelPrincipal.add(cmbTipoCapa, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        // Adicionar listeners aos botões
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarLivro();
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        // Adicionar painel de botões ao painel principal
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);

        // Adicionar painel principal à janela
        add(painelPrincipal);
    }

    private void salvarLivro() {
        // Validar se os campos obrigatórios estão preenchidos
        if (txtId.getText().trim().isEmpty() ||
                txtNome.getText().trim().isEmpty() ||
                txtResenha.getText().trim().isEmpty() ||
                txtEdicao.getText().trim().isEmpty() ||
                txtAnoPublicacao.getText().trim().isEmpty() ||
                txtNumeroPaginas.getText().trim().isEmpty() ||
                cmbAutor.getSelectedIndex() == 0 ||
                cmbEditora.getSelectedIndex() == 0 ||
                cmbTipoCapa.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar se os campos numéricos são válidos
        try {
            Integer.parseInt(txtId.getText().trim());
            Integer.parseInt(txtEdicao.getText().trim());
            Integer.parseInt(txtAnoPublicacao.getText().trim());
            Integer.parseInt(txtNumeroPaginas.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Os campos ID, Edição, Ano de Publicação e Número de Páginas devem conter apenas números!",
                    "Valores inválidos",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Criar objetos necessários
            Autor autor = new Autor(cmbAutor.getSelectedIndex(),
                    cmbAutor.getSelectedItem().toString(),
                    "Cidade não informada");

            Editora editora = new Editora();
            editora.setId(cmbEditora.getSelectedIndex());
            editora.setNome(cmbEditora.getSelectedItem().toString());

            TipoCapa tipoCapa = new TipoCapa(cmbTipoCapa.getSelectedIndex(),
                    cmbTipoCapa.getSelectedItem().toString());

            Livro livro = new Livro(
                    Integer.parseInt(txtId.getText().trim()),
                    txtNome.getText().trim(),
                    txtResenha.getText().trim(),
                    Integer.parseInt(txtEdicao.getText().trim()),
                    Integer.parseInt(txtAnoPublicacao.getText().trim()),
                    Integer.parseInt(txtNumeroPaginas.getText().trim()),
                    autor,
                    editora,
                    tipoCapa);

            livro.salvar();

            salvarNoArquivo(livro);

            JOptionPane.showMessageDialog(this,
                    "Livro cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCampos();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar dados no arquivo: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarNoArquivo(Livro livro) throws IOException {
        // Obter data e hora atual
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = agora.format(formatter);

        // Criar o conteúdo a ser salvo
        String conteudo = String.format(
                "=== LIVRO CADASTRADO ===\n" +
                        "Data/Hora: %s\n" +
                        "ID: %s\n" +
                        "Nome: %s\n" +
                        "Resenha: %s\n" +
                        "Edição: %s\n" +
                        "Ano de Publicação: %s\n" +
                        "Número de Páginas: %s\n" +
                        "Autor: %s\n" +
                        "Editora: %s\n" +
                        "Tipo de Capa: %s\n" +
                        "=========================\n\n",
                dataHora,
                livro.getId(),
                livro.getNome(),
                livro.getResenha(),
                livro.getEdicao(),
                livro.getAnoPublicacao(),
                livro.getNumeroPaginas(),
                livro.getAutor().getNome(),
                livro.getEditora().getNome(),
                livro.getTipoCapa().getNome());

        try (FileWriter writer = new FileWriter("livros.txt", true)) {
            writer.write(conteudo);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtResenha.setText("");
        txtEdicao.setText("");
        txtAnoPublicacao.setText("");
        txtNumeroPaginas.setText("");
        cmbAutor.setSelectedIndex(0);
        cmbEditora.setSelectedIndex(0);
        cmbTipoCapa.setSelectedIndex(0);
        txtId.requestFocus(); // Focar no primeiro campo
    }

}
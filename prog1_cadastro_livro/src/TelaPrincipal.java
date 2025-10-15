import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        // Configurações da janela principal
        setTitle("Sistema de Cadastro - Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centralizar na tela

        // Criar e configurar o menu
        criarMenu();

        // Criar conteúdo principal
        criarConteudoPrincipal();

        // Tornar a janela visível
        setVisible(true);
    }

    private void criarMenu() {
        // Criar a barra de menu
        JMenuBar menuBar = new JMenuBar();

        // Criar o menu "Cadastros"
        JMenu menuCadastros = new JMenu("Cadastros");

        // Criar submenu "Autor"
        JMenuItem menuItemAutor = new JMenuItem("Autor");
        menuItemAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioAutor();
            }
        });

        // Criar submenu "Livro"
        JMenuItem menuItemLivro = new JMenuItem("Livro");
        menuItemLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioLivro();
            }
        });

        // Adicionar itens ao menu Cadastros
        menuCadastros.add(menuItemAutor);
        menuCadastros.add(menuItemLivro);

        // Criar menu "Ajuda"
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem menuItemSobre = new JMenuItem("Sobre");
        menuItemSobre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSobre();
            }
        });
        menuAjuda.add(menuItemSobre);

        // Adicionar menus à barra de menu
        menuBar.add(menuCadastros);
        menuBar.add(menuAjuda);

        // Definir a barra de menu para a janela
        setJMenuBar(menuBar);
    }

    private void criarConteudoPrincipal() {
        // Criar painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Criar painel de boas-vindas
        JPanel painelBoasVindas = new JPanel(new GridBagLayout());
        painelBoasVindas.setBackground(new Color(240, 248, 255)); // Azul claro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Título principal
        JLabel lblTitulo = new JLabel("Sistema de Cadastro de Biblioteca");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(25, 25, 112)); // Azul escuro
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelBoasVindas.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Bem-vindo ao sistema de gerenciamento");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(70, 70, 70));
        gbc.gridy = 1;
        painelBoasVindas.add(lblSubtitulo, gbc);

        // Instruções
        JLabel lblInstrucoes = new JLabel(
                "<html><center>Use o menu 'Cadastros' para acessar:<br>• Cadastro de Autores<br>• Cadastro de Livros</center></html>");
        lblInstrucoes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstrucoes.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 20, 20, 20);
        painelBoasVindas.add(lblInstrucoes, gbc);

        // Painel de botões de acesso rápido
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        painelBotoes.setBackground(new Color(240, 248, 255));

        // Botão para Cadastro de Autor
        JButton btnCadastroAutor = new JButton("Cadastrar Autor");
        btnCadastroAutor.setPreferredSize(new Dimension(150, 40));
        btnCadastroAutor.setFont(new Font("Arial", Font.BOLD, 12));
        btnCadastroAutor.setBackground(new Color(70, 130, 180));
        btnCadastroAutor.setForeground(Color.WHITE);
        btnCadastroAutor.setFocusPainted(false);
        btnCadastroAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioAutor();
            }
        });

        // Botão para Cadastro de Livro
        JButton btnCadastroLivro = new JButton("Cadastrar Livro");
        btnCadastroLivro.setPreferredSize(new Dimension(150, 40));
        btnCadastroLivro.setFont(new Font("Arial", Font.BOLD, 12));
        btnCadastroLivro.setBackground(new Color(60, 179, 113));
        btnCadastroLivro.setForeground(Color.WHITE);
        btnCadastroLivro.setFocusPainted(false);
        btnCadastroLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioLivro();
            }
        });

        painelBotoes.add(btnCadastroAutor);
        painelBotoes.add(btnCadastroLivro);

        gbc.gridy = 3;
        gbc.insets = new Insets(40, 20, 20, 20);
        painelBoasVindas.add(painelBotoes, gbc);

        // Adicionar ao painel principal
        painelPrincipal.add(painelBoasVindas, BorderLayout.CENTER);

        // Barra de status
        JPanel painelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelStatus.setBackground(new Color(220, 220, 220));
        painelStatus.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblStatus = new JLabel("Sistema pronto para uso");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));
        painelStatus.add(lblStatus);

        painelPrincipal.add(painelStatus, BorderLayout.SOUTH);

        // Adicionar painel principal à janela
        add(painelPrincipal);
    }

    private void abrirFormularioAutor() {
        // Criar nova instância do formulário de autor
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormCadastroAutor();
            }
        });
    }

    private void abrirFormularioLivro() {
        // Criar nova instância do formulário de livro
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormCadastroLivro();
            }
        });
    }

    private void mostrarSobre() {
        String mensagem = "Sistema de Cadastro de Biblioteca\n" +
                "Versão 1.0\n\n" +
                "Desenvolvido para gerenciar cadastros de:\n" +
                "• Autores\n" +
                "• Livros\n\n" +
                "© 2025 - Sistema de Biblioteca";
        JOptionPane.showMessageDialog(this, mensagem, "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

}
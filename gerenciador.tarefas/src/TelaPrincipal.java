import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Cadastro de Tarefas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        criarMenu();
        criarConteudoPrincipal();

        setVisible(true);
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastros = new JMenu("Cadastros");

        JMenuItem menuItemResponsavel = new JMenuItem("Responsável");
        menuItemResponsavel.addActionListener(e -> abrirFormularioResponsavel());

        JMenuItem menuItemPrioridade = new JMenuItem("Prioridade");
        menuItemPrioridade.addActionListener(e -> abrirFormularioPrioridade());

        JMenuItem menuItemTarefa = new JMenuItem("Tarefa");
        menuItemTarefa.addActionListener(e -> abrirFormularioTarefas());

        menuCadastros.add(menuItemResponsavel);
        menuCadastros.add(menuItemPrioridade);
        menuCadastros.add(menuItemTarefa);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem menuItemSobre = new JMenuItem("Sobre");
        menuItemSobre.addActionListener(e -> mostrarSobre());
        menuAjuda.add(menuItemSobre);

        menuBar.add(menuCadastros);
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);
    }

    private void criarConteudoPrincipal() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBackground(new Color(245, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel lblTitulo = new JLabel("Sistema de Cadastro de Tarefas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentro.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Gerencie suas tarefas de forma simples e organizada");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(80, 80, 80));
        gbc.gridy = 1;
        painelCentro.add(lblSubtitulo, gbc);

        JLabel lblInstrucoes = new JLabel(
                "<html><center>Use os botões abaixo para acessar os cadastros<br>e registrar novas tarefas.</center></html>");
        lblInstrucoes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstrucoes.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        painelCentro.add(lblInstrucoes, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        painelBotoes.setBackground(new Color(245, 250, 255));

        JButton btnTarefa = criarBotao("Cadastrar Tarefa", new Color(60, 179, 113), e -> abrirFormularioTarefas());
        JButton btnResponsavel = criarBotao("Cadastrar Responsável", new Color(70, 130, 180), e -> abrirFormularioResponsavel());
        JButton btnPrioridade = criarBotao("Cadastrar Prioridade", new Color(72, 209, 204), e -> abrirFormularioPrioridade());

        painelBotoes.add(btnTarefa);
        painelBotoes.add(btnResponsavel);
        painelBotoes.add(btnPrioridade);

        gbc.gridy = 3;
        painelCentro.add(painelBotoes, gbc);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        // Barra de status
        JPanel painelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelStatus.setBackground(new Color(230, 230, 230));
        painelStatus.setBorder(BorderFactory.createEtchedBorder());
        JLabel lblStatus = new JLabel("Sistema pronto para uso");
        painelStatus.add(lblStatus);

        painelPrincipal.add(painelStatus, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JButton criarBotao(String texto, Color corFundo, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(180, 45));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.addActionListener(acao);
        return botao;
    }

    private void abrirFormularioResponsavel() {
        SwingUtilities.invokeLater(() -> new FormResponsavel());
    }

    private void abrirFormularioPrioridade() {
        SwingUtilities.invokeLater(() -> new FormPrioridade());
    }

    private void abrirFormularioTarefas() {
        SwingUtilities.invokeLater(() -> new FormTarefas());
    }

    private void mostrarSobre() {
        String mensagem = """
                Sistema de Cadastro de Tarefas
                Versão 1.0

                Desenvolvido para registrar:
                • Responsáveis
                • Prioridades
                • Tarefas

                © 2025 - Sistema de Tarefas
                """;
        JOptionPane.showMessageDialog(this, mensagem, "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}

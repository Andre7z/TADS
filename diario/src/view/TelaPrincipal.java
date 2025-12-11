package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;

import dao.*;
import controller.*;

public class TelaPrincipal extends JFrame {

    private Connection conn;

    public TelaPrincipal(Connection conn) {
        this.conn = conn;

        setTitle("Sistema Escolar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        criarMenu();
        criarConteudoPrincipal();

        setVisible(true);
    }

    /* ===================== MENU SUPERIOR ===================== */

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuLancamentos = new JMenu("Lançamentos");
        JMenu menuAjuda = new JMenu("Ajuda");

        JMenuItem miAluno = new JMenuItem("Aluno");
        miAluno.addActionListener(_ -> abrirAluno());

        JMenuItem miProfessor = new JMenuItem("Professor");
        miProfessor.addActionListener(_ -> abrirProfessor());

        JMenuItem miDisciplina = new JMenuItem("Disciplina");
        miDisciplina.addActionListener(_ -> abrirDisciplina());

        JMenuItem miPeriodo = new JMenuItem("Período");
        miPeriodo.addActionListener(_ -> abrirPeriodo());

        JMenuItem miTurma = new JMenuItem("Turma");
        miTurma.addActionListener(_ -> abrirTurma());

        JMenuItem miDiario = new JMenuItem("Diário");
        miDiario.addActionListener(_ -> abrirDiario());

        JMenuItem miSobre = new JMenuItem("Sobre");
        miSobre.addActionListener(_ -> mostrarSobre());

        menuCadastros.add(miAluno);
        menuCadastros.add(miProfessor);
        menuCadastros.add(miDisciplina);
        menuCadastros.add(miPeriodo);
        menuCadastros.add(miTurma);

        menuLancamentos.add(miDiario);

        menuAjuda.add(miSobre);

        menuBar.add(menuCadastros);
        menuBar.add(menuLancamentos);
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);
    }

    /*
     * ===================== CONTEÚDO CENTRAL (3 x 2 BOTÕES) =====================
     */

    private void criarConteudoPrincipal() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBackground(new Color(245, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel lblTitulo = new JLabel("Sistema Escolar");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentro.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Gerencie alunos, professores, turmas, diários e notas");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(80, 80, 80));
        gbc.gridy = 1;
        painelCentro.add(lblSubtitulo, gbc);

        JLabel lblInstrucoes = new JLabel(
                "<html><center>Use os botões abaixo para acessar os cadastros<br>" +
                        "e lançar diários e notas.</center></html>");
        lblInstrucoes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInstrucoes.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        painelCentro.add(lblInstrucoes, gbc);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 20, 20));
        painelBotoes.setBackground(new Color(245, 250, 255));
        painelBotoes.setPreferredSize(new Dimension(550, 100));

        JButton btnAluno = criarBotao("Cadastrar Aluno",
                new Color(70, 130, 180), _ -> abrirAluno());
        JButton btnProfessor = criarBotao("Cadastrar Professor",
                new Color(60, 179, 113), _ -> abrirProfessor());
        JButton btnDisciplina = criarBotao("Cadastrar Disciplina",
                new Color(72, 209, 204), _ -> abrirDisciplina());
        JButton btnPeriodo = criarBotao("Cadastrar Período",
                new Color(218, 165, 32), _ -> abrirPeriodo());
        JButton btnTurma = criarBotao("Cadastrar Turma",
                new Color(199, 21, 133), _ -> abrirTurma());
        JButton btnDiario = criarBotao("Diário / Notas",
                new Color(123, 104, 238), _ -> abrirDiario());

        painelBotoes.add(btnAluno);
        painelBotoes.add(btnProfessor);
        painelBotoes.add(btnDisciplina);
        painelBotoes.add(btnPeriodo);
        painelBotoes.add(btnTurma);
        painelBotoes.add(btnDiario);

        gbc.gridy = 3;
        painelCentro.add(painelBotoes, gbc);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

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
        botao.setPreferredSize(new Dimension(200, 50));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.addActionListener(acao);
        return botao;
    }

    // ===================== ABRIR TELAS ===================== 

    private void abrirAluno() {
        SwingUtilities.invokeLater(() -> {
            PessoaDAO pessoaDAO = new PessoaDAO(conn);
            AlunoDAO alunoDAO = new AlunoDAO();
            AlunoController controller = new AlunoController(alunoDAO, pessoaDAO);
            new TelaAluno(controller).setVisible(true);
        });
    }

    private void abrirProfessor() {
        SwingUtilities.invokeLater(() -> {
            PessoaDAO pessoaDAO = new PessoaDAO(conn);
            ProfessorDAO profDAO = new ProfessorDAO(conn);
            ProfessorController controller = new ProfessorController(profDAO, pessoaDAO);
            new TelaProfessor(controller).setVisible(true);
        });
    }

    private void abrirDisciplina() {
        SwingUtilities.invokeLater(() -> {
            DisciplinaDAO dDAO = new DisciplinaDAO(conn);
            DisciplinaController controller = new DisciplinaController(dDAO);
            new TelaDisciplina(controller).setVisible(true);
        });
    }

    private void abrirPeriodo() {
        SwingUtilities.invokeLater(() -> {
            PeriodoDAO pDAO = new PeriodoDAO(conn);
            PeriodoController controller = new PeriodoController(pDAO);
            new TelaPeriodo(controller).setVisible(true);
        });
    }

    private void abrirTurma() {
        SwingUtilities.invokeLater(() -> {
            TurmaDAO tDAO = new TurmaDAO(conn);
            TurmaController controller = new TurmaController(tDAO);
            new TelaTurma(controller).setVisible(true);
        });
    }

    // Usa TelaDiario(DiarioController, NotaController, Connection)
    private void abrirDiario() {
        SwingUtilities.invokeLater(() -> {
            DiarioDAO dDAO = new DiarioDAO(conn);
            NotaDAO nDAO = new NotaDAO(conn);
            DiarioController dCtrl = new DiarioController(dDAO, nDAO, conn);
            NotaController nCtrl = new NotaController(nDAO, dDAO);
            new TelaDiario(dCtrl, nCtrl, conn).setVisible(true);
        });
    }

    private void mostrarSobre() {
        String mensagem = """
                Sistema Escolar
                Versão 1.0

                Funcionalidades:
                • Cadastro de Alunos e Professores
                • Cadastro de Disciplinas, Períodos e Turmas
                • Diário de classe e lançamento de notas (média aritmética)

                © 2025 - Sistema Escolar
                """;
        JOptionPane.showMessageDialog(this, mensagem,
                "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conn = ConnectionFactory.getConnection();
            new TelaPrincipal(conn);
        });
    }
}

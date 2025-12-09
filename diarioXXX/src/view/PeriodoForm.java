package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.*;
import dao.Conexao;

public class PeriodoForm extends JFrame {
    private static final Logger logger = Logger.getLogger(PeriodoForm.class.getName());
    private JTextField txtId, txtNome;
    private JButton btnSalvar, btnAlterar, btnExcluir, btnPesquisar, btnLimpar, btnSair;

    public PeriodoForm() {
        setTitle("Cadastro de Período");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,250);
        setLocationRelativeTo(null);
        inicializar();
        btnSalvar.addActionListener(_-> salvar());
        btnAlterar.addActionListener(_-> alterar());
        btnExcluir.addActionListener(_-> excluir());
        btnPesquisar.addActionListener(_-> pesquisar());
        btnLimpar.addActionListener(_-> limpar());
        btnSair.addActionListener(_-> dispose());
        setVisible(true);
    }

    private void inicializar() {
        JPanel p=new JPanel(new GridBagLayout()); GridBagConstraints gbc=new GridBagConstraints(); gbc.insets=new Insets(5,5,5,5); gbc.anchor=GridBagConstraints.WEST;
        int linha=0;
        gbc.gridx=0; gbc.gridy=linha; p.add(new JLabel("ID:"),gbc); gbc.gridx=1; txtId=new JTextField(20); p.add(txtId,gbc);
        gbc.gridx=0; gbc.gridy=++linha; p.add(new JLabel("Descrição:"),gbc); gbc.gridx=1; txtNome=new JTextField(20); p.add(txtNome,gbc);
        JPanel botoes=new JPanel(new FlowLayout()); btnSalvar=new JButton("Salvar"); btnAlterar=new JButton("Alterar"); btnExcluir=new JButton("Excluir");
        btnPesquisar=new JButton("Pesquisar"); btnLimpar=new JButton("Limpar"); btnSair=new JButton("Sair");
        botoes.add(btnSalvar); botoes.add(btnAlterar); botoes.add(btnExcluir); botoes.add(btnPesquisar); botoes.add(btnLimpar); botoes.add(btnSair);
        gbc.gridx=0; gbc.gridy=++linha; gbc.gridwidth=2; p.add(botoes,gbc); add(p);
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()){ JOptionPane.showMessageDialog(this,"Descrição obrigatório"); return;}
        String sql="INSERT INTO periodo(nome) VALUES(?)";
        try (Connection c=Conexao.getConnection(); PreparedStatement ps=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, txtNome.getText().trim()); ps.executeUpdate(); ResultSet rs=ps.getGeneratedKeys(); if (rs.next()) txtId.setText(String.valueOf(rs.getInt(1)));
            logger.info("Período salvo"); JOptionPane.showMessageDialog(this,"Salvo"); limpar();
        } catch (SQLException e){ logger.log(Level.SEVERE,"Erro salvar período",e); JOptionPane.showMessageDialog(this,"Erro: "+e.getMessage()); }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()){ JOptionPane.showMessageDialog(this,"Informe ID"); return;}
        String sql="UPDATE periodo SET nome=? WHERE id=?";
        try (Connection c=Conexao.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1, txtNome.getText().trim()); ps.setInt(2,Integer.parseInt(txtId.getText().trim())); ps.executeUpdate(); logger.info("Período alterado"); JOptionPane.showMessageDialog(this,"Alterado");
        } catch (SQLException e){ logger.log(Level.SEVERE,"Erro alterar período",e); JOptionPane.showMessageDialog(this,"Erro: "+e.getMessage()); }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()){ JOptionPane.showMessageDialog(this,"Informe ID"); return;}
        String sql="DELETE FROM periodo WHERE id=?";
        try (Connection c=Conexao.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) { ps.setInt(1,Integer.parseInt(txtId.getText().trim())); ps.executeUpdate(); logger.info("Período excluído"); JOptionPane.showMessageDialog(this,"Excluído"); limpar(); } catch (SQLException e){ logger.log(Level.SEVERE,"Erro excluir período",e); JOptionPane.showMessageDialog(this,"Erro: "+e.getMessage()); }
    }

    private void pesquisar() {
        if (txtId.getText().trim().isEmpty()){ JOptionPane.showMessageDialog(this,"Informe ID"); return;}
        String sql="SELECT * FROM periodo WHERE id=?";
        try (Connection c=Conexao.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1,Integer.parseInt(txtId.getText().trim())); ResultSet rs=ps.executeQuery(); if (rs.next()){ txtNome.setText(rs.getString("nome")); logger.info("Período carregado"); } else JOptionPane.showMessageDialog(this,"Não encontrado");
        } catch (SQLException e){ logger.log(Level.SEVERE,"Erro pesquisar período",e); JOptionPane.showMessageDialog(this,"Erro: "+e.getMessage()); }
    }

    private void limpar() { txtId.setText(""); txtNome.setText(""); }
    public static void main(String[] args){ SwingUtilities.invokeLater(PeriodoForm::new); }
}

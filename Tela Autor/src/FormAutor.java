import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class FormAutor extends JFrame {
    private JTextField txtID;
    private JTextField txtNome;
    private JTextField txtCidade;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;

    public FormAutor() {
        setTitle("Cadastro de Autor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtID = new JTextField(15);
        painelPrincipal.add(txtID, gbc);

        // nome
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

        // cidade
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Cidade:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCidade = new JTextField(15);
        painelPrincipal.add(txtCidade, gbc);

        // botoes
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarAutor();
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
                System.exit(0);
            }
        });

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);

        add(painelPrincipal);
    }

    private void salvarAutor() {
        if (txtID.getText().trim().isEmpty() ||
                txtNome.getText().trim().isEmpty() ||
                txtCidade.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter("autores.txt", true)) {
            writer.write(txtID.getText() + ";" + txtNome.getText() + ";" + txtCidade.getText() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String mensagem = String.format(
                "Cliente salvo com sucesso!\n\n" +
                        "ID: %s\n" +
                        "Nome: %s\n" +
                        "Cidade: %s\n",
                txtID.getText(),
                txtNome.getText(),
                txtCidade.getText());

        JOptionPane.showMessageDialog(this,
                mensagem,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        txtID.setText("");
        txtNome.setText("");
        txtCidade.setText("");
        txtID.requestFocus();
    }

    public static void main(String[] args) {
        // Executar na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormAutor();
            }
        });
    }

}
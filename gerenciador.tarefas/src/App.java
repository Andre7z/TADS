import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {
        // Criar e exibir a tela
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaPrincipal();
            }
        });
    }
}
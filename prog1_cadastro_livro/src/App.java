import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {
        // Criar e exibir a tela
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaPrincipal();// FormCadastroLivro();// FormCadastroAutor();
            }
        });
        // Autor maria = new Autor(1, "Maria José", "Goiania");
        // Autor jose = new Autor(1, "José Maria", "Jataí");

        // maria.salvar();

        // TipoCapa capaDura = new TipoCapa(1, "Dura");
        // TipoCapa capaFlexivel = new TipoCapa(2, "Flexível");

        // Editora editoraA = new Editora();
        // Editora editoraB = new Editora();

        // editoraA.salvar();

        // Livro livro = new Livro();
        // livro.setId(1);
        // livro.setNome("Java Básico");
        // livro.setResenha("Um livro para iniciantes em Java");
        // livro.setEdicao(1);
        // livro.setAnoPublicacao(2023);
        // livro.setNumeroPaginas(300);
        // livro.setAutor(maria);
        // livro.setEditora(editoraA);
        // livro.setTipoCapa(capaDura);

        // livro.salvar();

    }
}

public class App {
    public static void main(String[] args) throws Exception {
        
        Editora editora1 = new Editora(01, "Cultrix");
        Autor autor1 = new Autor (01, "Jigoro Kano", "Japão");
        TipoCapa tipoCapa1 = new TipoCapa(01, "Normal");
        Livro livro1 = new Livro(01, "Judô Kodokan", "O livro sobre judô", 7,  2022, 271, autor1, editora1, tipoCapa1);
        
        Editora editora2 = new Editora(02, "Hunter Books");
        Autor autor2 = new Autor (02, "Maquiavel", "Itália");
        TipoCapa tipoCapa2 = new TipoCapa(02, "Dura");
        Livro livro2 = new Livro(02, "O Príncipe", "Um tratado sobre política", 5,  2021, 128, autor2, editora2, tipoCapa2);



        
        livro1.salvar();
        livro1.alterar();
        livro1.pesquisar();
        livro1.excluir();

        
        editora1.salvar();
        editora1.alterar();
        editora1.pesquisar();
        editora1.excluir();

        
        autor1.salvar();
        autor1.alterar();
        autor1.pesquisar();
        autor1.excluir();

        livro1.imprimir();
        livro2.imprimir();
    }



}
  
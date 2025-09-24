public class Livro {
    private int id;
    private String nome;
    private String resenha;
    private int edicao;
    private int anoPublicacao;
    private int nPaginas;
    private Autor autor;
    private Editora editora;
    private TipoCapa tipoCapa;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getResenha() {
        return resenha;
    }
    public void setResenha(String resenha) {
        this.resenha = resenha;
    }
    public int getEdicao() {
        return edicao;
    }
    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public int getnPaginas() {
        return nPaginas;
    }
    public void setnPaginas(int nPaginas) {
        this.nPaginas = nPaginas;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public Editora getEditora() {
        return editora;
    }
    public void setEditora(Editora editora) {
        this.editora = editora;
    }
    public TipoCapa getTipoCapa() {
        return tipoCapa;
    }
    public void setTipoCapa(TipoCapa tipoCapa) {
        this.tipoCapa = tipoCapa;
    }

    public Livro() {
    }

    public Livro(int id, String nome, String resenha, int edicao, int anoPublicacao, int nPaginas, Autor autor, Editora editora, TipoCapa tipoCapa) {
        this.id = id;
        this.nome = nome;
        this.resenha = resenha;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.nPaginas = nPaginas;
        this.autor = autor;
        this.editora = editora;
        this.tipoCapa = tipoCapa;
    }

        public Boolean salvar(){
        System.out.println("Salvo com sucesso");
        return true;
    }
    public Boolean alterar(){
        System.out.println("Alterado com sucesso");
        return true;
    }

    public Boolean excluir(){
        System.out.println("Excluido com sucesso");
        return true;
    }

    public Boolean pesquisar(){
        System.out.println("Pesquisado com sucesso");
        return true;
    }

    public void imprimir(){
        System.out.println("-----------:");
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Resenha: " + this.resenha);
        System.out.println("Edição: " + this.edicao);
        System.out.println("Ano de Publicação: " + this.anoPublicacao);
        System.out.println("Número de Páginas: " + this.nPaginas);
        System.out.println("Autor: " + this.autor.getNome() + " - " + this.autor.getCidade());
        System.out.println("Editora: " + this.editora.getNome());
        System.out.println("Tipo de Capa: " + this.tipoCapa.getDescricao());
        System.out.println("-----------");
    }

}

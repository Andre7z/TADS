public class Livro {
    private int id;
    private String nome;
    private String resenha;
    private int edicao;
    private int anoPublicacao;
    private int numeroPaginas;
    private Autor autor;
    private Editora editora;
    private TipoCapa tipoCapa;

    /**
     * 
     */
    public Livro() {
    }

    /**
     * @param id
     * @param nome
     * @param resenha
     * @param edicao
     * @param anoPublicacao
     * @param numeroPaginas
     * @param autor
     * @param editora
     * @param tipoCapa
     */
    public Livro(int id, String nome, String resenha, int edicao, int anoPublicacao, int numeroPaginas, Autor autor,
            Editora editora, TipoCapa tipoCapa) {
        this.id = id;
        this.nome = nome;
        this.resenha = resenha;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.numeroPaginas = numeroPaginas;
        this.autor = autor;
        this.editora = editora;
        this.tipoCapa = tipoCapa;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the resenha
     */
    public String getResenha() {
        return resenha;
    }

    /**
     * @param resenha the resenha to set
     */
    public void setResenha(String resenha) {
        this.resenha = resenha;
    }

    /**
     * @return the edicao
     */
    public int getEdicao() {
        return edicao;
    }

    /**
     * @param edicao the edicao to set
     */
    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    /**
     * @return the anoPublicacao
     */
    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    /**
     * @param anoPublicacao the anoPublicacao to set
     */
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    /**
     * @return the numeroPaginas
     */
    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    /**
     * @param numeroPaginas the numeroPaginas to set
     */
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    /**
     * @return the autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * @return the editora
     */
    public Editora getEditora() {
        return editora;
    }

    /**
     * @param editora the editora to set
     */
    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    /**
     * @return the tipoCapa
     */
    public TipoCapa getTipoCapa() {
        return tipoCapa;
    }

    /**
     * @param tipoCapa the tipoCapa to set
     */
    public void setTipoCapa(TipoCapa tipoCapa) {
        this.tipoCapa = tipoCapa;
    }

    public void salvar() {
        System.out.println("----- Dados do livro salvo -----");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Resenha: " + getResenha());
        System.out.println("Edição: " + getEdicao());
        System.out.println("Ano de Publicação: " + getAnoPublicacao());
        System.out.println("Número de Páginas: " + getNumeroPaginas());
        System.out.println("Autor: " + getAutor().getNome());
        System.out.println("Editora: " + getEditora().getNome());
        System.out.println("Tipo de Capa: " + getTipoCapa().getNome());
        System.out.println("------- Livro salvo com sucesso! -------");
    }

    public void excluir() {
        System.out.println("Livro excluído com sucesso!");
    }

    public void atualizar() {
        System.out.println("Livro atualizado com sucesso!");
    }

    public void buscar() {
        System.out.println("Livro buscado com sucesso!");
    }

}

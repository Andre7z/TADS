public class Garagem extends Generica {
    private String nome;
    private String cidade;
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Garagem() {
    }

    public Garagem(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }
    
    
    @Override
    public void salvar() {
        System.out.println("Garagem salva com sucesso 2");
        super.salvar();
    }
    
    @Override
    public void excluir() {
        System.out.println("Garagem excluída com sucesso 2");
        super.excluir();
    }
    
    @Override
    public void alterar() {
        System.out.println("Garagem alterada com sucesso 2");
        super.alterar();
    }
    
    @Override
    public void pesquisar() {
        System.out.println("Garagem pesquisada com sucesso 2");
        super.pesquisar();
    }
    
}
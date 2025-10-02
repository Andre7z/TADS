public class Categoria extends Generica {
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria() {
    }
    public Categoria(String descricao) {
        this.descricao = descricao;
    }

    public void salvar() {
        System.out.println("Categoria salva com sucesso2");
    }
}

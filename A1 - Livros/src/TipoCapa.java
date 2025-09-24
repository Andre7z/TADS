public class TipoCapa {
    private int id;
    private String descricao;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoCapa(){ }

    public TipoCapa(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
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
    
}

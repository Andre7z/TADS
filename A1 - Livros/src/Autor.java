public class Autor {
    private int id;
    private String nome;
    private String cidade;

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
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Autor() {
    }

    public Autor(int id, String nome, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
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

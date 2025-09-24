public class Editora {
    private int id;
    private String nome;

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

    public Editora() {
    }
    public Editora(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

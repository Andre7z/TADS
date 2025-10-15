public class TipoCapa {
    private int id;
    private String nome;

    /**
     * 
     */
    public TipoCapa() {
    }

    /**
     * @param id
     * @param nome
     */
    public TipoCapa(int id, String nome) {
        this.id = id;
        this.nome = nome;
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
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void salvar() {
        System.out.println("TipoCapa salvo com sucesso!");
    }

    public void excluir() {
        System.out.println("TipoCapa excluído com sucesso!");
    }

    public void atualizar() {
        System.out.println("TipoCapa atualizado com sucesso!");
    }

    public void buscar() {
        System.out.println("TipoCapa buscado com sucesso!");
    }

}

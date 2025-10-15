public class ClasseGenerica {
    private int id;
    private String nome;

    public ClasseGenerica() {
    }

    public ClasseGenerica(int id, String nome) {
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
        System.out.println("Registro salvo com sucesso na XML!");
    }

}

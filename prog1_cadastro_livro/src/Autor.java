public class Autor extends ClasseGenerica {
    private String cidade;

    public Autor() {
    }

    public Autor(int id, String nome, String cidade) {
        super(id, nome);
        this.cidade = cidade;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public void salvar() {
        System.out.println("Autor salvo com sucesso");
    }

}

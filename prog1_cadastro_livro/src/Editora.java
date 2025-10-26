public class Editora extends ClasseGenerica {
    private String cidade;

    public Editora() {
    }

    public Editora(int id, String nome, String cidade) {
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
        System.out.println("Editora salva com sucesso");
    }

}

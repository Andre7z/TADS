public class Veiculo {
    private String nome;
    private int ano;
    private int modelo;
    private String cor;
    private String placa;
    private boolean unicoDono;
    private Categoria categoria;
    private Garagem garagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isUnicoDono() {
        return unicoDono;
    }

    public void setUnicoDono(boolean unicoDono) {
        this.unicoDono = unicoDono;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Garagem getGaragem() {
        return garagem;
    }

    public void setGaragem(Garagem garagem) {
        this.garagem = garagem;
    }

    public Veiculo() {
    }

    public Veiculo(String nome, int ano, int modelo, String cor, String placa, boolean unicoDono, Categoria categoria, Garagem garagem) {
        this.nome = nome;   
        this.ano = ano;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.unicoDono = unicoDono;
        this.categoria = categoria;
        this.garagem = garagem;
    }

        public void salvar() {
        System.out.println("Veículo salvo com sucesso");
    }
    public void excluir() {
        System.out.println("Veículo excluído com sucesso");
    }
    public void alterar() {
        System.out.println("Veículo alterado com sucesso");
    }
    public void pesquisar() {
        System.out.println("Veículo pesquisado com sucesso");
    }
}

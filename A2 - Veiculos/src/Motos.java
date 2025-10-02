public class Motos extends Veiculo {
    private int cilindrada;
    private Veiculo veiculo;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public Motos() {
    }

    public Motos(int cilindrada, Veiculo veiculo) {
        this.cilindrada = cilindrada;
        this.veiculo = veiculo;
    }

    @Override
    public void salvar() {
        System.out.println("Moto salva com sucesso2");
        super.salvar();
    }
}

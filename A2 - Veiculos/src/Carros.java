public class Carros extends Veiculo {
    private int motorizacaoCv;
    private int qtdeMarcha;
    private String opcionais;
    private Veiculo veiculo;

    public int getMotorizacaoCv() {
        return motorizacaoCv;
    }

    public void setMotorizacaoCv(int motorizacaoCv) {
        this.motorizacaoCv = motorizacaoCv;
    }

    public int getQtdeMarcha() {
        return qtdeMarcha;
    }

    public void setQtdeMarcha(int qtdeMarcha) {
        this.qtdeMarcha = qtdeMarcha;
    }

    public String getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(String opcionais) {
        this.opcionais = opcionais;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Carros() {
    }

    public Carros(int motorizacaoCv, int qtdeMarcha, String opcionais, Veiculo veiculo) {
        this.motorizacaoCv = motorizacaoCv;
        this.qtdeMarcha = qtdeMarcha;
        this.opcionais = opcionais;
        this.veiculo = veiculo;
    }

    @Override
    public void salvar() {
        System.out.println("Carro salvo com sucesso 2");
        super.salvar();
    }

}

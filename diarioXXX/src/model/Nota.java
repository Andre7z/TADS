package model;

public class Nota {

    private int id;
    private double valor;
    private int diarioId; // FK

    public Nota() {}

    public Nota(int id, double valor, int diarioId) {
        this.id = id;
        this.valor = valor;
        this.diarioId = diarioId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public int getDiarioId() { return diarioId; }
    public void setDiarioId(int diarioId) { this.diarioId = diarioId; }

}

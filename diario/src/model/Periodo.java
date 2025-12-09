package model;

public class Periodo {
    private int id;
    private String nomePeriodo;

    public Periodo() {}

    public Periodo(int id, String nomePeriodo) {
        this.id = id;
        this.nomePeriodo = nomePeriodo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomePeriodo() { return nomePeriodo; }
    public void setNomePeriodo(String nomePeriodo) { this.nomePeriodo = nomePeriodo; }
}

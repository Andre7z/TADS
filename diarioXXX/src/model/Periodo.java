package model;

public class Periodo {
    private int id;
    private String nome_periodo;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome_periodo() {
        return nome_periodo;
    }
    public void setNome_periodo(String nome_periodo) {
        this.nome_periodo = nome_periodo;
    }

    public Periodo (){}
    public Periodo(int id, String nome_periodo) {
        this.id = id;
        this.nome_periodo = nome_periodo;
    }
    

}

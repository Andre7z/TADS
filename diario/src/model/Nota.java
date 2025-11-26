package model;

public class Nota {

    private int id;
    private Double nota;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
        this.nota = nota;
    }
    public Nota(){}
    public Nota(int id, Double nota) {
        this.id = id;
        this.nota = nota;
    }
    
}

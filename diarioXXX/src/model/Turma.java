package model;

public class Turma {

    private int id;
    private String nome_turma;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome_turma() {
        return nome_turma;
    }
    public void setNome_turma(String nome_turma) {
        this.nome_turma = nome_turma;
    }
    
    public Turma(){}
    public Turma(int id, String nome_turma) {
        this.id = id;
        this.nome_turma = nome_turma;
    }

}

package model;

public class Disciplina {
    private int id;
    private String nomeDisciplina;
    private int idProfessor;
    

    public Disciplina() {}
    public Disciplina(int id, String nomeDisciplina, int idProfessor) {
        this.id = id;
        this.nomeDisciplina = nomeDisciplina;
        this.idProfessor = idProfessor;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNomeDisciplina() {
        return nomeDisciplina;
    }
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }
    public int getIdProfessor() {
        return idProfessor;
    }
    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

}
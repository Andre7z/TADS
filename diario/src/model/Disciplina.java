package model;

public class Disciplina {

    private int id;
    private String nomeDisciplina;
    private Professor professor;  

    public Disciplina() {}

    public Disciplina(int id, String nomeDisciplina, Professor professor) {
        this.id = id;
        this.nomeDisciplina = nomeDisciplina;
        this.professor = professor;
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

    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}

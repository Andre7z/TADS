package model;

public class Turma {
    private int id;
    private String nomeTurma;
    private int idDisciplina;
    private int idProfessor; // id_pessoa do professor
    private int idPeriodo;

    public Turma() {
    }

    public Turma(int id, String nomeTurma, int idDisciplina, int idProfessor, int idPeriodo) {
        this.id = id;
        this.nomeTurma = nomeTurma;
        this.idDisciplina = idDisciplina;
        this.idProfessor = idProfessor;
        this.idPeriodo = idPeriodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
}

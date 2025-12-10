package model;

public class Diario {
    private int id;
    private int idDisciplina;
    private int idPeriodo;
    private int idTurma;
    private int idAluno;
    private boolean status;

    public Diario() {
    }

    public Diario(int id, int idDisciplina, int idPeriodo,
            int idTurma, int idAluno, boolean status) {
        this.id = id;
        this.idDisciplina = idDisciplina;
        this.idPeriodo = idPeriodo;
        this.idTurma = idTurma;
        this.idAluno = idAluno;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

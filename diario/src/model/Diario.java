package model;

public class Diario {

    private int id;
    private Disciplina disciplina;
    private Periodo periodo;
    private Turma turma;
    private Aluno aluno;
    private boolean status;

    public Diario() {
    }

    public Diario(int id,
                  Disciplina disciplina,
                  Periodo periodo,
                  Turma turma,
                  Aluno aluno,
                  boolean status) {
        this.id = id;
        this.disciplina = disciplina;
        this.periodo = periodo;
        this.turma = turma;
        this.aluno = aluno;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Periodo getPeriodo() {
        return periodo;
    }
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Turma getTurma() {
        return turma;
    }
    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}

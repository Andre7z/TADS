package model;

import java.util.ArrayList;
import java.util.List;

public class Diario {

    private int id;
    private boolean status;
    private Aluno aluno;
    private Disciplina disciplina;
    private Periodo periodo;
    private Turma turma;

    private List<Nota> notas = new ArrayList<>();

    public Diario() {}

    public Diario(int id, boolean status, Aluno aluno, Disciplina disciplina, Periodo periodo, Turma turma) {
        this.id = id;
        this.status = status;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.periodo = periodo;
        this.turma = turma;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public Periodo getPeriodo() { return periodo; }
    public void setPeriodo(Periodo periodo) { this.periodo = periodo; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public List<Nota> getNotas() { return notas; }
    public void addNota(Nota n) { this.notas.add(n); }

    /** Calcula média aritmética */
    public double getMedia() {
        if (notas.isEmpty()) return 0;
        double soma = 0;
        for (Nota n : notas) soma += n.getValor();
        return soma / notas.size();
    }
}

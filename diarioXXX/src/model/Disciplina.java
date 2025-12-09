package model;

public class Disciplina {
    private int id;
    private String nome_disciplina;
    private Professor professor;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome_disciplina() {
        return nome_disciplina;
    }
    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    public Disciplina(){}
    
    public Disciplina(int id, String nome_disciplina, Professor professor) {
        this.id = id;
        this.nome_disciplina = nome_disciplina;
        this.professor = professor;
    }
    

}

package model;

public class Professor extends Pessoa {
    private String matricula;

    public Professor() {
    }

    public Professor(int id, String nome, String endereco, String telefone,
            String email, String matricula) {
        super(id, nome, endereco, telefone, email);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}

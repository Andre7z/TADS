package model;

public class Aluno extends Pessoa {

    private int matricula;
    private String nome_pai;
    private String nome_mae;
    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public String getNome_pai() {
        return nome_pai;
    }
    public void setNome_pai(String nome_pai) {
        this.nome_pai = nome_pai;
    }
    public String getNome_mae() {
        return nome_mae;
    }
    public void setNome_mae(String nome_mae) {
        this.nome_mae = nome_mae;
    }
    public Aluno(){}

    public Aluno(int id, String nome, String endereco, String telefone, String email, int matricula, String nome_pai,
            String nome_mae) {
        super(id, nome, endereco, telefone, email);
        this.matricula = matricula;
        this.nome_pai = nome_pai;
        this.nome_mae = nome_mae;
    }
    

}

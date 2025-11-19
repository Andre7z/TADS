package model;

import java.time.LocalDate;

public class ListaTarefas {
    private int id;
    private LocalDate dataTarefa;
    private String descricaoTarefa;
    private String observacao;
    private Prioridade prioridade;
    private Responsavel responsavel;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getDataTarefa() {
        return dataTarefa;
    }
    public void setDataTarefa(LocalDate dataTarefa) {
        this.dataTarefa = dataTarefa;
    }
    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }
    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public Prioridade getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
    public Responsavel getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

        public ListaTarefas(){}

    public ListaTarefas( int id, LocalDate dataTarefa, String descricaoTarefa, String observacao, Prioridade prioridade, Responsavel responsavel){
        this.id = id;
        this.dataTarefa = dataTarefa;
        this.descricaoTarefa = descricaoTarefa;
        this.observacao = observacao;
        this.prioridade = prioridade;
        this.responsavel = responsavel;
    }
    
}

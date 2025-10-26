import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class ListaTarefas {
    private int id;
    private LocalDate dataTarefa;
    private String descricaoTarefa;
    private String observacao;
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
    
    public ListaTarefas(){}

    public ListaTarefas( int id, LocalDate dataTarefa, String descricaoTarefa, String observacao){
        
    }
}

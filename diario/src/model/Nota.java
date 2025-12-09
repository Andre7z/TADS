package model;

import java.util.ArrayList;
import java.util.List;

public class Nota {
    private int idDiario;
    private List<Double> notas = new ArrayList<>();
    private double media;
    private boolean aprovado;

    public int getIdDiario() { return idDiario; }
    public void setIdDiario(int idDiario) { this.idDiario = idDiario; }

    public List<Double> getNotas() { return notas; }

    public double getMedia() { return media; }

    public boolean isAprovado() { return aprovado; }

    public void adicionarNota(double valor) {
        if (valor < 0 || valor > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        notas.add(valor);
        recalcularMedia();
    }

    private void recalcularMedia() {
        if (notas.isEmpty()) {
            media = 0;
            aprovado = false;
            return;
        }
        double soma = 0;
        for (double v : notas) soma += v;
        media = soma / notas.size();
        aprovado = media >= 6.0;
    }
}

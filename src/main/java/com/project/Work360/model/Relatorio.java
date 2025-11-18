package com.project.Work360.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.io.Serializable;


@Entity
@Table(name = "relatorios")
public class Relatorio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer tarefasConcluidas;
    private Integer tarefasPendentes;
    private Integer reunioesRealizadas;
    private Integer minutosFocoTotal;
    private Double percentualConclusao;
    private Double riscoBurnout;
    private String tendenciaProdutividade;
    private String tendenciaFoco;

    @Lob
    private String insights;

    @Lob
    private String recomendacaoIA;

    private String resumoGeral;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "relatorio_anterior_id")
    private Relatorio relatorioAnterior;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    public void setTarefasConcluidas(Integer tarefasConcluidas) {
        this.tarefasConcluidas = tarefasConcluidas;
    }

    public Integer getTarefasPendentes() {
        return tarefasPendentes;
    }

    public void setTarefasPendentes(Integer tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }

    public Integer getReunioesRealizadas() {
        return reunioesRealizadas;
    }

    public void setReunioesRealizadas(Integer reunioesRealizadas) {
        this.reunioesRealizadas = reunioesRealizadas;
    }

    public Integer getMinutosFocoTotal() {
        return minutosFocoTotal;
    }

    public void setMinutosFocoTotal(Integer minutosFocoTotal) {
        this.minutosFocoTotal = minutosFocoTotal;
    }

    public Double getPercentualConclusao() {
        return percentualConclusao;
    }

    public void setPercentualConclusao(Double percentualConclusao) {
        this.percentualConclusao = percentualConclusao;
    }

    public Double getRiscoBurnout() {
        return riscoBurnout;
    }

    public void setRiscoBurnout(Double riscoBurnout) {
        this.riscoBurnout = riscoBurnout;
    }

    public String getTendenciaProdutividade() {
        return tendenciaProdutividade;
    }

    public void setTendenciaProdutividade(String tendenciaProdutividade) {
        this.tendenciaProdutividade = tendenciaProdutividade;
    }

    public String getTendenciaFoco() {
        return tendenciaFoco;
    }

    public void setTendenciaFoco(String tendenciaFoco) {
        this.tendenciaFoco = tendenciaFoco;
    }

    public String getInsights() {
        return insights;
    }

    public void setInsights(String insights) {
        this.insights = insights;
    }

    public String getRecomendacaoIA() {
        return recomendacaoIA;
    }

    public void setRecomendacaoIA(String recomendacaoIA) {
        this.recomendacaoIA = recomendacaoIA;
    }

    public String getResumoGeral() {
        return resumoGeral;
    }

    public void setResumoGeral(String resumoGeral) {
        this.resumoGeral = resumoGeral;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Relatorio getRelatorioAnterior() {
        return relatorioAnterior;
    }

    public void setRelatorioAnterior(Relatorio relatorioAnterior) {
        this.relatorioAnterior = relatorioAnterior;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Relatorio{");
        sb.append("id=").append(id);
        sb.append(", usuario=").append(usuario);
        sb.append(", dataInicio=").append(dataInicio);
        sb.append(", dataFim=").append(dataFim);
        sb.append(", tarefasConcluidas=").append(tarefasConcluidas);
        sb.append(", tarefasPendentes=").append(tarefasPendentes);
        sb.append(", reunioesRealizadas=").append(reunioesRealizadas);
        sb.append(", minutosFocoTotal=").append(minutosFocoTotal);
        sb.append(", percentualConclusao=").append(percentualConclusao);
        sb.append(", riscoBurnout=").append(riscoBurnout);
        sb.append(", tendenciaProdutividade='").append(tendenciaProdutividade).append('\'');
        sb.append(", tendenciaFoco='").append(tendenciaFoco).append('\'');
        sb.append(", insights='").append(insights).append('\'');
        sb.append(", recomendacaoIA='").append(recomendacaoIA).append('\'');
        sb.append(", resumoGeral='").append(resumoGeral).append('\'');
        sb.append(", criadoEm=").append(criadoEm);
        sb.append(", relatorioAnterior=").append(relatorioAnterior);
        sb.append('}');
        return sb.toString();
    }
}

package com.project.Work360.model;
import java.time.LocalDate;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "analytics_metrica")
public class AnalyticsMetrica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDate data;
    private Integer minutosFoco;
    private Integer minutosReuniao;
    private Integer tarefasConcluidasNoDia;
    private String periodoMaisProdutivo;

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getMinutosFoco() {
        return minutosFoco;
    }

    public void setMinutosFoco(Integer minutosFoco) {
        this.minutosFoco = minutosFoco;
    }

    public Integer getMinutosReuniao() {
        return minutosReuniao;
    }

    public void setMinutosReuniao(Integer minutosReuniao) {
        this.minutosReuniao = minutosReuniao;
    }

    public Integer getTarefasConcluidasNoDia() {
        return tarefasConcluidasNoDia;
    }

    public void setTarefasConcluidasNoDia(Integer tarefasConcluidasNoDia) {
        this.tarefasConcluidasNoDia = tarefasConcluidasNoDia;
    }

    public String getPeriodoMaisProdutivo() {
        return periodoMaisProdutivo;
    }

    public void setPeriodoMaisProdutivo(String periodoMaisProdutivo) {
        this.periodoMaisProdutivo = periodoMaisProdutivo;
    }


}
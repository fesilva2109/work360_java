package com.project.Work360.ai.rag;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rag_contexto")
public class RAGContexto implements Serializable {

    @Id
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "contexto", columnDefinition = "VARCHAR(MAX)")
    private String contexto;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public RAGContexto() {}

    public RAGContexto(Long usuarioId, String contexto) {
        this.usuarioId = usuarioId;
        this.contexto = contexto;
        this.atualizadoEm = LocalDateTime.now();
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
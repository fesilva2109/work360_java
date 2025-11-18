package com.project.Work360.ai.rag;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RAGService {

    private final RAGRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final int MAX_ITEMS = 7;

    @Autowired
    public RAGService(RAGRepository repository) {
        this.repository = repository;
    }


    public String buscarContexto(Long usuarioId) {
        return repository.findById(usuarioId)
                .map(RAGContexto::getContexto)
                .orElse("[]");  // histórico vazio
    }


    public void salvarContexto(Long usuarioId, String novoContexto) {
        RAGContexto rag = repository.findById(usuarioId)
                .orElse(new RAGContexto(usuarioId, novoContexto));

        rag.setContexto(novoContexto);
        rag.setAtualizadoEm(LocalDateTime.now());
        repository.save(rag);
    }


    public void appendContexto(Long usuarioId, String novoBloco) {

        List<String> blocos;

        try {
            // Tenta ler o JSON armazenado
            blocos = mapper.readValue(buscarContexto(usuarioId), new TypeReference<List<String>>() {});
        } catch (Exception e) {
            blocos = new ArrayList<>();
        }

        // Adiciona novo bloco no início
        blocos.add(0, novoBloco);

        // Mantém tamanho máximo
        if (blocos.size() > MAX_ITEMS) {
            blocos = blocos.subList(0, MAX_ITEMS);
        }

        try {
            salvarContexto(usuarioId, mapper.writeValueAsString(blocos));
        } catch (Exception e) {
            salvarContexto(usuarioId, "[]");
        }
    }


    public void limparContexto(Long usuarioId) {
        salvarContexto(usuarioId, "[]");
    }
}
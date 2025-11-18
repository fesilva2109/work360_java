package com.project.Work360.ai.agents;

import com.project.Work360.model.Relatorio;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.stereotype.Service;



@Service
public class IAAgenteResumoService {

    private final ChatClient chat;

    public IAAgenteResumoService(ChatClient.Builder builder) {
        this.chat = builder.build();
    }

    public String gerarResumo(Relatorio relatorio, String contextoRAG) {

        String prompt = """
        Gere um resumo claro, curto e objetivo do relatório abaixo.
        
        Use também o histórico (RAG) para identificar padrões ao longo dos dias.
        
        NUNCA use markdown ou blocos ```json.
        
        ### HISTÓRICO RAG:
        %s
        
        ### RELATÓRIO:
        %s
        """.formatted(contextoRAG, relatorio.toString());


        return chat
                .prompt(prompt)
                .call()
                .content();
    }
}


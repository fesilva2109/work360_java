package com.project.Work360.ai.agents;


import com.google.gson.Gson;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IAAgenteClassificadorService {

    private final ChatModel chatModel;
    private final Gson gson = new Gson();

    public IAAgenteClassificadorService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Map<String, Object> classificar(Object relatorio, String contextoRAG) {

        String prompt = """
            Você é um classificador de produtividade.
            Responda estritamente em JSON válido, contendo EXATAMENTE:
            
            {
              "tarefasConcluidas": 0,
              "tarefasPendentes": 0,
              "reunioesRealizadas": 0,
              "minutosFoco": 0
            }
            
            Nunca adicione novas chaves.
            Nunca escreva explicações.
            Nunca use markdown.
            Nunca use ```json.
            
            ### CONTEXTO HISTÓRICO (RAG)
            %s
            
            ### RELATÓRIO DO DIA
            %s
            """.formatted(contextoRAG, relatorio.toString());

        ChatResponse response = chatModel.call(
                new Prompt(
                        new SystemMessage("Agente classificador"),
                        new UserMessage(prompt)
                )
        );

        String output = response.getResult().getOutput().getText();

        System.out.println("=== CLASS RAW ===");
        System.out.println(output);

        try {
            return gson.fromJson(output, Map.class);
        } catch (Exception e) {
            System.out.println("⚠ JSON inválido no classificador. Usando fallback.");
        }

        Map<String,Object> fallback = new HashMap<>();
        fallback.put("tarefasConcluidas", 0);
        fallback.put("tarefasPendentes", 0);
        fallback.put("reunioesRealizadas", 0);
        fallback.put("minutosFoco", 0);

        return fallback;
    }
}

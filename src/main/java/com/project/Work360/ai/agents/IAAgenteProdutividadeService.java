package com.project.Work360.ai.agents;


import com.google.gson.Gson;
import com.project.Work360.model.Relatorio;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.*;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class IAAgenteProdutividadeService {

    private final ChatClient chat;
    private final Gson gson = new Gson();

    public IAAgenteProdutividadeService(ChatClient.Builder builder) {
        this.chat = builder.build();
    }

    public Map<String, Object> analisarProdutividade(Relatorio relatorio, String contextoRAG) {

        String prompt = """
            Você é um agente de análise de produtividade.
            Responda exclusivamente com um JSON válido seguindo exatamente esta estrutura:
            
            {
              "percentualConclusao": 0.0,
              "riscoBurnout": 0.0,
              "tendenciaProdutividade": "",
              "tendenciaFoco": "",
              "insights": "",
              "recomendacoes": ""
            }
            
            NUNCA use markdown ou blocos ```json.
            
            =====================================================
            REGRAS PARA A ANÁLISE:
            =====================================================
            
            1) TENDÊNCIA DE PRODUTIVIDADE (manhã, tarde, noite):
               - Analise o histórico do usuário (RAG).
               - Se a maioria das tarefas concluídas ocorreram na parte da manhã → "manhã"
               - Se ocorreram mais na tarde → "tarde"
               - Se ocorreram mais à noite → "noite"
               - SE NÃO DER para determinar → escolha a melhor estimativa com base nos padrões do histórico.
            
            2) TENDÊNCIA DE FOCO (intervalo de horário real):
               - Analise os campos "periodo_mais_produtivo" do histórico.
               - Retorne algo como "entre 09h e 10h", "entre 21h e 22h".
               - Se houver vários dias, calcule a média.
               - SE NÃO FOR possível calcular → retorne um período genérico como:
                    "entre 10h e 11h" baseado nos dados mais próximos.
            
            3) RECOMENDAÇÕES:
               - NUNCA deixe vazio.
               - Caso o relatório não contenha informações sólidas, gere recomendações leves e motivacionais como:
                    - "Continue mantendo seu ritmo."
                    - "Você está indo muito bem, mantenha o foco."
                    - "Pequenos períodos adicionais de foco podem melhorar sua performance."
            
            4) INSIGHTS:
               - Faça um resumo curto do comportamento produtivo do usuário considerando o histórico e o dia atual.
            
            =====================================================
            RELATÓRIO ATUAL:
            %s
            
            HISTÓRICO RAG (VÁRIOS DIAS):
            %s
            =====================================================
        """.formatted(relatorio.toString(), contextoRAG);


        String output = chat.prompt(prompt).call().content();

        try {
            return gson.fromJson(output, Map.class);
        } catch (Exception e) {
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("percentualConclusao", 0.0);
            fallback.put("riscoBurnout", 0.0);
            fallback.put("tendenciaProdutividade", "Indefinido");
            fallback.put("tendenciaFoco", "Indefinido");
            fallback.put("insights", output);
            fallback.put("recomendacoes", "Não foi possível gerar recomendações.");
            return fallback;
        }
    }
}

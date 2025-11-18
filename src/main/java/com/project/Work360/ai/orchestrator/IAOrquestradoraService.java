package com.project.Work360.ai.orchestrator;

import com.project.Work360.ai.agents.IAAgenteClassificadorService;
import com.project.Work360.ai.agents.IAAgenteProdutividadeService;
import com.project.Work360.ai.agents.IAAgenteResumoService;
import com.project.Work360.ai.rag.RAGService;
import com.project.Work360.model.Relatorio;
import com.project.Work360.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class IAOrquestradoraService {

    private final IAAgenteProdutividadeService produtividadeAgent;
    private final IAAgenteResumoService resumoAgent;
    private final IAAgenteClassificadorService classificadorAgent;
    private final RAGService ragService;
    private final RelatorioService relatorioService;

    @Autowired
    public IAOrquestradoraService(
            IAAgenteProdutividadeService produtividadeAgent,
            IAAgenteResumoService resumoAgent,
            IAAgenteClassificadorService classificadorAgent,
            RAGService ragService,
            RelatorioService relatorioService
    ) {
        this.produtividadeAgent = produtividadeAgent;
        this.resumoAgent = resumoAgent;
        this.classificadorAgent = classificadorAgent;
        this.ragService = ragService;
        this.relatorioService = relatorioService;
    }


    private int getIntSafe(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        if (value instanceof Number n) return n.intValue();
        if (value instanceof String s && s.matches("\\d+")) return Integer.parseInt(s);
        return 0;
    }

    private double getDoubleSafe(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0.0;
        if (value instanceof Number n) return n.doubleValue();
        if (value instanceof String s && s.matches("\\d+(\\.\\d+)?")) return Double.parseDouble(s);
        return 0.0;
    }


    public Relatorio processarRelatorio(Relatorio relatorio) {

        Long usuarioId = relatorio.getUsuario().getId();


        String contextoRAG = ragService.buscarContexto(usuarioId);


        Map<String, Object> dadosClassificados =
                classificadorAgent.classificar(relatorio, contextoRAG);

        relatorio.setTarefasConcluidas(
                getIntSafe(dadosClassificados, "tarefasConcluidas")
        );
        relatorio.setTarefasPendentes(
                getIntSafe(dadosClassificados, "tarefasPendentes")
        );
        relatorio.setReunioesRealizadas(
                getIntSafe(dadosClassificados, "reunioesRealizadas")
        );
        relatorio.setMinutosFocoTotal(
                getIntSafe(dadosClassificados, "minutosFoco")
        );

        // 3) Agente de produtividade
        Map<String, Object> analiseProd =
                produtividadeAgent.analisarProdutividade(relatorio, contextoRAG);

        relatorio.setPercentualConclusao(
                getDoubleSafe(analiseProd, "percentualConclusao")
        );
        relatorio.setRiscoBurnout(
                getDoubleSafe(analiseProd, "riscoBurnout")
        );
        relatorio.setTendenciaProdutividade(
                (String) analiseProd.getOrDefault("tendenciaProdutividade", "Indefinido")
        );
        relatorio.setTendenciaFoco(
                (String) analiseProd.getOrDefault("tendenciaFoco", "Indefinido")
        );
        relatorio.setInsights(
                (String) analiseProd.getOrDefault("insights", "")
        );
        relatorio.setRecomendacaoIA(
                (String) analiseProd.getOrDefault("recomendacoes", "")
        );

        // 4) Agente de Resumo
        String resumo = resumoAgent.gerarResumo(relatorio, contextoRAG);
        relatorio.setResumoGeral(resumo);

        // 5) Bloco para o RAG
        String blocoRag = """
            RELATÓRIO %s
            Período: %s → %s
            Produtividade: %s
            Foco: %s
            Burnout: %s
            Insights: %s
        """.formatted(
                LocalDate.now(),
                relatorio.getDataInicio(),
                relatorio.getDataFim(),
                relatorio.getTendenciaProdutividade(),
                relatorio.getTendenciaFoco(),
                relatorio.getRiscoBurnout(),
                relatorio.getInsights()
        );


        ragService.appendContexto(usuarioId, blocoRag);

        return relatorioService.saveRelatorio(relatorio);
    }
}

package com.project.Work360.service;

import com.project.Work360.dto.RelatorioResponse;
import com.project.Work360.mapper.RelatorioMapper;
import com.project.Work360.model.AnalyticsMetrica;
import com.project.Work360.model.Relatorio;
import com.project.Work360.model.Usuario;
import com.project.Work360.repository.AnalyticsMetricaRepository;
import com.project.Work360.repository.RelatorioRepository;
import com.project.Work360.repository.ReuniaoRepository;
import com.project.Work360.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final RelatorioMapper relatorioMapper = new RelatorioMapper();

    @Autowired
    private AnalyticsMetricaRepository metricaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public RelatorioService(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }


    @Transactional
    public RelatorioResponse gerarRelatorioCompleto(Long usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        Usuario usuario = usuarioService.findUsuarioById(usuarioId);

        List<AnalyticsMetrica> metricas = metricaRepository.findByUsuarioIdAndDataBetween(
                usuarioId, dataInicio, dataFim);

        if (metricas.isEmpty()) {
            throw new RuntimeException("Sem métricas para gerar relatório.");
        }

        // Somatórios básicos
        int totalTarefasConcluidas = metricas.stream()
                .mapToInt(m -> m.getTarefasConcluidasNoDia() != null ? m.getTarefasConcluidasNoDia() : 0)
                .sum();

        int totalMinutosFoco = metricas.stream()
                .mapToInt(m -> m.getMinutosFoco() != null ? m.getMinutosFoco() : 0)
                .sum();

        // Minutos de reunião
        int totalMinutosReuniao = metricas.stream()
                .mapToInt(m -> m.getMinutosReuniao() != null ? m.getMinutosReuniao() : 0)
                .sum();

        // Tarefas pendentes
        int tarefasPendentes = (int) tarefaRepository.findByUsuarioId(usuarioId)
                .stream()
                .filter(t -> !t.isConcluida())
                .count();

        // Reuniões realizadas no intervalo
        int reunioesRealizadas = (int) reuniaoRepository.findByUsuarioId(usuarioId)
                .stream()
                .filter(r -> r.getData() != null
                        && !r.getData().isBefore(dataInicio.atStartOfDay())
                        && !r.getData().isAfter(dataFim.atTime(23, 59)))
                .count();


        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);
        relatorio.setDataInicio(dataInicio);
        relatorio.setDataFim(dataFim);
        relatorio.setTarefasConcluidas(totalTarefasConcluidas);
        relatorio.setTarefasPendentes(tarefasPendentes);
        relatorio.setReunioesRealizadas(reunioesRealizadas);
        relatorio.setMinutosFocoTotal(totalMinutosFoco);


        relatorio.setPercentualConclusao(null);
        relatorio.setRiscoBurnout(null);
        relatorio.setTendenciaProdutividade(null);
        relatorio.setTendenciaFoco(null);
        relatorio.setResumoGeral(null);
        relatorio.setInsights(null);
        relatorio.setRecomendacaoIA(null);

        Relatorio salvo = relatorioRepository.save(relatorio);
        return relatorioMapper.toResponse(salvo);
    }


    public Relatorio saveRelatorio(Relatorio relatorio) {
        return relatorioRepository.save(relatorio);
    }


    public Relatorio findEntityById(Long id) {
        return relatorioRepository.findById(id).orElse(null);
    }


    public List<RelatorioResponse> findByUsuario(Long usuarioId) {
        return relatorioRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(relatorioMapper::toResponse)
                .toList();
    }


    public boolean deletarRelatorio(Long id) {
        if (relatorioRepository.existsById(id)) {
            relatorioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

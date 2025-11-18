package com.project.Work360.service;


import com.project.Work360.dto.AnalyticsEventoRequest;
import com.project.Work360.dto.AnalyticsEventoResponse;
import com.project.Work360.dto.AnalyticsMetricaResponse;
import com.project.Work360.mapper.AnalyticsEventoMapper;
import com.project.Work360.mapper.AnalyticsMetricaMapper;
import com.project.Work360.model.*;
import com.project.Work360.repository.AnalyticsEventoRepository;
import com.project.Work360.repository.AnalyticsMetricaRepository;
import com.project.Work360.repository.ReuniaoRepository;
import com.project.Work360.repository.TarefaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AnalyticsEventoRepository eventoRepository;
    private final AnalyticsMetricaRepository metricaRepository;
    private final UsuarioService usuarioService;
    private final TarefaService tarefaService;
    private final ReuniaoService reuniaoService;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    private final AnalyticsEventoMapper eventoMapper = new AnalyticsEventoMapper();
    private final AnalyticsMetricaMapper metricaMapper = new AnalyticsMetricaMapper();

    @Autowired
    public AnalyticsService(
            AnalyticsEventoRepository eventoRepository,
            AnalyticsMetricaRepository metricaRepository,
            UsuarioService usuarioService,
            TarefaService tarefaService,
            ReuniaoService reuniaoService) {
        this.eventoRepository = eventoRepository;
        this.metricaRepository = metricaRepository;
        this.usuarioService = usuarioService;
        this.tarefaService = tarefaService;
        this.reuniaoService = reuniaoService;
    }

    public AnalyticsEventoResponse saveEvento(AnalyticsEventoRequest request) {
        Usuario usuario = usuarioService.findUsuarioById(request.usuarioId());
        Tarefa tarefa = request.tarefaId() != null ? tarefaService.findTarefaById(request.tarefaId()) : null;
        Reuniao reuniao = request.reuniaoId() != null ? reuniaoService.findReuniaoById(request.reuniaoId()) : null;

        AnalyticsEvento evento = eventoMapper.toEntity(request, usuario, tarefa, reuniao);
        AnalyticsEvento salvo = eventoRepository.save(evento);


        if (tarefa != null && evento.getTipoEvento() == TipoEvento.TAREFA_CONCLUIDA) {
            tarefa.setConcluida(true);
            tarefaRepository.save(tarefa);
            System.out.println("Tarefa marcada como concluída: " + tarefa.getTitulo());
        }

        return eventoMapper.toResponse(salvo);
    }


    public List<AnalyticsMetricaResponse> findMetricasByUsuario(Long usuarioId) {
        return metricaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(metricaMapper::toResponse)
                .toList();
    }

    @Transactional
    public void gerarMetricasCompletas(Long usuarioId) {
        System.out.println(">> Gerando métricas para usuário " + usuarioId);
        Usuario usuario = usuarioService.findUsuarioById(usuarioId);
        LocalDate hoje = LocalDate.now();


        metricaRepository.deleteByUsuarioIdAndData(usuarioId, hoje);


        LocalDateTime inicioDoDia = hoje.atStartOfDay();
        LocalDateTime fimDoDia = inicioDoDia.plusDays(1).minusSeconds(1);


        List<AnalyticsEvento> eventos = eventoRepository.findByUsuarioIdAndTimestampBetween(
                usuarioId, inicioDoDia, fimDoDia);

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento encontrado para gerar métricas.");
            return;
        }


        long tarefasConcluidas = eventos.stream()
                .filter(e -> e.getTipoEvento() == TipoEvento.TAREFA_CONCLUIDA)
                .count();

        int minutosTarefas = eventos.stream()
                .filter(e -> e.getTipoEvento() == TipoEvento.TAREFA_CONCLUIDA && e.getTarefa() != null)
                .mapToInt(e -> e.getTarefa().getEstimativaMinutos() != null ? e.getTarefa().getEstimativaMinutos() : 0)
                .sum();


        Map<Long, LocalDateTime> inicioReunioes = new HashMap<>();
        Map<Long, LocalDateTime> inicioFoco = new HashMap<>();

        int minutosReuniao = 0;
        int minutosFoco = 0;

        for (AnalyticsEvento e : eventos) {


            if (e.getTipoEvento() == TipoEvento.REUNIAO_INICIO && e.getReuniao() != null) {
                inicioReunioes.put(e.getReuniao().getId(), e.getTimestamp());
            } else if (e.getTipoEvento() == TipoEvento.REUNIAO_FIM && e.getReuniao() != null) {
                LocalDateTime inicio = inicioReunioes.remove(e.getReuniao().getId());
                if (inicio != null) {
                    minutosReuniao += Duration.between(inicio, e.getTimestamp()).toMinutes();
                }
            }


            if (e.getTipoEvento() == TipoEvento.FOCO_INICIO) {
                inicioFoco.put(e.getId(), e.getTimestamp());
            } else if (e.getTipoEvento() == TipoEvento.FOCO_FIM) {
                LocalDateTime inicio = inicioFoco.values().stream()
                        .reduce((first, second) -> second)
                        .orElse(null);
                if (inicio != null) {
                    minutosFoco += Duration.between(inicio, e.getTimestamp()).toMinutes();
                }
                inicioFoco.clear(); // fecha sessão
            }
        }


        Map<Integer, Long> contagemPorHora = eventos.stream()
                .filter(e -> e.getTipoEvento() == TipoEvento.FOCO_INICIO ||
                        e.getTipoEvento() == TipoEvento.TAREFA_CONCLUIDA ||
                        e.getTipoEvento() == TipoEvento.REUNIAO_INICIO)
                .collect(Collectors.groupingBy(e -> e.getTimestamp().getHour(), Collectors.counting()));

        String periodoMaisProdutivo = contagemPorHora.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + ":00 - " + (e.getKey() + 1) + ":00")
                .orElse("Não definido");


        if (tarefasConcluidas == 0 && minutosFoco == 0 && minutosReuniao == 0) {
            System.out.println("Nenhum dado relevante para gerar métricas de "
                    + usuario.getNome() + " em " + hoje);
            return;
        }


        AnalyticsMetrica metrica = new AnalyticsMetrica();
        metrica.setUsuario(usuario);
        metrica.setData(hoje);
        metrica.setMinutosFoco(minutosFoco);
        metrica.setMinutosReuniao(minutosReuniao);
        metrica.setTarefasConcluidasNoDia((int) tarefasConcluidas);
        metrica.setPeriodoMaisProdutivo(periodoMaisProdutivo);


        metricaRepository.save(metrica);

        System.out.println("Métricas geradas com sucesso para " + usuario.getNome() + " em " + hoje);
    }


    public Optional<AnalyticsMetricaResponse> findMetricaDoDia(Long usuarioId, LocalDate data) {
        return metricaRepository.findByUsuarioIdAndData(usuarioId, data)
                .map(metricaMapper::toResponse);
    }


}
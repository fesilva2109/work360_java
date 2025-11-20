package com.project.Work360.controller;

import com.project.Work360.dto.RelatorioResponse;
import com.project.Work360.repository.RelatorioRepository;
import com.project.Work360.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/relatorios")
@Tag(name = "6 - Relatórios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;


    @Operation(summary = "Gera um novo relatório completo de produtividade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relatório gerado com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RelatorioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao gerar relatório!", content = @Content)
    })
    @PostMapping("/gerar")
    public ResponseEntity<RelatorioResponse> gerarRelatorioCompleto(
            @RequestParam Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        try {
            RelatorioResponse relatorio = relatorioService.gerarRelatorioCompleto(usuarioId, dataInicio, dataFim);
            return ResponseEntity.status(201).body(relatorio);
        } catch (RuntimeException e) {
            // Captura a exceção "Sem métricas..." e a transforma em uma resposta 400 Bad Request.
            if (e.getMessage().contains("Sem métricas para gerar relatório")) {
                return ResponseEntity.badRequest().build();
            }
            throw e; // Se for outra RuntimeException, mantém o comportamento de erro interno (500).
        }
    }


    @Operation(summary = "Lista todos os relatórios de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatórios retornados com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RelatorioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum relatório encontrado!", content = @Content)
    })
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<RelatorioResponse>> listarRelatorios(@PathVariable Long usuarioId) {
        List<RelatorioResponse> relatorios = relatorioService.findByUsuario(usuarioId);
        return ResponseEntity.ok(relatorios);
    }


    @Operation(summary = "Deleta um relatório pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relatório deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRelatorio(@PathVariable Long id) {
        boolean deletado = relatorioService.deletarRelatorio(id);
        return deletado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

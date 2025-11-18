package com.project.Work360.controller;

import com.project.Work360.dto.TarefaRequest;
import com.project.Work360.dto.TarefaResponse;
import com.project.Work360.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "3 - Tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(summary = "Cria uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos!",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TarefaResponse> create(@Valid @RequestBody TarefaRequest request) {
        return new ResponseEntity<>(tarefaService.save(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todas as tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<TarefaResponse>> readAll(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("titulo").ascending());
        return ResponseEntity.ok(tarefaService.findAll(pageable));
    }

    @Operation(summary = "Busca uma tarefa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada!",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> read(@PathVariable Long id) {
        TarefaResponse tarefa = tarefaService.findById(id);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualiza uma tarefa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar tarefa!",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> update(@PathVariable Long id, @Valid @RequestBody TarefaRequest request) {
        TarefaResponse tarefa = tarefaService.update(id, request);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Exclui uma tarefa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa excluída com sucesso!", content = @Content),
            @ApiResponse(responseCode = "400", description = "Tarefa não encontrada!", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return tarefaService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
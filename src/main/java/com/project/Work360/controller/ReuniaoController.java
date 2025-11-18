package com.project.Work360.controller;

import com.project.Work360.dto.ReuniaoRequest;
import com.project.Work360.dto.ReuniaoResponse;
import com.project.Work360.service.ReuniaoService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reunioes")
@Tag(name = "4 - Reuniões")
public class ReuniaoController {

    @Autowired
    private ReuniaoService reuniaoService;

    @Operation(summary = "Cria uma nova reunião")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reunião criada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReuniaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos!",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReuniaoResponse> create(@Valid @RequestBody ReuniaoRequest request) {
        return ResponseEntity.status(201).body(reuniaoService.save(request));
    }

    @Operation(summary = "Lista todas as reuniões")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reuniões retornada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReuniaoResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ReuniaoResponse>> readAll(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("data").descending());
        return ResponseEntity.ok(reuniaoService.findAll(pageable));
    }

    @Operation(summary = "Busca uma reunião por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reunião encontrada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReuniaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reunião não encontrada!",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReuniaoResponse> read(@PathVariable Long id) {
        ReuniaoResponse reuniao = reuniaoService.findById(id);
        return reuniao != null ? ResponseEntity.ok(reuniao) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualiza uma reunião existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reunião atualizada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReuniaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar reunião!",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReuniaoResponse> update(@PathVariable Long id, @Valid @RequestBody ReuniaoRequest request) {
        ReuniaoResponse reuniao = reuniaoService.update(id, request);
        return reuniao != null ? ResponseEntity.ok(reuniao) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Exclui uma reunião por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reunião excluída com sucesso!", content = @Content),
            @ApiResponse(responseCode = "400", description = "Reunião não encontrada!", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return reuniaoService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
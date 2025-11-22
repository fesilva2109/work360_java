package com.project.Work360.oracle.controller;

import com.project.Work360.oracle.model.FocusSession;
import com.project.Work360.oracle.service.FocusSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/focus")
@Tag(name = "Focus IoT (Oracle)", description = "Gerencia sessões de foco e dados IoT")
public class FocusSessionController {

    @Autowired
    private FocusSessionService service;

    @Operation(summary = "Inicia uma nova sessão de foco (Cronômetro)")
    @PostMapping("/start")
    public ResponseEntity<FocusSession> start(@RequestBody Map<String, Long> payload) {
        Long usuarioId = payload.get("usuarioId");
        return ResponseEntity.ok(service.startSession(usuarioId));
    }

    @Operation(summary = "Finaliza a sessão e retorna dados simulados do IoT")
    @PostMapping("/stop/{sessionId}")
    public ResponseEntity<FocusSession> stop(@PathVariable Long sessionId) {
        return ResponseEntity.ok(service.finishSession(sessionId));
    }

    @Operation(summary = "Histórico de sessões do usuário (Salvo no Oracle)")
    @GetMapping("/history/{usuarioId}")
    public ResponseEntity<List<FocusSession>> getHistory(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.getHistory(usuarioId));
    }
    @Operation(summary = "Recebe dados reais/simulados do IoT durante a sessão de foco")
    @PostMapping("/iot/sensordata")
    public ResponseEntity<String> receiveSensorData(@RequestBody Map<String, Object> payload) {
        Long usuarioId = Long.valueOf(payload.get("usuarioId").toString());
        int bpm = Integer.parseInt(payload.get("batimentosCardiacos").toString());
        int noise = Integer.parseInt(payload.get("nivelRuidoDB").toString());
        int segundos = Integer.parseInt(payload.get("tempoFocoSegundos").toString());

        service.updateLiveSensorData(usuarioId, bpm, noise, segundos);

        return ResponseEntity.ok("Dados IoT recebidos com sucesso");
    }
}
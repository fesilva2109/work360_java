package com.project.Work360.oracle.service;

import com.project.Work360.oracle.model.FocusSession;
import com.project.Work360.oracle.repository.FocusSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FocusSessionService {

    @Autowired
    private FocusSessionRepository repository;

    public FocusSession startSession(Long usuarioId) {
        FocusSession session = new FocusSession();
        session.setUsuarioId(usuarioId);
        session.setStartTime(LocalDateTime.now());
        session.setStatus("EM_ANDAMENTO");
        return repository.save(session);
    }

    public FocusSession finishSession(Long sessionId) {
        Optional<FocusSession> sessionOpt = repository.findById(sessionId);
        
        if (sessionOpt.isPresent()) {
            FocusSession session = sessionOpt.get();
            session.setEndTime(LocalDateTime.now());
            session.setStatus("CONCLUIDO");

  
            int randomBpm = ThreadLocalRandom.current().nextInt(60, 110); 
            int randomNoise = ThreadLocalRandom.current().nextInt(30, 80); 
            
            session.setAvgBpm(randomBpm);
            session.setAvgNoiseDb(randomNoise);

            return repository.save(session);
        }
        throw new RuntimeException("Sessão não encontrada");
    }

    public List<FocusSession> getHistory(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
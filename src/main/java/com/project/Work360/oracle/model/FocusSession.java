package com.project.Work360.oracle.model;

import jakarta.persistence.*;
import lombok.Data; 
import java.time.LocalDateTime;

@Data 
@Entity
@Table(name = "TB_FOCUS_SESSION")
public class FocusSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "focus_seq")
    @SequenceGenerator(name = "focus_seq", sequenceName = "SQ_TB_FOCUS_SESSION", allocationSize = 1)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long usuarioId;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    // Dados que viriam do IoT
    @Column(name = "AVG_BPM")
    private Integer avgBpm;

    @Column(name = "AVG_NOISE_DB")
    private Integer avgNoiseDb;
    
    @Column(name = "STATUS")
    private String status; 
}
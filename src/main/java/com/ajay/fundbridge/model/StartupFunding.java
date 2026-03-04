package com.ajay.fundbridge.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "startup_funding")
public class StartupFunding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "startup_id", nullable = false, unique = true)
    private Startup startup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_stage_id", nullable = false)
    private FundingStage fundingStage;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountRequired;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal equityOffered;

    @Column(columnDefinition = "TEXT")
    private String useOfFunds;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

package com.ajay.fundbridge.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "investor_portfolio",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"investor_id", "startup_id"})
        }
)
public class InvestorPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "investor_id", nullable = false)
    private UUID investorId;

    @Column(name = "startup_id", nullable = false)
    private UUID startupId;

    @Column(name = "investment_amount", nullable = false)
    private BigDecimal investmentAmount;

    @Column(name = "equity_percentage")
    private BigDecimal equityPercentage;

    @Column(name = "investment_date")
    private LocalDate investmentDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
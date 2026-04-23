package com.fundbridge.investor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "investors")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "investor_type", nullable = false)
    private InvestorType investorType;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "company_website")
    private String companyWebsite;

    private String location;

    @Column(length = 1000)
    private String bio;

    @Column(name = "portfolio_size")
    private Integer portfolioSize;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

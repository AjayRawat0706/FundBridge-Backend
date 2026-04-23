package com.fundbridge.startup.model;

import com.fundbridge.startup.model.Startup;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "startup_documents")
public class StartupDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup_id", nullable = false)
    private Startup startup;

    @Column(nullable = false, length = 50)
    private String documentType;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String publicId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt;
}
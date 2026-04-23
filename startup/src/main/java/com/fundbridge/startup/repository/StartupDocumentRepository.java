package com.fundbridge.startup.repository;

import com.fundbridge.startup.model.StartupDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StartupDocumentRepository  extends JpaRepository<StartupDocument, UUID> {
    List<StartupDocument> findByStartupId(UUID startupId);
}

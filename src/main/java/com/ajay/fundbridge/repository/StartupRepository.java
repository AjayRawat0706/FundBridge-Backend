package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StartupRepository extends JpaRepository<Startup, UUID> {
    List<Startup> findByOwnerId(UUID ownerId);
}

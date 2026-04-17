package com.fundbridge.investor.repository;

import com.fundbridge.investor.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface StartupRepository extends JpaRepository<Startup, UUID> {
    List<Startup> findByOwnerId(UUID ownerId);
}

package com.fundbridge.startup.repository;

import com.fundbridge.startup.model.StartupFunding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StartupFundingRepository extends JpaRepository<StartupFunding, UUID> {
    boolean existsByStartupId(UUID id);

    Optional<StartupFunding> findByStartupId(UUID startupId);
}

package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, UUID> {
    Optional<Investor> findByUserId(UUID userId);
}

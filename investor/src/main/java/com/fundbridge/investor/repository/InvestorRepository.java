package com.fundbridge.investor.repository;

import com.fundbridge.investor.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, UUID> {
    Optional<Investor> findByUserId(UUID userId);
}

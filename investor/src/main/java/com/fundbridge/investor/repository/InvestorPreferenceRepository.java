package com.fundbridge.investor.repository;

import com.fundbridge.investor.model.InvestorPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvestorPreferenceRepository extends JpaRepository<InvestorPreference, UUID> {

    void deleteByInvestorId(UUID investorId);

    Optional<InvestorPreference> findByInvestorId(UUID investorId);
}

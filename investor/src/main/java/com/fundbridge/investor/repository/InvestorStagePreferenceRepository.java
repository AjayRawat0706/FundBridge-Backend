package com.fundbridge.investor.repository;

import com.fundbridge.investor.model.InvestorStagePreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvestorStagePreferenceRepository extends JpaRepository<InvestorStagePreference, UUID> {
    void deleteByInvestorId(UUID investorId);

    List<InvestorStagePreference> findByInvestorId(UUID investorId);
}
